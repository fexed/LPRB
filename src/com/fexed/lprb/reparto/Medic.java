package com.fexed.lprb.reparto;

public class Medic {
    public int number;

    public Medic(int number) {
        this.number = number;
    }

    public synchronized void use(String name, int code, int time) { //0R, 1Y, 2W
        String codeStr = "";
        switch (code) {
            case 0: codeStr = "RED"; break;
            case 1: codeStr = "YLW"; break;
            case 2: codeStr = "WHT"; break;
        }
        System.out.println("IN\tM" + this.number + "\tP" + name + "\t\t\t" + codeStr + " IN");
        try { Thread.sleep(time*1000); }
        catch (InterruptedException ignored) {}
        System.out.println("OUT\tM" + this.number + "\tP" + name + "\t\t\t" + codeStr + " OUT");
        this.notifyAll();
    }
}
