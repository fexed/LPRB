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

    public ServerCongresso(int porta) throws RemoteException {
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

    public static void main(String[] args) throws RemoteException {
        //TODO porta da riga di comando
        Thread T = new Thread(new ServerCongresso(1337));
        T.setName("Server del Congresso");
        T.start();
    }

    @Override
    public GiornataCongresso[] getGiornate() throws RemoteException {
        return this.giornate;
    }

    @Override
    public SessioneCongresso[] getSessioni(int giorno) throws RemoteException {
        SessioneCongresso[] sessioni = this.giornate[giorno].sessioni;
        if (sessioni == null) {
            sessioni = new SessioneCongresso[12];
            for (int i = 0; i < 12; i++) sessioni[i] = new SessioneCongresso(i);
        }
        return sessioni;
    }

    @Override
    public InterventoCongresso[] getInterventi(int giorno, int sessione) throws RemoteException {
        if (getSessioni(giorno)[sessione] == null) getSessioni(giorno)[sessione] = new SessioneCongresso(sessione);
        InterventoCongresso[] interventi = this.giornate[giorno].sessioni[sessione].interventi;
        if (interventi == null) interventi = new InterventoCongresso[5];
        return interventi;
    }

    @Override
    public boolean newIntervento(int giorno, int sessione, int intervento, String speaker) throws RemoteException {
        if (getInterventi(giorno, sessione)[intervento] != null) return false;
        else {
            getInterventi(giorno, sessione)[intervento] = new InterventoCongresso(speaker);
            return true;
        }
    }
}
