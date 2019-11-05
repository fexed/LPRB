package com.fexed.lprb.conticorrenti;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CCGenerator {
    public static void main(String[] args) {
        String[] type = {"Bonifico", "Accredito", "Bollettino", "F24", "PagoBancomat"};
        Random rnd = new Random(System.currentTimeMillis());
        Faker faker = new Faker();
        int n = rnd.nextInt(20) + 90;
        int k;

        List<CCPerson> ccs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            CCPerson p = new CCPerson(faker.name().firstName(), faker.name().lastName());
            k = rnd.nextInt(20) + 1;
            List<CCMovement> m = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                Date d = faker.date().birthday(2, 2);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
                m.add(new CCMovement(dateFormat.format(d), type[rnd.nextInt(type.length)]));
            }
            p.movimenti = m;                                                        //Generazione dei conti correnti
            ccs.add(p);                                                             //e dei loro movimenti
            //System.out.println(i + ": " + p.nome + " " + p.cognome + ", " + p.movimenti.size() + " movimenti");
        }

        Gson gson = new Gson();
        String inputFileName = "records.json";
        Path path = Paths.get(inputFileName);                                       //Preparazione del file da scrivere
        try {
            FileChannel fileChnl = FileChannel.open(path, StandardOpenOption.WRITE);
            ByteBuffer bBuffer;
            String str = gson.toJson(ccs);
            bBuffer = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));        //Preparazione del buffer

            while(bBuffer.hasRemaining()) {
                fileChnl.write(bBuffer);
            }
            bBuffer.clear();
            fileChnl.force(true);
            fileChnl.close();
        } catch (IOException ignored) {}
    }
}
