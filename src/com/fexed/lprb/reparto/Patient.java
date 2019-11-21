package com.fexed.lprb.reparto;

import java.util.Random;

/**
 * @author Federico Matteoni
 */
public class Patient implements Runnable {
    public String name;
    public int code;
    public int medic;
    public int k;
    public Random rnd;
    private Reparto rep;

    public Patient(Reparto rep, String name, int code, int medic) {
        this.rep = rep;
        this.name = name;
        this.code = code;
        this.medic = medic;
        this.rnd = new Random(System.currentTimeMillis());
        this.k = this.rnd.nextInt(5) + 1; //k = 1 -- 5
    }

    @Override
    public void run() {
        for (int i = 0; i < k; i++) {
            rep.queue(this);
            try { Thread.sleep((this.rnd.nextInt(1) + 1)*1000); }
            catch (InterruptedException ignored) {}
        }
    }
}
