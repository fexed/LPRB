package com.fexed.lprb.conticorrenti;

import java.util.List;

/**
 * @author Federico Matteoni
 */
public class CCPerson {
    public String nome, cognome;
    public List<CCMovement> movimenti;

    public CCPerson(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }
}
