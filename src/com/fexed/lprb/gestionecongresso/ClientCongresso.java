package com.fexed.lprb.gestionecongresso;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * @author Federico Matteoni
 */
public class ClientCongresso implements Runnable {
    private int porta;

    private ClientCongresso(int porta) {
        this.porta = porta;
    }

    private void printMenu() {
        System.out.println("****\tMenù di accesso alla piattaforma");
        System.out.println("****\t\t1. Registrare uno speaker per una sessione");
        System.out.println("****\t\t2. Visualizzare il programma del congresso");
        System.out.println("****\t\t0. Uscire");
        System.out.print("****\t\t> ");
    }

    private void printCalendario(InterfacciaCongresso congresso) throws RemoteException {
        GiornataCongresso[] giornate = congresso.getGiornate();
        for (int i = 0; i < 3; i++) {
            System.out.println("Giornata " + giornate[i].nGiornata);
            SessioneCongresso[] sessioni = congresso.getSessioni(i);
            for (int j = 0; j < 12; j++) {
                System.out.print("S" + sessioni[j].nSessione + "\t");
                InterventoCongresso[] interventi = congresso.getInterventi(i, j);
                for (int k = 0; k < 5; k++) {
                    System.out.print(k+1 + ". ");
                    try {
                        System.out.print(interventi[k].nomeSpeaker + ", ");
                    } catch (NullPointerException ex) {System.out.print("____, ");}
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void run() {
        InterfacciaCongresso congresso;

        try {
            Registry r = LocateRegistry.getRegistry(this.porta);
            congresso = (InterfacciaCongresso) r.lookup("CONGRESSO-SERVER");

            if (congresso != null) {
                System.out.println("Client connesso al server su porta " + this.porta);
                int n;
                Scanner scn = new Scanner(System.in);

                do {
                    printMenu();
                    n = scn.nextInt();

                    switch (n) {
                        case 1:
                            System.out.println("Raccolta informazioni dal server");
                            try {
                                int giorno, sessione, intervento;
                                String name;

                                do {
                                    System.out.print("A quale giornata ci si vuole registrare? 0 per annullare\n[1-3] ");
                                    giorno = scn.nextInt();
                                } while (giorno < 0 || giorno > 3);
                                if (giorno != 0) {
                                    //congresso.getGiornate()[giorno - 1];
                                    do {
                                        System.out.print("A quale sessione ci si vuole iscrivere?\n[1-12] ");
                                        sessione = scn.nextInt();
                                    } while(sessione < 1 || sessione > 12);

                                    //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1];
                                    do {
                                        System.out.print("A quale intervento ci si vuole iscrivere?\n[1-5] ");
                                        intervento = scn.nextInt();
                                    } while (intervento < 1 || intervento > 5);

                                    if (congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1] == null) {
                                        System.out.print("Perfetto, a che nome? ");
                                        name = scn.next();
                                        if (congresso.newIntervento(giorno - 1, sessione - 1, intervento - 1, name)) {
                                            System.out.println("Speaker " + congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1].nomeSpeaker + " registrato con successo!");
                                        } else System.err.println("Errore");
                                        //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1].interventi[intervento - 1];
                                    } else {
                                        //congresso.getGiornate()[giorno - 1].sessioni[sessione - 1].interventi[intervento - 1];
                                        System.err.println("Spiacenti, intervento già prenotato da " + congresso.getInterventi(giorno - 1, sessione - 1)[intervento - 1].nomeSpeaker);
                                    }
                                }
                            }
                            catch (Exception ex) { System.err.println("Errore"); ex.printStackTrace(); }
                            break;
                        case 2:
                            System.out.println("Recupero del programma dal server");
                            if (congresso.getGiornate() == null)
                                System.out.println("Il congresso è ancora vuoto.");
                            else {
                                printCalendario(congresso);
                            }
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
        } catch (Exception ex) { System.err.println("Errore"); ex.printStackTrace(); }

    }

    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: server <porta>");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 1024) throw new NumberFormatException();
                Thread T = new Thread(new ClientCongresso(port));
                T.setName("Client");
                T.start();
            } catch (NumberFormatException ex) { System.err.println("Il parametro inserito non è una porta valida"); }
        }
    }
}
