package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

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
        int bytesRead;
        ByteBuffer bBuff = null;
        StringBuilder sBuff = new StringBuilder();
        String str;
        SelectionKey keyR, keyW;

        System.out.println(Thread.currentThread().getName() + "\tConnesso");

        try {
            skt.configureBlocking(false);
            Selector selector = Selector.open();
            keyR = skt.register(selector, SelectionKey.OP_READ);
            keyW = skt.register(selector, SelectionKey.OP_WRITE);

            while (bBuff == null) bBuff = (ByteBuffer) keyR.attachment();
            sBuff.append(StandardCharsets.UTF_8.decode(bBuff).toString());

            str = sBuff.toString();
            System.out.println(Thread.currentThread().getName() + "\t" + str);
            str = str.concat("\tEchoed by Fexed's Server");
            bBuff = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            keyW.attach(bBuff);
        } catch (IOException ignored) {}

        System.out.println(Thread.currentThread().getName() + "\tDisconnesso");
    }
}