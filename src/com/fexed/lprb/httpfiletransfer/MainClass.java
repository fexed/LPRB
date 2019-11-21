package com.fexed.lprb.httpfiletransfer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Federico Matteoni
 */
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
