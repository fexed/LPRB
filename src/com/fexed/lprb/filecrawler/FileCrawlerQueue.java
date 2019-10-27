package com.fexed.lprb.filecrawler;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 @author Federico Matteoni
 @link https://elearning.di.unipi.it/mod/assign/view.php?id=6968
 */
public class FileCrawlerQueue {
    private LinkedList<String> queue;   //La lista
    private int directories;            //Statistica sulle directory trovate

    /**
     * Costruttore della coda condivisa. Inizializza la lista e le statistiche
     */
    public FileCrawlerQueue() {
        this.queue = new LinkedList<>();
        this.directories = 0;
    }

    /**
     * Inserisce {@code file} alla fine della coda condivisa
     * @param file Il file da inserire nella coda
     * @return {@code true} (come specificato da {@link LinkedList#add})
     */
    public synchronized boolean append(String file) {
        this.directories++;             //Incremento il numero di sottodirectory scovate
        return this.queue.add(file);
    }

    /**
     * Inserisce il token {@code null} al termine della coda e stampa il messaggio di conclusione
     */
    public synchronized void closeQueue() {
        this.queue.add(null);
        System.out.println("\t\t\t" + Thread.currentThread().getName() + " ha scoperto " + this.directories + " directory");
    }

    /**
     * Ottiene l'elemento di testa della coda e lo rimuove
     * @return L'elemento di testa della coda, o {@code null} se la coda è vuota
     */
    public synchronized String get() {
        try {
            return this.queue.removeFirst();
        } catch (NoSuchElementException ex) { return null; }
    }

    /**
     * Mostra l'elemento di testa della coda, senza rimuoverlo
     * @return L'elemento di testa della coda
     */
    public synchronized String peek() {
        return this.queue.peek();
    }
}
