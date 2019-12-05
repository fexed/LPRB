package com.fexed.lprb.gestionecongresso;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class ClientCongresso implements Runnable {
    private int porta;

    public ClientCongresso(int porta) {
        this.porta = porta;
    }

    private void printMenu() {
        System.out.println("\n\n****\tMenù di accesso alla piattaforma");
        System.out.println("****\t\t1. Registrare uno speaker per una sessione");
        System.out.println("****\t\t2. Visualizzare il programma del congresso");
        System.out.println("****\t\t0. Uscire");
        System.out.print("****\t\t> ");
    }

    @Override
    public void run() {
        InterfacciaCongresso congresso = null;

        try {
            Registry r = LocateRegistry.getRegistry(this.porta);
            congresso = (InterfacciaCongresso) r.lookup("CONGRESSO-SERVER");

            if (congresso != null) {
                System.out.println("Client connesso al server su porta " + this.porta);
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
            } else System.err.println("Errore nel caricamento del congresso.");
        } catch (Exception ex) { System.err.println("Errore durante l'inizializzazione: " + ex.getMessage()); }

    }

    public static void main(String[] args) {
        //TODO porta da linea di comando
        Thread T = new Thread(new ClientCongresso(1337));
        T.setName("Client");
        T.start();
    }
}
