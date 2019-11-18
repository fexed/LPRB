package com.fexed.lprb.NIOechoserver;

import java.nio.channels.SocketChannel;

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

    }
}
