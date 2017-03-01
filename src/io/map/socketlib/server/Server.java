package io.map.socketlib.server;

import io.map.socketlib.server.ClientConnectionManager.Callbacks;
import io.map.socketlib.server.ClientConnectionManager.Conditions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private ClientConnectionManager clientConnectionManager;

    private Thread connectionListenerThread;

    public Server(int port, Conditions conditions, Callbacks callbacks) throws IOException {
        this.clientConnectionManager = new ClientConnectionManager(conditions, callbacks);

        serverSocket = new ServerSocket(port);
        connectionListenerThread = new Thread(new ConnectionListener());
    }

    public void start() {
        connectionListenerThread.start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public ClientConnectionManager getClientConnectionManager() {
        return clientConnectionManager;
    }

    private class ConnectionListener implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clientConnectionManager.addClientConnection(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
