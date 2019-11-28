package com.fexed.lprb.timeserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Federico Matteoni
 */
public class TimeServer implements Runnable {
    private InetAddress addr;
    private int port;

    /**
     * Inizializza l'indirizzo IP multicast a cui spedire i pacchetti
     * @param addr L'indirizzo IP multicast
     */
    public TimeServer(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: TimeServer <multicast IP> <porta>");
        } else {
            try {
                InetAddress addr = InetAddress.getByName(args[0]);
                int port = Integer.parseInt(args[1]);
                if (addr.isMulticastAddress()) {
                    Thread server = new Thread(new TimeServer(addr, port));
                    server.setName("TimeServerThread");
                    server.start();
                } else System.err.println("L'indirizzo fornito non è un indirizzo di multicast");
            } catch (UnknownHostException ex) { System.err.println("UnknownHostException");
            } catch (NumberFormatException ex) { System.err.println("Porta non valida"); }
        }
    }
}
