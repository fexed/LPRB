package com.fexed.lprb.gestionecongresso;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;

/**
 * @author Federico Matteoni
 */
public class ServerCongresso extends RemoteServer implements Runnable, InterfacciaCongresso {
    private GiornataCongresso[] giornate;
    private int porta;

    ServerCongresso(int porta) {
        super();
        this.giornate = new GiornataCongresso[3];
        for (int i = 0; i < 3; i++) this.giornate[i] = new GiornataCongresso(i+1);
        this.porta = porta;
    }

    @Override
    public void run() {
        try {
            //Esportazione dell'oggetto
            InterfacciaCongresso stub = (InterfacciaCongresso) UnicastRemoteObject.exportObject(this, this.porta);

            //Crezione del registro
            LocateRegistry.createRegistry(this.porta);
            Registry r = LocateRegistry.getRegistry(this.porta);

            //Pubblicazione dello stub nel registro
            r.rebind("CONGRESSO-SERVER", stub);

            //Server pronto
            System.out.println("Server pronto su porta " + this.porta);
        } catch (RemoteException ex) { System.err.println("Errore di comunicazione: " + ex.getMessage()); }

    }

    @Override
    public GiornataCongresso[] getGiornate() {
        return this.giornate;
    }

    @Override
    public SessioneCongresso[] getSessioni(int giorno) {
        SessioneCongresso[] sessioni = this.giornate[giorno].sessioni;
        if (sessioni == null) {
            sessioni = new SessioneCongresso[12];
            for (int i = 0; i < 12; i++) sessioni[i] = new SessioneCongresso(i);
        }
        return sessioni;
    }

    @Override
    public InterventoCongresso[] getInterventi(int giorno, int sessione) {
        if (getSessioni(giorno)[sessione] == null) getSessioni(giorno)[sessione] = new SessioneCongresso(sessione);
        InterventoCongresso[] interventi = this.giornate[giorno].sessioni[sessione].interventi;
        if (interventi == null) interventi = new InterventoCongresso[5];
        return interventi;
    }

    @Override
    public boolean newIntervento(int giorno, int sessione, int intervento, String speaker) {
        if (getInterventi(giorno, sessione)[intervento] != null) return false;
        else {
            getInterventi(giorno, sessione)[intervento] = new InterventoCongresso(speaker);
            return true;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) System.err.println("Usage: server <porta>");
        else {
            try {
                int port = Integer.parseInt(args[0]);
                if (port < 1024) throw new NumberFormatException();
                Thread T = new Thread(new ServerCongresso(port));
                T.setName("Server");
                T.start();
            } catch (NumberFormatException ex) { System.err.println("Il parametro inserito non è una porta valida"); }
        }
    }
}
