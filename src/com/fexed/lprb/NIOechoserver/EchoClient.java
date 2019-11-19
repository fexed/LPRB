package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class EchoClient {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);                                        //Input
            System.out.print("String to be echoed: ");
            String str = in.next();

            SocketChannel skt = SocketChannel.open();                                   //Connessione
            System.out.println("Connecting...");
            skt.connect(new InetSocketAddress("127.0.0.1", 1337));
            skt.configureBlocking(false);
            System.out.println("Connected. Sending \"" + str + "\"...");

            Selector selector = Selector.open();
            SelectionKey keyW = skt.register(selector, SelectionKey.OP_WRITE);
            SelectionKey keyR = skt.register(selector, SelectionKey.OP_READ);

            ByteBuffer bBuff = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));   //Scrittura
            int n;
            do { n = ((SocketChannel) keyW.channel()).write(bBuff); }  while(n > 0);
            System.out.println("Data sent. Awating echo...");

            bBuff = ByteBuffer.allocate(128);                                           //Lettura
            do { n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n > 0);

            bBuff.flip();                                                               //Stampa
            System.out.println(StandardCharsets.UTF_8.decode(bBuff));

            skt.close();
        } catch (IOException ignored) {}

    }
}
