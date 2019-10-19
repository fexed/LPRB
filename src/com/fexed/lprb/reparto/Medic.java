package com.fexed.lprb.reparto;

import java.util.concurrent.locks.Lock;

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

    public synchronized void workFor(Patient p, int time, String code, String shortCode) throws InterruptedException {
        System.out.println(code + "+" + (this.number + 1) + "\t\t" + p.name + "\t" + shortCode);
        this.isFree = false;
        sleep(time * 1000);
        this.isFree = true;
        System.out.println(code + "-" + (this.number + 1) + "\t\t" + p.name + "\t" + shortCode);
        this.notifyAll();
    }
}