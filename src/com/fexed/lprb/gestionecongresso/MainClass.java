package com.fexed.lprb.gestionecongresso;

/*
Si progetti un’applicazione Client/Server per la gestione delle registrazioni ad un congresso. L’organizzazione del
congresso fornisce agli speaker delle varie sessioni un’interfaccia tramite la quale iscriversi ad una sessione, e la
possibilità di visionare i programmi delle varie giornate del congresso, con gli interventi delle varie sessioni.

Il server mantiene i programmi delle 3 giornate del congresso, ciascuno dei quali è memorizzato in una struttura dati
come quella mostrata in allegato, in cui ad ogni riga corrisponde una sessione (in tutto 12 per ogni giornata).
Per ciascuna sessione vengono memorizzati i nomi degli speaker che si sono registrati (al massimo 5).

        Sessione        Intervento1     Intervento2     ...     Intervento5
        S1              nomespeaker1    nomespeaker2            nomespeaker5
        S2              nomespeaker1    nomespeaker2            nomespeaker5
        ...
        S12             nomespeaker1    nomespeaker2            nomespeaker5

Il client può richiedere operazioni per:
    - registrare uno speaker ad una sessione;
    - ottenere il programma del congresso;
Il client inoltra le richieste al server tramite il meccanismo di RMI. Prevedere, per ogni possibile operazione una
gestione di eventuali condizioni anomale (ad esempio la richiesta di registrazione ad una giornata e/o sessione
inesistente oppure per la quale sono già stati coperti tutti gli spazi d’intervento)

Il client è implementato come un processo ciclico che continua a fare richieste sincrone fino ad esaurire tutte le
esigenze utente. Stabilire una opportuna condizione di terminazione del processo di richiesta.
Client, server e registry possono essere eseguiti sullo stesso host.
 */

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: MainClass <porta>");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 1024) throw new NumberFormatException();
                Thread S = new Thread(new ServerCongresso(port));
                S.setName("Server");
                S.start();

                Thread C = new Thread(new ClientCongresso(port));
                C.setName("Client");
                C.start();
            } catch (NumberFormatException ex) { System.err.println("Il parametro inserito non è una porta valida"); }
        }
    }
}
