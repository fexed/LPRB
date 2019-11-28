package com.fexed.lprb.timeserver;

/*
Definire un Server TimeServer, che
- invia su un gruppo di multicast dategroup, ad intervalli regolari, la data e l’ora.
- attende tra un invio ed il successivo un intervallo di tempo simulata mediante il metodo  sleep().

L’indirizzo IP di dategroup viene introdotto  da linea di comando.
Definire quindi un client TimeClient che si unisce a dategroup e riceve, per dieci volte consecutive, data ed ora,
le visualizza, quindi termina.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Federico Matteoni
 */
public class MainClass {
    public static void main(String[] args) {
        //IP di classe D
        //Cioè 224.0.0.0 - 239.255.255.255
        //in ambito locale, tra 239.0.0.0 e 239.255.255.255

        if (args.length != 2) {
            System.out.println("usage: TimeServer <multicast IP> <porta>");
        } else {
            try {
                InetAddress addr = InetAddress.getByName(args[0]);
                int port = Integer.parseInt(args[1]);
                if (addr.isMulticastAddress()) {
                    Thread client = new Thread(new TimeClient(addr, port));
                    client.setName("TimeClientThread");
                    client.start();

                    Thread server = new Thread(new TimeServer(addr, port));
                    server.setName("TimeServerThread");
                    server.start();
                    try {
                        client.join();
                    } catch (InterruptedException ignored) {}
                } else System.err.println("L'indirizzo fornito non è un indirizzo di multicast");
            } catch (UnknownHostException ex) { System.err.println("UnknownHostException");
            } catch (NumberFormatException ex) { System.err.println("Porta non valida"); }
        }
    }
}
