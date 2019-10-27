package com.fexed.lprb.filecrawler;

/**
    @author Federico Matteoni
    @link https://elearning.di.unipi.it/mod/assign/view.php?id=6968
 */
public class MainClass {
    static int k = 10;

    public static void main(String[] args) {
        /*
            FileCrawlerMain <path>
                path    (String)    Path da cui partire per eseguire l'analisi
         */
        if (args.length != 1) {
            System.err.println("Specificare il <path>");
        } else {
            String path = args[0];

            //Preparazione della coda condivisa
            FileCrawlerQueue queue = new FileCrawlerQueue();

            //Preparazione e avvio del thread produttore. Gli assegno un nome significativo usato durante la stampa
            Thread P = new Thread(new FileCrawlerProducer(path, queue));
            P.setName("P");
            P.start();

            //Preparazione e avvio dei k thread consumatori, anche loro con un nome significativo per la stampa
            for (int i = 0; i < k; i++) {
                Thread T = new Thread(new FileCrawlerConsumer(queue));
                T.setName("T" + i);
                T.start();
            }
        }
    }
}
