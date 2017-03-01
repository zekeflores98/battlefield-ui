package io.map.socketlib.scanning;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Scannable {
    public static final int DEFAULT_TIMEOUT = 15000;

    private final Callbacks callbacks;
    private final DatagramSocket scannableSocket;
    private final Thread receiverThread;
    private final byte[] replyBytes;

    private int timeout;

    public Scannable(int port, String reply, Callbacks callbacks) throws SocketException {
        this.callbacks = callbacks;

        replyBytes = reply.getBytes();
        scannableSocket = new DatagramSocket(port);
        receiverThread = new Thread(new Receiver());

        timeout = DEFAULT_TIMEOUT;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void start(boolean useTimeout) {
        receiverThread.start();

        if (useTimeout) {
            try {
                Thread.sleep(timeout);
                stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        scannableSocket.close();
    }

    public interface Callbacks {
        void onSend(DatagramPacket packet);
        void onReceive(DatagramPacket packet);
        boolean shouldReply(DatagramPacket packet);
    }

    private class Receiver implements Runnable {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            while (true) {
                try {
                    DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                    scannableSocket.receive(receivedPacket);
                    callbacks.onReceive(receivedPacket);

                    new Thread(new Responder(receivedPacket)).start();
                } catch (SocketException e) {
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private class Responder implements Runnable {
        private final DatagramPacket receivedPacket;

        public Responder(DatagramPacket receivedPacket) {
            this.receivedPacket = receivedPacket;
        }

        @Override
        public void run() {
            if (callbacks.shouldReply(receivedPacket)) {
                try {
                    DatagramPacket outboundPacket = new DatagramPacket(replyBytes, replyBytes.length,
                            receivedPacket.getAddress(), receivedPacket.getPort());
                    scannableSocket.send(outboundPacket);

                    callbacks.onSend(outboundPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
