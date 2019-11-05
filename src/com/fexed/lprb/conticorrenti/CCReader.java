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

public class CCReader implements Runnable {
    @Override
    public void run() {
        String inputFileName = "records.json";
        Path path = Paths.get(inputFileName);
        String str;

        Gson gson = new Gson();
        try {
            FileChannel fileChnl = FileChannel.open(path, StandardOpenOption.READ);
            ByteBuffer bBuffer = ByteBuffer.allocate(1024);
            StringBuilder sBuffer = new StringBuilder();
            while (fileChnl.read(bBuffer) != -1) {
                bBuffer.flip();
                while (bBuffer.hasRemaining()) sBuffer.append(StandardCharsets.UTF_8.decode(bBuffer).toString());
                bBuffer.clear();
            }

            CCPerson[] persons = gson.fromJson(sBuffer.toString(), (Type) CCPerson[].class);
            for (CCPerson person : persons) {
                MainClass.poolExecutor.submit(new CCAnalyzer(person));
            }
            MainClass.poolExecutor.shutdown();
        } catch (IOException ignored) { }
    }
}
