package com.fexed.lprb.conticorrenti;

/**
 * @author Federico Matteoni
 */
public class CCStats {
    private static int nBonifico = 0;
    private static int nAccredito = 0;
    private static int nBollettino = 0;
    private static int nF24 = 0;
    private static int nPagoBancomat = 0;

    public static synchronized void incnBonifico() {CCStats.nBonifico++;}
    public static synchronized void incnAccredito() {CCStats.nAccredito++;}
    public static synchronized void incnBollettino() {CCStats.nBollettino++;}
    public static synchronized void incnF24() {CCStats.nF24++;}
    public static synchronized void incnPagoBancomat() {CCStats.nPagoBancomat++;}
    public static int getnBonifico() {return CCStats.nBonifico;}
    public static int getnAccredito() {return CCStats.nAccredito;}
    public static int getnBollettino() {return CCStats.nBollettino;}
    public static int getnF24() {return CCStats.nF24;}
    public static int getnPagoBancomat() {return CCStats.nPagoBancomat;}
}
