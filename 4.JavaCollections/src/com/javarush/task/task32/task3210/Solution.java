package com.javarush.task.task32.task3210;

import java.io.IOException;
import java.io.RandomAccessFile;/*
Используем RandomAccessFile
*/

public class Solution {
    public static void main(String... args) {
        String fileName = args[0];
        Integer number = Integer.valueOf(args[1]);
        String text = args[2];

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.seek(number);
            byte[] buffer = new byte[text.length()];
            raf.read(buffer, 0, buffer.length);
            String string = new String(buffer);
            raf.seek(raf.length());
            raf.write(string.equals(text) ? "true".getBytes() : "false".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
