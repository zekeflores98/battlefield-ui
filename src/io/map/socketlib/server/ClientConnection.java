package io.map.socketlib.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection {
    private final ClientConnectionManager clientConnectionManager;
    private final String id;
    private final Socket socket;

    private Thread receiverThread;
    private PrintWriter printWriter;
    private OnReceiveCallback onReceiveCallback;

    public ClientConnection(ClientConnectionManager clientConnectionManager, String id, Socket socket) throws IOException {
        this.clientConnectionManager = clientConnectionManager;
        this.id = id;
        this.socket = socket;

        receiverThread = new Thread(new Receiver());
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    public String getId() {
        return id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void start() {
        receiverThread.start();
    }

    public void stop() throws IOException {
        socket.close();
    }

    public void sendData(String data) {
        printWriter.println(data);
        printWriter.flush();
    }

    public void setOnReceiveCallback(OnReceiveCallback onReceive) {
        this.onReceiveCallback = onReceive;
    }

    public interface OnReceiveCallback {
        void dataReceived(String id, String data);
    }

    public class Receiver implements Runnable {

        private final BufferedReader bufferedReader;

        public Receiver() throws IOException {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            StringBuilder stringBuilder = new StringBuilder();

            while (true) {
                try {
                    stringBuilder.append(bufferedReader.readLine());

                    if (onReceiveCallback != null) {
                        onReceiveCallback.dataReceived(id, stringBuilder.toString());
                    }

                    stringBuilder.setLength(0); // clear the string builder's contents
                } catch (IOException e) {
                    if (e instanceof SocketException) {
                        clientConnectionManager.clientDisconnected(id);
                    } else {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
