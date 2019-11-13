package com.fexed.lprb.httpfiletransfer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Date;

public class Handler implements Runnable {
    private Socket skt;

    public Handler(Socket skt) {
        this.skt = skt;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "\tNew connection from " + skt.getInetAddress().getHostAddress());
            InputStream inStream = skt.getInputStream();        //Ottenimento dell'HTTP request header
            String HTTPRequest = new String(inStream.readNBytes(100));
            int indexOfFirstNewLine = HTTPRequest.indexOf("\n");//Ottenimento del path dal request header
            String pathRequested = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[1].substring(1);
            System.out.println(Thread.currentThread().getName() + "\tRequested: " + pathRequested);

            File file = new File(pathRequested);
            byte[] fileContents = null;
            OutputStream outStream = skt.getOutputStream();
            try {
                fileContents = Files.readAllBytes(Paths.get(pathRequested));
                outStream.write(("HTTP/1.1 200 OK\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Server: Fexed's HTTPFileTransferServer v0.1\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Date: " + new Date().toString() + "\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Content-Type: text/plain\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Content-Length: " + file.length() + "\n").getBytes(Charset.defaultCharset()));
                outStream.write(("\n").getBytes(Charset.defaultCharset()));
                outStream.write(fileContents);
                outStream.flush();                              //Header e file in caso esista
            } catch (NoSuchFileException ex) {
                outStream.write(("HTTP/1.1 500 No Such File Exception\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Server: Fexed's HTTPFileTransferServer v0.1\n").getBytes(Charset.defaultCharset()));
                outStream.write(("Date: " + new Date().toString() + "\n").getBytes(Charset.defaultCharset()));
                outStream.flush();                              //Header d'errore in caso di file non esistente
            }

            System.out.println(Thread.currentThread().getName() + "\tClosing connection");
            skt.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
