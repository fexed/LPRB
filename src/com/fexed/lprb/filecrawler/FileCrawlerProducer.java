package com.fexed.lprb.filecrawler;

import java.io.File;

public class FileCrawlerProducer implements Runnable {
    public String path;
    public FileCrawlerQueue queue;

    public FileCrawlerProducer (String path, FileCrawlerQueue queue) {
        this.path = path;
        this.queue = queue;
    }

    public void checkDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            queue.append(path);
            String[] files = dir.list();
            for (String file : files) {
                File newfile = new File(path + "/" + file);
                if (newfile.isDirectory()) {
                    checkDir(path + "/" + file);
                }
            }
        }
    }

    @Override
    public void run() {
        checkDir(this.path);
    }
}
