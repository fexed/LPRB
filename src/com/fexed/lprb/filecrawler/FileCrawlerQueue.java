package com.fexed.lprb.filecrawler;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FileCrawlerQueue {
    private LinkedList<String> queue;

    public FileCrawlerQueue() {
        this.queue = new LinkedList<>();
    }

    public synchronized boolean append(String file) {
        return this.queue.add(file);
    }

    public synchronized String get() {
        try {
            return this.queue.removeFirst();
        } catch (NoSuchElementException ex) { return null; }
    }
}
