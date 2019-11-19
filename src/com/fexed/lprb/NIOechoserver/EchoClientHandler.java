package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.net.Socket;
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
        ByteBuffer bBuff = ByteBuffer.allocate(128);
        StringBuilder sBuff = new StringBuilder();
        String str;
        SelectionKey keyR, keyW;

        System.out.println(Thread.currentThread().getName() + "\tConnesso");

        try {
            skt.configureBlocking(false);
            Selector selector = Selector.open();
            keyR = skt.register(selector, SelectionKey.OP_READ);
            keyW = skt.register(selector, SelectionKey.OP_WRITE);

            int n;
            do {bBuff.clear(); n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n == 0);  //Wait
            do {n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n > 0);
            bBuff.flip();
            sBuff.append(StandardCharsets.UTF_8.decode(bBuff).toString());

            str = sBuff.toString();
            System.out.println(Thread.currentThread().getName() + "\t\"" + str + "\"");
            str = str.concat("\tEchoed by Fexed's Server");
            bBuff = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            do { n = ((SocketChannel) keyW.channel()).write(bBuff); }  while(n > 0);

        } catch (IOException ignored) {}

        System.out.println(Thread.currentThread().getName() + "\tDisconnesso");
    }
}