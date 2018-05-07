package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) {
        String resultFileName = args[0];
        String[] fileNameParts = new String[args.length - 1];
        System.arraycopy(args, 1, fileNameParts, 0, args.length - 1);
        Arrays.sort(fileNameParts);

        List<FileInputStream> streams = new ArrayList<>();
        Stream.of(fileNameParts).forEach(s -> {
            try {
                streams.add(new FileInputStream(s));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        try {
            ZipInputStream zipInput = new ZipInputStream(new SequenceInputStream(Collections.enumeration(streams)));
            FileOutputStream fos = new FileOutputStream(new File(resultFileName));
            byte[] buff = new byte[1024 * 1024];
            while (zipInput.getNextEntry() != null) {
                int x;
                while ((x = zipInput.read(buff)) != -1) {
                    fos.write(buff, 0, x);
                    fos.flush();
                }
            }
            zipInput.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
