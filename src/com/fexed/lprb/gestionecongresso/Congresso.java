package com.fexed.lprb.gestionecongresso;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia di accesso al congresso
 * @author Federico Matteoni
 */
public interface Congresso extends Remote {
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

    /**
     * Ritorna le sessioni all'interno di una giornata di congresso
     * @param giornata la gionrata di congresso interessata
     * @return le 12 sessioni della giornata
     * @throws RemoteException
     */
    SessioneCongresso getSessioni(GiornataCongresso giornata) throws RemoteException;

    /**
     * Ritorna una sessione di una giornata di congresso, specificata da sessione
     * @param giornata la giornata di congresso interessata
     * @param sessione la sessione da ritornare
     * @return la sessione richiesta
     * @throws RemoteException
     */
    SessioneCongresso getSessione(GiornataCongresso giornata, int sessione) throws RemoteException;

    /**
     * Ritorna gli interventi della singola sessione
     * @param sessione la sessione interessata
     * @return i 5 interventi della sessione
     * @throws RemoteException
     */
    InterventoCongresso getInterventi(SessioneCongresso sessione) throws RemoteException;

    /**
     * Ritorna un intervento della sessione, indicato da intervento
     * @param sessione la sessione interessata
     * @param intervento il numero di intervento interessato
     * @return l'intervento richiesto
     * @throws RemoteException
     */
    InterventoCongresso getIntervento(SessioneCongresso sessione, int intervento) throws RemoteException;
}
