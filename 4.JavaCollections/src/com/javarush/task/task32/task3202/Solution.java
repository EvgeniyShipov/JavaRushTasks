package com.javarush.task.task32.task3202;

import java.io.*;

/* 
Читаем из потока
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("G:/Hello.txt"));
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) {
        StringWriter writer = new StringWriter();
        try (BufferedInputStream bis = new BufferedInputStream(is)) {
            while (bis.available() > 0)
                writer.write(bis.read());
        } catch (IOException e) {
        }
        return writer;
    }
}