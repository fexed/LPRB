package com.fexed.lprb.NIOechoserver;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Federico Matteoni
 */
public class EchoClientHandler implements Runnable {
    private SocketChannel skt;

    public EchoClientHandler(SocketChannel skt) {
        this.skt = skt;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "\tConnesso");

        System.out.println(Thread.currentThread().getName() + "\tDisconnesso");
    }
}
