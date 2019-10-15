package com.fexed.lprb.pi;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class PiStopper implements Runnable {
    private int time;
    private PiCalculator calculator;

    public PiStopper(PiCalculator calculator, int time) {
        this.calculator = calculator;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            System.out.println("Stopper: " + this.time + "s");
            sleep(this.time);
            calculator.isRunning = false;
        } catch (InterruptedException ignored) {}
    }
}
