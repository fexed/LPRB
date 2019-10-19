package com.fexed.lprb.reparto;

import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Reparto {
    private int nr, ny, nw;
    private final Medic[] medics;
    private final Object redCode;

    public Reparto(int red, int yellow, int white) {
        this.nr = red;
        this.ny = yellow;
        this.nw = white;
        this.medics = new Medic[10];
        for (int i = 0; i < 10; i++) medics[i] = new Medic(i);
        this.redCode = new Object();

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
                for (int i = 0; i < 10; i++) this.medics[i].redCodeSignal = true;
                System.out.println("R!!+\tPaziente " + p.name + " in codice rosso");
                sleep((rnd.nextInt(5) + 1) * 1000);
                System.out.println("R!!-\tFine codice rosso");
            } catch (InterruptedException ignored) {}
            for (int i = 0; i < 10; i++) {
                try {
                    this.medics[i].redCodeSignal = false;
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
                System.out.println("Y!+\t\tPaziente " + p.name + " in codice giallo per medico " + (p.medic+1));
                sleep((rnd.nextInt(5)+1)*1000);
                System.out.println("Y!-\t\tFine per medico " + (p.medic+1));
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
                System.out.println("W+\t\tPaziente " + p.name + " in codice bianco con medico " + (m.number+1));
                sleep((rnd.nextInt(5) + 1) * 1000);
                System.out.println("W-\t\tFine codice bianco con medico " + (m.number+1));
                m.isFree = true;
                m.notify();
            } catch (InterruptedException | IllegalMonitorStateException ignored) {}
        }
    }
}
