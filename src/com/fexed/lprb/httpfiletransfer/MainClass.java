package com.fexed.lprb.httpfiletransfer;

/*
Scrivere un programma JAVA che implementi un server HTTP che gestisca richieste di trasferimento di file di diverso
tipo (es. immagini jpeg, gif e/o testo) provenienti da un browser web.

Il server
-   sta in ascolto su una porta nota al client (es. 6789)
-   gestisce richieste HTTP di tipo GET inviate da un browser alla URL localhost:port/filename

Le connessioni possono essere non persistenti.

Usare le classi Socket e ServerSocket per sviluppare il programma server.
Per inviare al server le richieste, utilizzare un qualsiasi browser.
 */

import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainClass {
    public static void main(String[] args) {
        boolean running = true;
        ServerSocket srvSkt = null;
        Socket skt = null;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            srvSkt = new ServerSocket(1337);
            do {
                skt = srvSkt.accept();                              //Apertura e accettazione della connessione

                threadPool.execute(new Handler(skt));               //Thread START
            } while (running);
            threadPool.shutdown();
            try { threadPool.awaitTermination(1, TimeUnit.SECONDS); }
            catch (InterruptedException ignored) {}
            srvSkt.close();                                         //Chiusura della connessione
        } catch (IOException e) { e.printStackTrace(); }
    }
}
