package com.fexed.lprb.UDPPing;

public class UDPPingClient implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new UDPPingClient()).start();
    }

    @Override
    public void run() {

    }
}
