package com.fexed.lprb.gestionecongresso;

/**
 * Singola giornata di congresso, contenente le 12 sessioni
 */
public class GiornataCongresso {
    /**
     * Il numero di giornata del congresso
     */
    public int nGiornata;

    /**
     * Le 12 sessioni della giornata di congresso
     */
    public SessioneCongresso[] sessioni;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n la giornata di congresso
     */
    public GiornataCongresso(int n) {
        this.nGiornata = n;
        this.sessioni = new SessioneCongresso[12];
    }
}
