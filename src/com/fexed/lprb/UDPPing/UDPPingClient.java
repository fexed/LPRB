package com.fexed.lprb.UDPPing;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Federico Matteoni
 */
public class UDPPingClient implements Runnable {
    private InetAddress hostname;
    private int port;

    public UDPPingClient(String hostname, int port) {
        try { this.hostname = InetAddress.getByName(hostname); }
        catch (UnknownHostException e) { System.err.println("ERR - arg 1");}
        this.port = port;
    }

    @Override
    public void run() {
        int transmitted = 0, received = 0;

        System.out.println("Launching " + this.getClass().getSimpleName() + " on " + this.hostname + ":" + this.port);

        try {
            DatagramSocket dtgSkt = new DatagramSocket();
            dtgSkt.setSoTimeout(2000);
            for (; transmitted < 10; transmitted++) {
                try {

                    byte[] buffer = ("PING " + transmitted + " " + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.hostname, this.port);
                    System.out.print(new String(buffer, StandardCharsets.UTF_8) + " RTT: ");
                    dtgSkt.send(packet);

                    long time = System.currentTimeMillis();
                    try {
                        dtgSkt.receive(packet);
                        time = System.currentTimeMillis() - time;
                        System.out.println(time + " ms");
                        received++;
                    } catch (SocketTimeoutException ex) {
                        System.out.println("*");
                    }
                } catch (IOException ex) { System.err.println("IOException error on packet " + transmitted + ". Resending"); transmitted--; }
            }
        } catch (SocketException ex) { System.err.println("SocketException error: " + ex.getMessage()); }
    }

    public static void main(String[] args) {
        if (args.length != 2) System.err.println("Usage: java UDPPingClient hostname port");
        else {
            try {
                int port = Integer.parseInt(args[1]);
                new Thread(new UDPPingClient(args[0], port)).start();
            } catch (NumberFormatException ex) { System.err.println("ERR - arg 2"); }
        }
    }
}
