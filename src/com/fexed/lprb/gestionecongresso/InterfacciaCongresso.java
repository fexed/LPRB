package com.fexed.lprb.gestionecongresso;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia di accesso al congresso
 * @author Federico Matteoni
 */
public interface InterfacciaCongresso extends Remote {
    /**
     * Ritorna l'insieme delle giornate del congresso
     * @return L'insieme delle giornate del congresso
     * @throws RemoteException
     */
    GiornataCongresso[] getGiornate() throws RemoteException;

    SessioneCongresso[] getSessioni(int giorno) throws RemoteException;

    InterventoCongresso[] getInterventi(int giorno, int sessione) throws RemoteException;

    boolean newIntervento(int giorno, int sessione, int intervento, String speaker) throws RemoteException;
}
