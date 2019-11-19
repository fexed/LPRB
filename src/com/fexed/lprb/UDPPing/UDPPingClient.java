package com.fexed.lprb.UDPPing;

import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

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
