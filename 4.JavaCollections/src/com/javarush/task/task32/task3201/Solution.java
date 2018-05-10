package com.javarush.task.task32.task3201;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        Integer number = Integer.valueOf(args[1]);
        String text = args[2];

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.seek(number);
            if (raf.length() < number)
                raf.seek(raf.length());
            raf.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
