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
public class EchoClient implements Runnable{
    private String strToEcho;

    public static void main(String[] args) {
        if (args.length >= 1) new Thread(new EchoClient(args[0])).start();
        else new Thread(new EchoClient()).start();
    }

    public EchoClient() {
        this.strToEcho = null;
    }

    public EchoClient(String strToEcho) {
        this.strToEcho = strToEcho;
    }

    @Override
    public void run() {
        try {
            if (this.strToEcho == null) {
                Scanner in = new Scanner(System.in);                                                //Input
                in.useDelimiter("\n");
                System.out.print("String to be echoed: ");
                strToEcho = in.next();
            }

            SocketChannel skt = SocketChannel.open();                                               //Connessione
            System.out.println("Connecting...");
            skt.connect(new InetSocketAddress("127.0.0.1", 1337));
            skt.configureBlocking(false);
            System.out.println("Connected. Sending \"" + strToEcho + "\"...");

            Selector selector = Selector.open();
            SelectionKey keyW = skt.register(selector, SelectionKey.OP_WRITE);
            SelectionKey keyR = skt.register(selector, SelectionKey.OP_READ);

            ByteBuffer bBuff = ByteBuffer.wrap(strToEcho.getBytes(StandardCharsets.UTF_8));               //Scrittura
            int n;
            do { n = ((SocketChannel) keyW.channel()).write(bBuff);}  while(n > 0);
            System.out.println("Data sent. Awating echo...");

            bBuff = ByteBuffer.allocate(128);
            do {bBuff.clear(); n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n == 0);  //Attesa
            do { n = ((SocketChannel) keyR.channel()).read(bBuff); } while (n > 0);                 //Lettura

            bBuff.flip();                                                                           //Stampa
            System.out.println(StandardCharsets.UTF_8.decode(bBuff));

            skt.close();
        } catch (IOException ignored) {}
    }
}
