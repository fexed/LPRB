package com.fexed.lprb.httpfiletransfer;

/*
Scrivere un programma JAVA che implementi un server HTTP che gestisca richieste di trasferimento di file di diverso
tipo (es. immagini jpeg, gif e/o testo) provenienti da un browser web.

Il server
-   sta in ascolto su una porta nota al client (es. 6789)
-   gestisce richieste HTTP di tipo GET inviate da un browser alla URL localhost:port/filename

Le connessioni possono essere non persistenti.

Usare le classi Socket e ServerSocket per sviluppare il programma server.
Per inviare al server le richieste, utilizzare un qualsiasi browser.
 */

import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class MainClass {
    public static void main(String[] args) {
        ServerSocket srvSkt = null;
        Socket skt = null;
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        try {
            srvSkt = new ServerSocket(1337);
            skt  = srvSkt.accept();                             //Apertura e accettazione della connessione

            //Thread START
            System.out.println("New connection from " + skt.getInetAddress().getHostAddress());
            InputStream inStream = skt.getInputStream();        //Read the HTTP request
            String HTTPRequest = new String(inStream.readNBytes(100));
            int indexOfFirstNewLine = HTTPRequest.indexOf("\n");//A bit of string manipulation to get the path
            String HTTPVersion = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[2];
            String pathRequested = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[1].substring(1);
            System.out.println("Requested: " + pathRequested + "\n" + HTTPVersion);

            Path path = Paths.get(pathRequested);               //Apro il file richiesto
            FileChannel fileChnl = FileChannel.open(path, StandardOpenOption.READ);
            ByteBuffer bBuffer = ByteBuffer.allocate(1024);     //Allocazione del buffer
            while (fileChnl.read(bBuffer) != -1) { }

            OutputStream outStream = skt.getOutputStream();
            outStream.write((HTTPVersion + " 200 OK\nContent-Type: text/html\n\n").getBytes(Charset.defaultCharset()));
            outStream.write(bBuffer.array());

            skt.close();
            //Thread END

            srvSkt.close();                                     //Chiusura della connessione
        } catch (IOException e) { e.printStackTrace(); }
    }
}
