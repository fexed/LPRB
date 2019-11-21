package com.fexed.lprb.conticorrenti;

/**
 * @author Federico Matteoni
 */
public class CCAnalyzer implements Runnable {
    public CCPerson p;

    public CCAnalyzer(CCPerson p){
        this.p = p;
    }

    @Override
    public void run() {
        //System.out.println(Thread.currentThread().getName() + ": analizzo i " + this.p.movimenti.size() + " movimenti di " + this.p.nome + " " + this.p.cognome);
        for (CCMovement movement : this.p.movimenti) {
            switch (movement.causale) {
                case "Bonifico":
                    CCStats.incnBonifico();
                    break;
                case "Accredito":
                    CCStats.incnAccredito();
                    break;
                case "Bollettino":
                    CCStats.incnBollettino();
                    break;
                case "F24":
                    CCStats.incnF24();
                    break;
                case "PagoBancomat":
                    CCStats.incnPagoBancomat();
                    break;
            }
        }
        //System.out.println("\t\t\t\t" + Thread.currentThread().getName() + ": concluso per " + this.p.nome + " " + this.p.cognome);
    }
}
