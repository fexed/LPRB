package com.fexed.lprb.reparto;

import java.util.Random;

public class Reparto {
    private Medic[] medics;
    private boolean[] isOccupied;
    private final Object isOccupiedLock;
    private boolean isRedCode;
    private final Object redCodeLock;
    private Random rnd;

    public Reparto() {
        this.medics = new Medic[10];
        this.isOccupied = new boolean[10];
        this.isOccupiedLock = new Object();
        this.isRedCode = false;
        this.rnd = new Random(System.currentTimeMillis());
        this.redCodeLock = new Object();

        for (int i = 0; i < 10; i++) {
            this.medics[i] = new Medic(i);
            this.isOccupied[i] = false;
        }
    }

    private int getFirstFree() {
        if (this.isRedCode) return -1;
        synchronized (this.isOccupiedLock) {
            for (int i = 0; i < 10; i++) {
                if (!this.isOccupied[i]) return i;
            }
        }
        return -1;
    }

    private int getNFree(){
        int n = 0;
        synchronized (this.isOccupiedLock) {
            for (int i = 0; i < 10; i++) {
                if (!this.isOccupied[i]) n++;
            }
        }
        return n;
    }

    public void queue(Patient p) { //0R, 1Y, 2W
        switch (p.code) {
            case 0: handleRedCode(p); break;
            case 1: handleYellowCode(p); break;
            case 2: handleWhiteCode(p); break;
            default: break;
        }
    }

    public void handleRedCode(Patient p) {
        synchronized (redCodeLock) {
            this.isRedCode = true;
            while(getNFree() != 10) {
                try { Thread.sleep(1); }
                catch (InterruptedException ignored) {}
            }

            synchronized (this.isOccupiedLock) {
                for (int i = 0; i < 10; i++) {
                    this.isOccupied[i] = true;
                }
            }

            System.out.println("IN\t---\tP" + p.name + "\t\t\tRED IN");
            try { Thread.sleep((this.rnd.nextInt(5) + 1)*1000); }
            catch (InterruptedException ignored) {}
            System.out.println("OUT\t---\tP" + p.name + "\t\t\tRED OUT");

            synchronized (this.isOccupiedLock) {
                for (int i = 0; i < 10; i++) {
                    this.isOccupied[i] = false;
                }
                this.isOccupiedLock.notifyAll();
            }
            this.isRedCode = false;
            this.redCodeLock.notifyAll();
        }
    }

    public void handleYellowCode(Patient p) {
        do {
            try { Thread.sleep(1); }
            catch (InterruptedException ignored) {}
        } while (this.isOccupied[p.medic] || this.isRedCode);
        this.isOccupied[p.medic] = true;
        this.medics[p.medic].use(p.name, p.code, (this.rnd.nextInt(5) + 1));
        this.isOccupied[p.medic] = false;
    }

    public void handleWhiteCode(Patient p) {
        int nmedic = -1;
        do {
            try { Thread.sleep(1); }
            catch (InterruptedException ignored) {}
        } while (this.isRedCode);
        do { nmedic = getFirstFree(); } while (nmedic == -1);
        this.isOccupied[nmedic] = true;
        this.medics[nmedic].use(p.name, p.code, (this.rnd.nextInt(5) + 1));
        this.isOccupied[nmedic] = false;
    }
}
