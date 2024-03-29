package com.fexed.lprb.postoffice;

import java.sql.Time;
import java.time.Instant;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
Simulare il flusso di clienti in un ufficio postale che ha 4 sportelli. Nell'ufficio esiste:
-   un'ampia sala d'attesa in cui ogni persona può entrare liberamente. Quando entra, ogni persona prende il numero dalla
    numeratrice e aspetta il proprio turno in questa sala.
-   una seconda sala, meno ampia, posta davanti agli sportelli, in cui si può entrare solo  a gruppi di k persone (nella
    seconda sala possono stare in attesa massimo k persone)
-   Una persona si mette quindi prima in coda nella prima sala, poi passa nella seconda sala.
-   Ogni persona impiega un tempo differente per la propria operazione allo sportello. Una volta terminata l'operazione,
    la persona esce dall'ufficio

Scrivere un programma in cui:
-   l'ufficio viene modellato come una classe JAVA, in cui viene attivato un ThreadPool di dimensione uguale al numero
    degli sportelli
-   la coda delle persone presenti nella sala d'attesa è gestita esplicitamente dal programma
-   la seconda coda (davanti agli sportelli) è quella gestita implicitamente dal ThreadPool
-   ogni persona viene modellata come un task, un task che deve essere assegnato ad uno dei thread associati agli
    sportelli
-   si preveda di far entrare tutte le persone nell'ufficio postale, all'inizio del programma


Facoltativo: prevedere il caso di un flusso continuo di clienti e la possibilità che l'operatore chiuda lo sportello
stesso dopo che in un certo intervallo di tempo non si presentano clienti al suo sportello.
 */

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static void main(String[] args) {
        PostOffice po = new PostOffice(10);
        Random rnd = new Random(Calendar.getInstance().getTimeInMillis());
        int max = rnd.nextInt(100);
        for(int i = 0; i < max; i++) po.addNewPerson(new Person(i, rnd.nextInt(10)+2));

        while (po.hasPeople()) {
            po.assignPerson(po.waitForDoor(), po.popPerson());
        }
    }
}
