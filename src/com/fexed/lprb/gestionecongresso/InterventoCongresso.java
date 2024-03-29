package com.fexed.lprb.gestionecongresso;

import java.io.Serializable;

/**
 * Singolo intervento in una sessione di una giornata di congresso
 * @author Federico Matteoni
 */
public class InterventoCongresso implements Serializable {
    /**
     * Il nome dello speaker che tiene questo intervento
     */
    public String nomeSpeaker;

    /**
     * Costruttore della classe, istanzia l'attributo {@code nomeSpeaker}
     * @param nome Il nome dello speaker che tiene questo intervento
     */
    public InterventoCongresso(String nome) {
        this.nomeSpeaker = nome;
    }
}
