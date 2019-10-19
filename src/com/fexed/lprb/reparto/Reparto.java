package com.fexed.lprb.reparto;

import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Reparto {
    private int nr, ny, nw;
    private final Medic[] medics;

    public Reparto(int red, int yellow, int white) {
        this.nr = red;
        this.ny = yellow;
        this.nw = white;
        this.medics = new Medic[10];
        for (int i = 0; i < 10; i++) medics[i] = new Medic(i);

        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        for (int i = 0; i < this.nr; i++) { //red
            Thread t = new Thread(new Patient(rnd.nextInt(5) + 1, 0, -1, this));
            //k = 1 -- 5
            t.setName("R" + i);
            t.start();
        }
        for (int i = 0; i < this.ny; i++) { //yellow
            Thread t = new Thread(new Patient(rnd.nextInt(8) + 1, 1, rnd.nextInt(10), this));
            //k = 1 -- 8
            t.setName("Y" + i);
            t.start();
        }
        for (int i = 0; i < this.nw; i++) { //white
            Thread t = new Thread(new Patient(rnd.nextInt(10) + 1, 2, -1, this));
            //k = 1 -- 10
            t.setName("W" + i);
            t.start();
        }
        System.out.println("  CODE  \t\tNM\tW\tY\tR");
    }

    public void enter(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        //p enters the reparto
        switch (p.code) {
            case 0: //red code
                handleRed(p);
                break;
            case 1: //yellow code
                handleYellow(p);
                break;
            case 2: //white code
                handleWhite(p);
                break;
        }
    }

    public void handleRed(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        //System.out.println("\t\t\t\t\t" + p.name + " in attesa");
        synchronized (this.medics) {
            try {
                for (int i = 0; i < 10; i++) {
                    try {
                        this.medics[i].redCodeSignal = true;
                        this.medics[i].isFree = false;
                        this.medics[i].wait();
                    } catch (IllegalMonitorStateException ignored) {}
                }
                System.out.println("REDCODE+\t\t" + p.name + "\t\t\tR");
                sleep((rnd.nextInt(5) + 1) * 1000);
                System.out.println("REDCODE-\t\t" + p.name + "\t\t\tR");
            } catch (InterruptedException ignored) {}
            for (int i = 0; i < 10; i++) {
                try {
                    this.medics[i].redCodeSignal = false;
                    this.medics[i].isFree = true;
                    this.medics[i].notify();
                } catch (IllegalMonitorStateException ignored) {}
            }
        }
    }

    public void handleYellow(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        //System.out.println("\t\t\t\t\t" + p.name + " in attesa");
        Medic m;
        synchronized (this.medics) {
            m = this.medics[p.medic];
        }
        synchronized (m) {
            try {
                while (m.redCodeSignal) m.wait();
                m.isFree = false;
                System.out.println("YELCOD+" + (p.medic+1) + "\t\t" + p.name + "\t\tY");
                sleep((rnd.nextInt(5)+1)*1000);
                System.out.println("YELCOD-" + (p.medic+1) + "\t\t" + p.name + "\t\tY");
                m.isFree = true;
                m.notify();
            } catch (InterruptedException | IllegalMonitorStateException ignored) {}
        }
    }

    public void handleWhite(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        //System.out.println("\t\t\t\t\t" + p.name + " in attesa");
        Medic m = null;
        synchronized (this.medics) {
            do {
                for (int i = 0; i < 10; i++)
                    if (this.medics[i].isFree) {
                        m = this.medics[i];
                        break;
                    }
            } while (m == null);
        }
        synchronized (m) {
            try {
                while (m.redCodeSignal) m.wait();
                m.isFree = false;
                System.out.println("WHTCOD+" + (m.number+1) + "\t\t"+ p.name + "\tW");
                sleep((rnd.nextInt(5) + 1) * 1000);
                System.out.println("WHTCOD-" + (m.number+1) + "\t\t"+ p.name + "\tW");
                m.isFree = true;
                m.notify();
            } catch (InterruptedException | IllegalMonitorStateException ignored) {}
        }
    }
}
