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

    /**
     * Ritorna una singola giornata di congresso, specificata dal parametro giorno
     * @param giorno La giornata da restituire
     * @return La n-esima giornata di congresso
     * @throws RemoteException
     */
    GiornataCongresso getGiornata(int giorno) throws RemoteException;
}
