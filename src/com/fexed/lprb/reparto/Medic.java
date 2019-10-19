package com.fexed.lprb.reparto;

public class Medic {
    public int number;
    public boolean isFree;
    public boolean redCodeSignal;

    public Medic (int number) {
        this.number = number;
        this.isFree = true;
        this.redCodeSignal = false;
    }
}
