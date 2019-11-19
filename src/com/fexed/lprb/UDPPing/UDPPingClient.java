package com.fexed.lprb.UDPPing;

/**
 * @author Federico Matteoni
 */
public class UDPPingClient implements Runnable {
    private String hostname;
    private int port;

    public UDPPingClient(String hostname, int port) {
        this.hostname = hostname;
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
