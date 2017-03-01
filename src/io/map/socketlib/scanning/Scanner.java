package io.map.socketlib.scanning;

import java.io.IOException;
import java.net.*;

public class Scanner {
    private static final int DEFAULT_WAIT_TIME = 2000;
    private static final int DEFAULT_RETRIES = 5;

    private final int targetPort;
    private final byte[] messageBytes;
    private final Thread senderThread;
    private final Thread receiverThread;
    private final Callbacks callbacks;

    private final DatagramSocket scannerSocket;

    private int retries;
    private int waitTime;
    private boolean isBroadcasting;

    public Scanner(int targetPort, String message, Callbacks callbacks) throws SocketException {
        this.callbacks = callbacks;

        this.targetPort = targetPort;
        messageBytes = message.getBytes();

        scannerSocket = new DatagramSocket();
        senderThread = new Thread(new Sender());
        receiverThread = new Thread(new Receiver());

        retries = DEFAULT_RETRIES;
        waitTime = DEFAULT_WAIT_TIME;
    }

    public void start() {
        isBroadcasting = true;
        senderThread.start();
        receiverThread.start();
    }

    public void waitToFinish() throws InterruptedException {
        senderThread.join();
    }

    public void stop() {
        isBroadcasting = false;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public interface Callbacks {
        void onSend(DatagramPacket packet);
        void onReceive(DatagramPacket packet);
    }

    private class Receiver implements Runnable {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    scannerSocket.receive(packet);

                    callbacks.onReceive(packet);
                } catch (SocketException e) {
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Sender implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (isBroadcasting && count++ < retries) {
                try {
                    DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length,
                            InetAddress.getByName("255.255.255.255"), targetPort);
                    scannerSocket.send(packet);
                    callbacks.onSend(packet);

                    Thread.sleep(waitTime);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            scannerSocket.close();
        }
    }
}
