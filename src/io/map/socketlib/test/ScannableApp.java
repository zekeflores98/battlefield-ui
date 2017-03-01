package io.map.socketlib.test;

import io.map.socketlib.scanning.Scannable;

import java.net.DatagramPacket;
import java.net.SocketException;

public class ScannableApp {
    public static void main(String[] args) {
        try {
            Scannable scannable = new Scannable(3020, "I AM HERE",
                    new Scannable.Callbacks() {
                        @Override
                        public void onSend(DatagramPacket packet) {
                            System.out.printf("Sending `%s` to %s:%d.\n",
                                    new String(packet.getData()).trim(),
                                    packet.getAddress().getHostAddress(),
                                    packet.getPort());
                        }

                        @Override
                        public void onReceive(DatagramPacket packet) {
                            String strData = new String(packet.getData()).trim();
                            System.out.printf("Detected by a scanner. Received `%s`.\n", strData);
                        }

                        @Override
                        public boolean shouldReply(DatagramPacket packet) {
                            String strData = new String(packet.getData()).trim();
                            return strData.equals("R U THERE?");
                        }
                    });
            scannable.start(false);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
