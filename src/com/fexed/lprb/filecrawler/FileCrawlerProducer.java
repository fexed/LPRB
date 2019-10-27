package com.fexed.lprb.filecrawler;

import java.io.File;

/**
    @author Federico Matteoni
    @link https://elearning.di.unipi.it/mod/assign/view.php?id=6968
 */
public class FileCrawlerProducer implements Runnable {
    public String path;             //Il path da esplorare
    public FileCrawlerQueue queue;  //La coda condivisa con i consumatori, da riempire

    /**
     * Costruttore del thread produttore che si occupa di riempire correttamente gli attributi necessari
     * @param path Il path da esplorare
     * @param queue La coda condivisa con i consumatori, da riempire
     */
    public FileCrawlerProducer (String path, FileCrawlerQueue queue) {
        this.path = path;
        this.queue = queue;
    }

    /**
     * Funzione ricorsiva, usata per esplorare tutte le sottodirectory
     * @param path La directory attuale da esplorare
     */
    public void checkDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {    //Condizione necessaria per la corretta esplorazione e condizione d'uscita
            queue.append(path);     //Inserisco nella coda la nuova directory scovata
            String[] files = dir.list();
            if (files != null) {
                for (String file : files) {
                    File newfile = new File(path + "/" + file);
                    if (newfile.isDirectory()) {
                        checkDir(path + "/" + file);    //Entro nella sottodirectory
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        checkDir(this.path);    //Inizia l'esplorazione
        queue.closeQueue();     //Chiudo la coda segnalando che l'esplorazione è finita e il produttore termina
        System.out.println("\t\t\t" + Thread.currentThread().getName() + " fine");
    }
}
