package com.fexed.lprb.filecrawler;

import java.io.File;

/**
 @author Federico Matteoni
 @link https://elearning.di.unipi.it/mod/assign/view.php?id=6968
 */
public class FileCrawlerConsumer implements Runnable {
    public FileCrawlerQueue queue;  //La coda condivisa dalla quale estrarre le directory

    /**
     * Costruttore del consumatore. Prepara il riferimento alla coda condivisa
     * @param queue La coda condivisa dalla quale estrarre le directory
     */
    public FileCrawlerConsumer(FileCrawlerQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        do {
            String path = this.queue.get(); //Estraggo l'elemento in testa alla coda (ved. FileCrawlerQueue)
            while (path != null) {          //Controllo che l'elemento sia una stringa valida
                File dir = new File(path);
                if (dir.isDirectory()) {    //Dovrebbe essere garantito, ma controllo che sia una directory
                    String[] files = dir.list();
                    if (files != null) {
                        for (String file : files) {
                            File newfile = new File(path + "/" + file);
                            if (newfile.isFile())   //Stampo il nome del file trovato, ma solo se non è una directory
                                System.out.println(Thread.currentThread().getName() + " FILE\t\t" + file + "\t" + newfile.getParent());
                        }
                    }
                }
                path = this.queue.get();    //Estraggo il prossimo elemento dalla coda (ved. FileCrawlerQueue)
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {} //Attendo se la lista è vuota
        } while (this.queue.peek() != null); //Concludo se la lista risulta ancora vuota: il produttore ha finito
        System.out.println("\t\t\t" + Thread.currentThread().getName() + " fine");
    }
}
