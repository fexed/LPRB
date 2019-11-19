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
    @Override
    public void run() {
        boolean running = true;
        ServerSocketChannel srvSkt = null;
        SocketChannel skt = null;
        Selector selector;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            srvSkt = ServerSocketChannel.open();
            srvSkt.socket().bind(new InetSocketAddress(1337));
            srvSkt.configureBlocking(false);
            selector = Selector.open();
            srvSkt.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println(Thread.currentThread().getName() + ": server online");
            do {
                do { skt = srvSkt.accept(); } while(skt == null);
                threadPool.execute(new EchoClientHandler(skt));                 //Gestione client connesso
            } while (running);
            threadPool.shutdown();
            try { threadPool.awaitTermination(1, TimeUnit.SECONDS); }
            catch (InterruptedException ignored) {}
            srvSkt.close();                                                     //Chiusura della connessione
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        new Thread(new EchoServer()).start();
    }
}
