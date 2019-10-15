package com.fexed.lprb.pi;

public class PiCalculator implements Runnable {
    private float accuracy;
    public boolean isRunning;

    public PiCalculator(float accuracy) {
        this.accuracy = accuracy;
        this.isRunning = true;
    }

    @Override
    public void run() {
        double pi = 0, denom = 1;
        int flipsign = 0;

        System.out.println("Calcolatore: " + this.accuracy + "");
        while (this.isRunning) {
            pi += Math.pow(-1, flipsign)*(4 / denom);
            denom += 2;
            flipsign++;

            System.out.print("");
            if (Math.abs(pi - Math.PI) < this.accuracy) this.isRunning = false;
        }

        System.out.println("\n\n**π = " + pi + "\n**Iterations: " + flipsign);
    }
}
