package com.fexed.lprb.filecrawler;

import java.io.File;

public class FileCrawlerConsumer implements Runnable {
    public FileCrawlerQueue queue;

    public FileCrawlerConsumer(FileCrawlerQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        do {
            String path = this.queue.get();
            while (path != null) {
                File dir = new File(path);
                if (dir.isDirectory()) {
                    String[] files = dir.list();
                    for (String file : files) {
                        File newfile = new File(path + "/" + file);
                        if (newfile.isFile())
                            System.out.println(Thread.currentThread().getName() + "FILE\t\t" + file + "\t" + newfile.getParent());
                        else if (newfile.isDirectory())
                            System.out.println(Thread.currentThread().getName() + "DIR\t\t" + file + "\t" + newfile.getParent());
                    }
                }
                path = this.queue.get();
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        } while (FileCrawlerMain.working);
        System.out.println("\t\t\t" + Thread.currentThread().getName() + " done");
    }
}
