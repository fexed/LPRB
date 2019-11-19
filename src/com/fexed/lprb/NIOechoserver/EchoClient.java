package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
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
            Scanner in = new Scanner(System.in);
            System.out.print("String to be echoed: ");
            String str = in.next();

            SocketChannel skt = SocketChannel.open();
            skt.connect(new InetSocketAddress("127.0.0.1", 1337));

            ByteBuffer bBuff = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));
            bBuff.flip();
            while (bBuff.hasRemaining()) skt.write(bBuff);
            bBuff.clear();
            skt.read(bBuff);

            System.out.println(StandardCharsets.UTF_8.decode(bBuff));

            skt.close();
        } catch (IOException ignored) {}

    }
}
