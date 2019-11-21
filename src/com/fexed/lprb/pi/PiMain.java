package com.fexed.lprb.pi;

/**
 * @author Federico Matteoni
 */
public class PiMain {
    /*
    Scrivere un programma che attiva un thread T che effettua il calcolo approssimato di π. Il programma principale
    riceve in input da linea di comando un parametro che indica il grado di accuratezza (accuracy) per il calcolo di π
    ed il tempo massimo di attesa dopo cui il programma principale interrompe il thread T.
    Il thread T effettua un ciclo infinito per il calcolo di π usando la serie di Gregory-Leibniz
                        (π = 4/1 – 4/3 + 4/5 - 4/7 + 4/9 - 4/11 ...)

    Il thread esce dal ciclo quando una delle due condizioni seguenti risulta verificata:
        1) il thread è stato interrotto
        2) la differenza tra il valore stimato di π ed il valore Math.PI (della libreria JAVA) è minore di accuracy
    */

    public static void main(String[] args) {
        Thread T, S;
        if (args.length != 2) {
            System.out.println("Please specify <accuracy> and <time limit> from the command line.");
        } else {
            PiCalculator calculator = new PiCalculator(Float.parseFloat(args[0]));
            T = new Thread(calculator);
            T.start();
            S = new Thread(new PiStopper(calculator, Integer.parseInt(args[1])));
            S.start();

            try {
                T.join();
            } catch (InterruptedException ignored) {}
        }
    }
}
