package com.fexed.lprb.NIOechoserver;

/*
Scrivere un programma echo server usando la libreria java NIO e, in particolare, il Selector e canali in modalità
non bloccante, e un programma echo client, usando NIO (va bene anche con modalità bloccante).

Il server accetta richieste di connessioni dai client, riceve messaggi inviati dai client e li rispedisce
(eventualmente aggiungendo "echoed by server" al messaggio ricevuto).

Il client legge il messaggio da inviare da console, lo invia al server e visualizza quanto ricevuto dal server.
 */

/**
 * @author Federico Matteoni
 */
public class MainClass {

    public static void main(String[] args) throws InterruptedException {
        Thread serverThread = new Thread(new EchoServer());
        serverThread.setName("ServerThread");
        serverThread.start();

        new Thread(new EchoClient("Client")).start();
        new Thread(new EchoClient("Long String to echo")).start();
        new Thread(new EchoClient("4 d1fF|cUl7 STR!NG t0  E C H O  -->")).start();
        new Thread(new EchoClient("Some more echoes")).start();
        new Thread(new EchoClient("Echo...echo...echo...\techo...")).start();
        new Thread(new EchoClient("exit")).start();
        serverThread.join();
    }
}
