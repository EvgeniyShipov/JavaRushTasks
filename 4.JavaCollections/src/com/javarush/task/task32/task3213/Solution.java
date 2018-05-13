package com.javarush.task.task32.task3213;

import java.io.BufferedReader;
import java.io.StringReader;

/* 
Шифр Цезаря
*/

public class Solution {
    public static void main(String[] args) {
        StringReader reader = new StringReader("Khoor#Dpljr#&C,₷B'3");
        System.out.println(decode(reader, -3));  //Hello Amigo #@)₴?$0
    }

    public static String decode(StringReader reader, int key) {
        try {
            BufferedReader br = new BufferedReader(reader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            char[] chars = result.toString().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] += key;
            }
            return new String(chars);
        } catch (Exception e) {
            return "";
        }
    }
}
