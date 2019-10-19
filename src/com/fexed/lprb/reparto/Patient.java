package com.fexed.lprb.reparto;

import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Patient implements Runnable {
    public int times, code, medic;
    public String name;
    private Reparto r;

    public Patient(int times, int code, int medic, Reparto r) { //0 R, 1 Y, 2 W
        this.times = times;
        this.code = code;
        this.medic = medic;
        this.r = r;
    }

    @Override
    public void run() {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        this.name = Thread.currentThread().getName();
        for (; this.times > 0; this.times--) {
            r.enter(this);
            try {
                sleep((rnd.nextInt(5)+1)/**1000*/); //simulate time between accesses
            } catch (InterruptedException ignored) {}
        }
    }
}
