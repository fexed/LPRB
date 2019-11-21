package com.fexed.lprb.UDPPing;

public class MainClass {
    public static void main(String[] args) throws InterruptedException {
        Thread server = new Thread(new UDPPingServer(1337));
        Thread client = new Thread(new UDPPingClient("localhost", 1337));

        server.start();
        Thread.sleep(500);
        client.start();
        client.join();
        System.out.println("Client finished, wait 1m for server timeout");
        server.join();
    }
}
