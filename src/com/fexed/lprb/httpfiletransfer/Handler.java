package com.fexed.lprb.httpfiletransfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ThreadPoolExecutor;

public class Handler implements Runnable {
    private Socket skt;

    public Handler(Socket skt) {
        this.skt = skt;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "\tNew connection from " + skt.getInetAddress().getHostAddress());
            InputStream inStream = skt.getInputStream();        //Read the HTTP request
            String HTTPRequest = new String(inStream.readNBytes(100));
            int indexOfFirstNewLine = HTTPRequest.indexOf("\n");//A bit of string manipulation to get the path
            String HTTPVersion = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[2];
            String pathRequested = HTTPRequest.substring(0, indexOfFirstNewLine).split(" ")[1].substring(1);
            System.out.println(Thread.currentThread().getName() + "\tRequested: " + pathRequested + "\t" + HTTPVersion);

            Path path = Paths.get(pathRequested);               //Apro il file richiesto
            FileChannel fileChnl = FileChannel.open(path, StandardOpenOption.READ);
            ByteBuffer bBuffer = ByteBuffer.allocate(1024);     //Allocazione del buffer
            while (fileChnl.read(bBuffer) != -1) {
            }

            OutputStream outStream = skt.getOutputStream();
            outStream.write((HTTPVersion + " 200 OK\nContent-Type: text/html\n\n").getBytes(Charset.defaultCharset()));
            outStream.write(bBuffer.array());

            System.out.println(Thread.currentThread().getName() + "\tClosing connection");
            skt.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
