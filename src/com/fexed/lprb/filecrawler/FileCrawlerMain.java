package com.fexed.lprb.filecrawler;

/*
Si scriva un programma JAVA che
-   riceve in input un filepath che individua una directory D
-   stampa le informazioni del contenuto di quella directory e, ricorsivamente, di tutti i file contenuti nelle
    sottodirectory di D

Il programma deve essere strutturato come segue:
-   attiva un thread produttore ed un insieme di k thread consumatori
-   il produttore comunica con i consumatori mediante una coda
-   il produttore visita ricorsivamente la directory data ed, eventualmente tutte le sottodirectory e mette nella coda
    il nome di ogni directory individuata
-   i consumatori prelevano dalla coda i nomi delle directories e stampano il loro contenuto  (nomi dei file)
-   la coda deve essere realizzata con una LinkedList. Ricordiamo che una Linked List non è una struttura thread-safe.
    Dalle API JAVA “Note that the implementation is not synchronized. If multiple threads access a linked list
    concurrently, and at least one of the threads modifies the list structurally, it must be synchronized externally”
*/

public class FileCrawlerMain {
    static int k = 10;
    static boolean working = true;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please specify the <path>");
        } else {
            String path = args[0];
            FileCrawlerQueue queue = new FileCrawlerQueue();

            Thread P = new Thread(new FileCrawlerProducer(path, queue));
            P.start();

            for (int i = 0; i < k; i++) {
                Thread T = new Thread(new FileCrawlerConsumer(queue));
                T.setName("T" + i);
                T.start();
            }

            try { P.join(); } catch (InterruptedException ignored) {}
            System.out.println("\t\t\tProducer done");
            working = false;
        }
    }
}
