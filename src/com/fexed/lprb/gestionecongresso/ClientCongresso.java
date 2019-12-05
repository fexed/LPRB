package com.fexed.lprb.gestionecongresso;

import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class ClientCongresso implements Runnable {

    private void printMenu() {
        System.out.println("\n\n****\tMenù di accesso alla piattaforma");
        System.out.println("****\t\t1. Registrare uno speaker per una sessione");
        System.out.println("****\t\t2. Visualizzare il programma del congresso");
        System.out.println("****\t\t0. Uscire");
        System.out.print("****\t\t> ");
    }

    @Override
    public void run() {
        int n;
        Scanner s = new Scanner(System.in);

        do {
            printMenu();
            n = s.nextInt();

            switch (n) {
                case 1:
                    //TODO
                    System.out.println("Raccolta informazioni dal server");
                    break;
                case 2:
                    //TODO
                    System.out.println("Recupero del programma dal server");
                    break;
                case 0:
                    System.out.println("****\tGrazie per aver usato la piattaforma, arrivederci!");
                    break;
                default:
                    System.err.println("Comando non riconosciuto, riprovare.");
                    n = -1;
                    break;
            }
        } while (n != 0);

    }

    public static void main(String[] args) {
        Thread T = new Thread(new ClientCongresso());
        T.setName("Client");
        T.start();
    }
}
