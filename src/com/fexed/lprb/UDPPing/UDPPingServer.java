package com.fexed.lprb.UDPPing;

import java.net.DatagramSocket;
import java.net.SocketException;

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
        try {
            DatagramSocket dtgSkt = new DatagramSocket(this.port);
        } catch (SocketException ignored) {}
    }

    public static void main(String[] args) throws InterruptedException {
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
