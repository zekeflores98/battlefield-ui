package io.map.socketlib.test;

import io.map.socketlib.scanning.Scanner;

import java.net.DatagramPacket;
import java.net.SocketException;

public class ScannerApp {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(3020, "R U THERE?",
                    new Scanner.Callbacks() {
                        @Override
                        public void onSend(DatagramPacket packet) {
                            System.out.printf("Sending `%s` to %s:%d.\n",
                                    new String(packet.getData()).trim(),
                                    packet.getAddress().getHostAddress(),
                                    packet.getPort());
                        }

                        @Override
                        public void onReceive(DatagramPacket packet) {
                            System.out.printf("Found scannable at %s:%d. Replied `%s`.\n",
                                    packet.getAddress().getHostAddress(),
                                    packet.getPort(),
                                    new String(packet.getData()).trim());
                        }
                    });

            scanner.start();
            scanner.waitToFinish();
        } catch (SocketException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
