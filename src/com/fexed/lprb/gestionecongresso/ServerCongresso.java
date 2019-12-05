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
        for (int i = 0; i < 3; i++) this.giornate[i] = new GiornataCongresso(i);
        this.porta = porta;
    }

    @Override
    public void run() {
        try {
            //Esportazione dell'oggetto
            InterfacciaCongresso stub = (InterfacciaCongresso) UnicastRemoteObject.exportObject(this, 0);

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
    public GiornataCongresso getGiornata(int giorno) throws RemoteException {
        if (giorno >= 0 && giorno <= 3) return this.giornate[giorno];
        else return null;
    }
}
