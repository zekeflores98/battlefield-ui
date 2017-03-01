package io.map.socketlib.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    private final Socket socket;
    private final Thread receiverThread;

    private final PrintWriter printWriter;
    private final Callbacks callbacks;

    private boolean isConnected;

    public Client(String serverAddress, int port, Callbacks callbacks) throws IOException {
        this.callbacks = callbacks;

        socket = new Socket(serverAddress, port);
        printWriter = new PrintWriter(socket.getOutputStream());
        receiverThread = new Thread(new Receiver());
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void start() {
        receiverThread.start();
    }

    public void stop() throws IOException {
        socket.close();
    }

    public void sendData(String data) {
        if (isConnected) {
            printWriter.println(data);
            printWriter.flush();
        }
    }

    public interface Callbacks {
        void dataReceived(String data);

        void serverDisconnected();
    }

    public class Receiver implements Runnable {

        private final BufferedReader bufferedReader;

        public Receiver() throws IOException {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            isConnected = true;
            StringBuilder stringBuilder = new StringBuilder();

            while (true) {
                try {
                    stringBuilder.append(bufferedReader.readLine());
                    callbacks.dataReceived(stringBuilder.toString());

                    stringBuilder.setLength(0); // clear the string builder's contents
                } catch (IOException e) {
                    if (e instanceof SocketException) {
                        isConnected = false;
                        callbacks.serverDisconnected();
                    } else {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
