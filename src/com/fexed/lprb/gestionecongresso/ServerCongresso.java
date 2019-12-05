package com.fexed.lprb.gestionecongresso;

public class ServerCongresso implements Runnable {
    @Override
    public void run() {

    }

    public static void main(String[] args) {
        Thread T = new Thread(new ServerCongresso());
        T.setName("Server del Congresso");
        T.start();
    }
}
