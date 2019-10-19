package com.fexed.lprb.reparto;

import static java.lang.Thread.sleep;

public class Medic {
    public int number;
    public boolean isFree;
    public boolean redCodeSignal;

    public Medic (int number) {
        this.number = number;
        this.isFree = true;
        this.redCodeSignal = false;
    }

    public synchronized void workFor(Patient p, int n, String code, String shortcode) throws InterruptedException {
        System.out.println(code + "+" + (this.number + 1) + "\t\t" + p.name + "\t" + shortcode);
        this.isFree = false;
        sleep(n * 1000);
        this.isFree = true;
        System.out.println(code + "-" + (this.number + 1) + "\t\t" + p.name + "\t" + shortcode);
        this.notify();
    }
}
