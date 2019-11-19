package com.fexed.lprb.UDPPing;

public class UDPPingServer implements Runnable{
    public static void main(String[] args) throws InterruptedException {
        new Thread(new UDPPingServer()).start();
    }

    @Override
    public void run() {

    }
}
