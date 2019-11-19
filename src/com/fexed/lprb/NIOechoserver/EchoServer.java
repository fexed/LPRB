package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Federico Matteoni
 */
public class EchoServer implements Runnable {
    static boolean running = true;
    @Override
    public void run() {
        ServerSocketChannel srvSkt = null;
        SocketChannel skt = null;
        Selector selector;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            srvSkt = ServerSocketChannel.open();                                  //Apertura del socket di ascolto
            srvSkt.socket().bind(new InetSocketAddress(1337));              //Configurazione del socket
            srvSkt.configureBlocking(false);
            selector = Selector.open();
            srvSkt.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(Thread.currentThread().getName() + ": server online");
            do {
                skt = srvSkt.accept();                                          //Accettazione e gestione dei client
                if (skt != null) threadPool.execute(new EchoClientHandler(skt));
                else Thread.sleep(500);
            } while (running);
            System.out.println(Thread.currentThread().getName() + ": server shutting down");
            threadPool.shutdown();
            try { threadPool.awaitTermination(1, TimeUnit.SECONDS); }
            catch (InterruptedException ignored) {}
            srvSkt.close();                                                     //Chiusura della connessione
            System.out.println(Thread.currentThread().getName() + ": server offline");
        } catch (IOException | InterruptedException e) { e.printStackTrace(); }
    }

    /**
     * Avvia la procedura di chiusura del server
     */
    public static void shutdownServer() {
        running = false;
    }

    public static void main(String[] args) {
        new Thread(new EchoServer()).start();
    }
}
