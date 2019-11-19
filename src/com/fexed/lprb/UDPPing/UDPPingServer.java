package com.fexed.lprb.UDPPing;

import java.net.DatagramSocket;
import java.net.SocketException;
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
            int rcvBuffSize = dtgSkt.getReceiveBufferSize();
            int sndBuffSize = dtgSkt.getSendBufferSize();
            System.out.println("RCV: " + rcvBuffSize + " B\tSND: " + sndBuffSize + " B");


        } catch (SocketException ex) { System.err.println("Couldn't open server on port " + this.port);}
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
