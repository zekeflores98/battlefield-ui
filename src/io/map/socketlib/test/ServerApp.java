package io.map.socketlib.test;

import io.map.socketlib.server.ClientConnection;
import io.map.socketlib.server.ClientConnectionManager;
import io.map.socketlib.server.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        Server server = new Server(3000,
                new ClientConnectionManager.Conditions() {
                    @Override
                    public boolean acceptCondition(Socket socket) {
                        return true;
                    }
                },
                new ClientConnectionManager.Callbacks() {
                    @Override
                    public void onAccept(ClientConnection clientConnection) {
                        try {
                            clientConnection.setOnReceiveCallback(new ClientConnection.OnReceiveCallback() {
                                @Override
                                public void dataReceived(String id, String data) {
                                    System.out.printf("Received `%s` from %s\n", data, id);
                                }
                            });

                            Socket socket = clientConnection.getSocket();

                            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                            printWriter.printf("Connected to server @ %s\n", new Date().toString());
                            printWriter.printf("(id=%s)\n", clientConnection.getId());
                            printWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onReject(Socket socket) { }

                    @Override
                    public void onError(Throwable throwable) { }

                    @Override
                    public void onConnect(String clientId) {
                        System.out.printf("Client connected. (id=%s)\n", clientId);
                    }

                    @Override
                    public void onDisconnect(String clientId) {
                        System.out.printf("Client disconnected. (id=%s)\n", clientId);
                    }
                });

        server.start();

        ClientConnectionManager clientConnectionManager = server.getClientConnectionManager();
        while (true) {
            try {
                String broadcastData = new Date().toString();

                clientConnectionManager.broadcastData(broadcastData);
                System.out.printf("Sent `%s`\n", broadcastData);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
