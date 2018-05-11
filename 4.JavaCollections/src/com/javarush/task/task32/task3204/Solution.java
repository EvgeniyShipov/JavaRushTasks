package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(8);
        int[] randomLetter = new int[3];
        while (!baos.toString().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$")) {
            baos.reset();
            for (int i = 0; i < 8; i++) {
                randomLetter[0] = (byte) (Math.random() * 9) + 48;
                randomLetter[1] = (byte) (Math.random() * 26) + 97;
                randomLetter[2] = (byte) (Math.random() * 26) + 65;
                baos.write(randomLetter[(int) (Math.random() * 3)]);
            }
        }
        return baos;
    }
}