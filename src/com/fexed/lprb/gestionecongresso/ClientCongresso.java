package com.fexed.lprb.gestionecongresso;

public class ClientCongresso implements Runnable {
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread T = new Thread(new ClientCongresso());
        T.setName("Client");
        T.start();
    }
}
