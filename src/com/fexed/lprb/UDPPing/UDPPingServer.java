package com.fexed.lprb.UDPPing;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.Random;

/**
 * @author Federico Matteoni
 */
public class UDPPingServer implements Runnable{
    private int port;

    public UDPPingServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Opening " + this.getClass().getSimpleName() + " on port " + this.port);
        try {
            DatagramSocket dtgSkt = new DatagramSocket(this.port);
            dtgSkt.setSoTimeout(60000);
            int rcvBuffSize = dtgSkt.getReceiveBufferSize();
            int sndBuffSize = dtgSkt.getSendBufferSize();
            System.out.println("RCV: " + rcvBuffSize + " B\tSND: " + sndBuffSize + " B");

            try {
                do {
                    byte[] buff = new byte[rcvBuffSize];
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);
                    dtgSkt.receive(packet);
                    System.out.print(packet.getAddress().getHostAddress() + ":" + packet.getPort() + "> " + new String(packet.getData(), StandardCharsets.UTF_8));

                    int n = getAction();
                    if (n == 0) {
                        int delay = getDelay();
                        System.out.println(" ACTION: delayed " + delay + " ms");

                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ignored) {
                        }

                        dtgSkt.send(packet);
                    } else {
                        System.out.println(" ACTION: not sent");
                    }
                } while (true);
            } catch (SocketTimeoutException ex) { System.err.println("Timeout server, 1m");
            } catch (IOException ex) { System.err.println("IOException on receive: " + ex.getMessage()); }
        } catch (SocketException ex) { System.err.println("Couldn't open server on port " + this.port); }
    }

    /**
     * Decide l'azione da intraprendere: ritrasmettere il pacchetto o perderlo con probabilità del 25%.
     * @return {@code 0} se decide di ritrasmettere il pacchetto, altrimenti {@code 1} se decide di perderlo
     */
    public int getAction() {
        Random rnd = new Random(System.currentTimeMillis());
        int n = rnd.nextInt(100);
        if (n < 25) return 1;
        else return 0;
    }

    /**
     * Ritorna un numero casuale compreso tra 50 e 499
     * @return il numero casuale
     */
    public int getDelay() {
        Random rnd = new Random(System.currentTimeMillis());
        return rnd.nextInt(450) + 50;
    }

    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: java UDPPingServer port");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                new Thread(new UDPPingServer(port)).start();
            }
            catch (NumberFormatException ex) { System.err.println("ERR - arg 1"); }

        }
    }
}
