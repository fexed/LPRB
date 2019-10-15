package com.fexed.lprb.postoffice;

import static java.lang.Thread.sleep;

public class Person implements Runnable {
    public int number;
    public final int duration;

    public Person(int number, int duration) {
        this.number = number;
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            System.out.println("[" + this.number + "] ***partito");
            sleep(duration*1000);
            System.out.println("[" + this.number + "] finito***");
        } catch (InterruptedException ignored) {}
    }
}
