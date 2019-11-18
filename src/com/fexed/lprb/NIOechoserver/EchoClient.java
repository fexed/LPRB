package com.fexed.lprb.NIOechoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author Federico Matteoni
 */
public class EchoClient {
    public static void main(String[] args) {
        try {
            SocketChannel skt = SocketChannel.open();
            skt.connect(new InetSocketAddress("127.0.0.1", 1337));

        } catch (IOException ignored) {}

    }
}
