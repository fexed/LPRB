package com.fexed.lprb.conticorrenti;

/*
Creare un file contenente oggetti che rappresentano i conti correnti di una banca.

Ogni conto corrente contiene il nome del correntista ed una lista di movimenti.

I movimenti registrati per un conto corrente sono relativi agli ultimi 2 anni, quindi possono essere molto numerosi.
Per ogni movimento vengono registrati la data e la causale del movimento.
L'insieme delle causali possibili è fissato: Bonifico, Accredito, Bollettino, F24, PagoBancomat.

-   rileggere il file e trovare, per ogni possibile causale, quanti movimenti hanno quella causale.
-   progettare un'applicazione che attiva un insieme di thread. Uno di essi legge dal file gli oggetti “conto corrente”
    e in passa, uno per volta, ai thread presenti in un thread pool.
-   ogni thread calcola il numero di occorrenze di ogni possibile causale all'interno di quel conto corrente ed aggiorna
    un contatore globale.
-   alla fine il programma stampa per ogni possibile causale il numero totale di occorrenze.
-   utilizzare NIO per l'interazione con il file e JSON per la serializzazione
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static ThreadPoolExecutor poolExecutor;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        Thread readerThread = new Thread(new CCReader());                               //Preparazione del thread lettore
        readerThread.setName("Lettore");
        poolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);  //Preparazione della threadpool
        readerThread.start();                                                           //Partenza
        try {
            readerThread.join();
            poolExecutor.awaitTermination(1, TimeUnit.MINUTES);                 //Attesa della fine dei lavori
        } catch (InterruptedException ignored) {}
        time = System.currentTimeMillis() - time;
        System.out.println(
                "Bonifico\t\t" + CCStats.getnBonifico() +
                "\nAccredito\t\t" + CCStats.getnAccredito() +
                "\nBollettino\t\t" + CCStats.getnBollettino() +
                "\nF24\t\t\t\t" + CCStats.getnF24() +
                "\nPagoBancomat\t" + CCStats.getnPagoBancomat() +                        //Output delle info richieste
                "\n" + time + "ms"
        );
    }
}
