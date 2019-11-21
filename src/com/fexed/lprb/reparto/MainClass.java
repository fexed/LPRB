package com.fexed.lprb.reparto;

/*
Il Reparto di Ortopedia dell’ospedale della città di Marina è gestito da una equipe di 10 medici, numerati da 1 a 10.
I pazienti del reparto devono essere visitati dai medici e sono distinti in base ad un codice di gravità/urgenza della
prestazione

- Codice rosso: hanno priorità su tutti e la loro visita richiede l’intervento esclusivo di tutti i medici dell’equipe.
- Codice giallo: la visita richiede l’intervento esclusivo di un solo medico, identificato dall’indice i, perchè quel
  medico ha una particolare specializzazione
- Codice bianco: la visita richiede l’intervento esclusivo di un qualsiasi medico


Il codice rosso ha priorità su tutti nell’accesso alle risorse del reparto, il codice giallo ha priorità sul codice bianco.

Nessuna visita può essere interrotta

Il gestore del reparto deve coordinare il lavoro dei medici (i.e. l’accesso dei pazienti alle visite).

Scrivere un programma JAVA che simuli il comportamento dei pazienti e del gestore del reparto.

Il programma riceve in ingresso il numero di pazienti in codice bianco, giallo, rosso ed attiva un thread per ogni
paziente.
Ogni utente accede k volte al reparto, con k generato casualmente. Simulare l'intervallo di tempo che intercorre tra un
accesso ed il successivo e l'intervallo di permanenza in visita mediante il metodo sleep.
Il programma deve terminare quando tutti i pazienti utenti hanno completato le visite in reparto.
*/

import java.util.Random;

/**
 * @author Federico Matteoni
 */
public class MainClass {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Please specify the number of patients in the <red>, <yellow> and <white> queue.");
        } else {
            int r = 0, y = 0, w = 0;
            boolean err = false;

            try {
                r = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.err.println("Please specify a valid number for the red queue");
                err = true;
            }
            try {
                y = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex) {
                System.err.println("Please specify a valid number for the yellow queue");
                err = true;
            }
            try {
                w = Integer.parseInt(args[2]);
            } catch (NumberFormatException ex) {
                System.err.println("Please specify a valid number for the white queue");
                err = true;
            }
            if (err) System.exit(1);

            Reparto rep = new Reparto();

            for (int i = 0; i < r; i++) {
                new Thread(new Patient(rep, "RED"+i, 0, -1)).start();
            }
            for (int i = 0; i < y; i++) {
                Random rnd = new Random(System.currentTimeMillis());
                new Thread(new Patient(rep, "YLW"+i, 1, rnd.nextInt(10))).start();
            }
            for (int i = 0; i < w; i++) {
                new Thread(new Patient(rep, "WHT"+i, 2, -1)).start();
            }
        }
    }
}
