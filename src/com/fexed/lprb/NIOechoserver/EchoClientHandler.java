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

        System.out.println(Thread.currentThread().getName() + "\tConnected");                       //Handler online

        try {
            skt.configureBlocking(false);
            Selector selector = Selector.open();
            keyR = skt.register(selector, SelectionKey.OP_READ);
            keyW = skt.register(selector, SelectionKey.OP_WRITE);                                   //Preparo i canali

            int n;
            do {bBuff.clear(); n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n == 0);  //Attesa
            do {n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n > 0);                  //Lettura
            bBuff.flip();
            sBuff.append(StandardCharsets.UTF_8.decode(bBuff).toString());
            str = sBuff.toString();                                                                 //Costruzione String

            if (str.equals("exit")) EchoServer.shutdownServer();                                    //Close on "exit"

            System.out.println(Thread.currentThread().getName() + "\t\"" + str + "\"");             //Output

            str = str.concat("\tEchoed by Fexed's Echo Server");                                    //Preparazione echo
            bBuff = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            do { n = ((SocketChannel) keyW.channel()).write(bBuff); }  while(n > 0);                //Invio echo

        } catch (IOException ignored) {}

        System.out.println(Thread.currentThread().getName() + "\tDisconnected");                    //Handler offline
    }
}