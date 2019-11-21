package com.fexed.lprb.conticorrenti;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Federico Matteoni
 */
public class CCReader implements Runnable {
    @Override
    public void run() {
        String inputFileName = "records.json";
        Path path = Paths.get(inputFileName);                                       //Preparazione del file da leggere

        Gson gson = new Gson();
        try {
            FileChannel fileChnl = FileChannel.open(path, StandardOpenOption.READ); //Apertura canale del file
            ByteBuffer bBuffer = ByteBuffer.allocate(1024);                         //Allocazione del buffer
            StringBuilder sBuffer = new StringBuilder();                            //Buffer di costruzione stringa JSON
            while (fileChnl.read(bBuffer) != -1) {
                bBuffer.flip();                                                     //Imposto per lettura
                while (bBuffer.hasRemaining()) sBuffer.append(StandardCharsets.UTF_8.decode(bBuffer).toString());
                bBuffer.clear();                                                    //Riparto dall'inizio e in scrittura
            }

            CCPerson[] persons = gson.fromJson(sBuffer.toString(), (Type) CCPerson[].class); //Lego i CC
            for (CCPerson person : persons) {
                MainClass.poolExecutor.submit(new CCAnalyzer(person));              //Assegno i task da eseguire
            }
            MainClass.poolExecutor.shutdown();                                      //Segnalo la fine del lavori
        } catch (IOException ignored) { }
    }
}
