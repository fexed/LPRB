package com.fexed.lprb.httpfiletransfer;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
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

            File file = new File(pathRequested);
            BufferedReader buffReader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = buffReader.readLine()) != null) sb.append(str);

            OutputStream outStream = skt.getOutputStream();
            outStream.write((HTTPVersion + " 200 OK\n").getBytes(Charset.defaultCharset()));
            outStream.write(("Server: Fexed's HTTPFileTransferServer v0.1\n").getBytes(Charset.defaultCharset()));
            outStream.write(("Date: " + new Date().toString() + "\n").getBytes(Charset.defaultCharset()));
            outStream.write(("Content-Type: text/plain\n").getBytes(Charset.defaultCharset()));
            outStream.write(("Content-Length: " + sb.length() + "\n").getBytes(Charset.defaultCharset()));
            outStream.write(("\n").getBytes(Charset.defaultCharset()));
            outStream.write(sb.toString().getBytes(Charset.defaultCharset()));
            outStream.flush();

            System.out.println(Thread.currentThread().getName() + "\tClosing connection");
            skt.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
