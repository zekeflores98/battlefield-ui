package io.map.socketlib.test;

import io.map.socketlib.client.Client;

import java.io.IOException;

public class ClientApp {
    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", 3000,
                new Client.Callbacks() {
                    @Override
                    public void dataReceived(String data) {
                        System.out.printf("Server > %s\n", data);
                    }

                    @Override
                    public void serverDisconnected() {
                        System.out.println("Server disconnected.");
                    }
                });
        client.start();

        while (client.isConnected()) {
            try {
                String broadcastData = "i am alive";

                client.sendData(broadcastData);
                System.out.printf("Sent `%s`\n", broadcastData);

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
