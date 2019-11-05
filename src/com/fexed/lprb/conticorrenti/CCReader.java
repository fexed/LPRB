package com.fexed.lprb.conticorrenti;

import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;

public class CCReader implements Runnable {
    @Override
    public void run() {
        String inputFileName = "records.json";
        File inputFile = new File(inputFileName);
        String str;

        Gson gson = new Gson();
        try {
            FileInputStream inStream = new FileInputStream(inputFile);
            BufferedReader reader  = new BufferedReader(new InputStreamReader(inStream));
            StringBuilder sBuffer = new StringBuilder();
            while ((str = reader.readLine()) != null) {
                sBuffer.append(str);
            }

            CCPerson[] persons = gson.fromJson(sBuffer.toString(), (Type) CCPerson[].class);
            //System.out.println(Thread.currentThread().getName() + ": l'ultimo CC è di " + persons[persons.length - 1].nome + " " + persons[persons.length - 1].cognome);

            for (CCPerson person : persons) {
                MainClass.poolExecutor.submit(new CCAnalyzer(person));
            }
            MainClass.poolExecutor.shutdown();
        } catch (IOException ignored) { }
    }
}
