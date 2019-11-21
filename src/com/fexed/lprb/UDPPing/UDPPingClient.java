package com.fexed.lprb.UDPPing;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Federico Matteoni
 */
public class UDPPingClient implements Runnable {
    private InetAddress hostname;
    private int port;

    /**
     * Costruisce il client su porta e host indicati
     * @param hostname l'host a cui connettersi
     * @param port la porta alla quale connettersi
     */
    public UDPPingClient(String hostname, int port) {
        try { this.hostname = InetAddress.getByName(hostname); }
        catch (UnknownHostException e) { System.err.println("ERR - arg 1");}
        this.port = port;
    }

    @Override
    public void run() {
        int transmitted = 0, received = 0;
        long RTTmin = 1000, RTTmax = 0;
        float RTTavg = 0;

        System.out.println("Launching " + this.getClass().getSimpleName() + " on " + this.hostname + ":" + this.port);

        try {
            DatagramSocket dtgSkt = new DatagramSocket();
            dtgSkt.setSoTimeout(2000);                      //Creo il socket con timeout di 2s
            for (; transmitted < 10; transmitted++) {
                try {

                    //PING <n> <timestamp>
                    byte[] buffer = ("PING " + transmitted + " " + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, this.hostname, this.port);
                    System.out.print(new String(buffer, StandardCharsets.UTF_8) + " RTT: ");
                    dtgSkt.send(packet);                    //Preparo e spedisco il pacchetto

                    long time = System.currentTimeMillis(); //Tempo attuale, per misurare l'RTT
                    try {
                        dtgSkt.receive(packet);             //Apetto il pacchetto
                        time = System.currentTimeMillis() - time;
                        System.out.println(time + " ms");
                        if (RTTmin > time) RTTmin = time;
                        if (RTTmax < time) RTTmax = time;
                        RTTavg = (RTTavg + time)/(transmitted + 1);

                        received++;                         //Se lo ricevo, registro le statistiche: RTT, ricevuti ecc.
                    } catch (SocketTimeoutException ex) {
                        System.out.println("*");            //Se scatta il timeout non lo ricevo
                    }
                } catch (IOException ex) { System.err.println("IOException error on packet " + transmitted + ". Resending"); transmitted--; }
            }
            double loss = ((transmitted - received)/(transmitted * 1.0)) * 100;

            System.out.println("\t\t\t\t---- PING Statistics ----"); //Stampa delle statistiche richieste
            System.out.printf(transmitted + " packets transmitted, " + received + " packets received, %1.0f%% packet loss\n", loss);
            System.out.printf("RTT (ms) min/avg/max = " + RTTmin + "/%1.2f/" + RTTmax + "\n", RTTavg);
        } catch (SocketException ex) { System.err.println("SocketException error: " + ex.getMessage()); }
    }

    /**
     * Controlla i parametri e avvia il client
     * @param args Parametri del programma
     */
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
