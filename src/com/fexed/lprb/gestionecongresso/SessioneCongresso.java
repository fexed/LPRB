package com.fexed.lprb.gestionecongresso;

import java.io.Serializable;

/**
 * Singola sessione di congresso nella giornata, contenente i 5 interventi
 * @author Federico Matteoni
 */
public class SessioneCongresso implements Serializable {
    /**
     * Il numero della sessione di congresso
     */
    public int nSessione;

    /**
     * I 5 interventi della sessione di congresso
     */
    public InterventoCongresso[] interventi;

    /**
     * Costruttore della classe che istanzia gli attributi
     * @param n il numero della sessione all'interno della giornata di congresso
     */
    public SessioneCongresso(int n) {
        this.nSessione = n;
        this.interventi = new InterventoCongresso[5];
    }
}
