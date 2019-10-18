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

        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        Patient p;
        for (int i = 0; i < 9; i++) medics[i] = new Medic(i);
        for (int i = 0; i < this.nr; i++) { //red
            new Thread(new Patient(rnd.nextInt(5) + 1, 0, -1, this)).start(); //k = 1 -- 5
        }
        for (int i = 0; i < this.ny; i++) { //yellow
            new Thread(new Patient(rnd.nextInt(8) + 1, 1, rnd.nextInt(10), this)).start();
            //k = 1 -- 8
        }
        for (int i = 0; i < this.nw; i++) { //white
            new Thread(new Patient(rnd.nextInt(10) + 1, 2, -1, this)).start();
            //k = 1 -- 10
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

        synchronized (this.medics) {
            try {
                System.out.println("Paziente in codice rosso");
                sleep((rnd.nextInt(5)+1)/**1000*/);
                System.out.println("Fine codice rosso");
            } catch (InterruptedException ignored) {}
        }
    }

    public void handleYellow(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        synchronized (this.medics[p.code]) {
            try {
                System.out.println("Paziente in codice giallo per medico " + p.medic);
                sleep((rnd.nextInt(5)+1)/**1000*/);
                System.out.println("Fine per medico " + p.medic);
                this.medics[p.code].notifyAll();
            } catch (InterruptedException ignored) {}
        }
    }

    public void handleWhite(Patient p) {
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        synchronized (this.medics) {
            try {
                System.out.println("Paziente in codice bianco");
                sleep((rnd.nextInt(5)+1)/**1000*/);
                System.out.println("Fine codice bianco");
            } catch (InterruptedException ignored) {}
        }
    }
}
