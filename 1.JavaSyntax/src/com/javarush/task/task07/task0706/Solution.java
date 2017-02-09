package com.javarush.task.task07.task0706;

import java.io.BufferedReader;
import java.io.InputStreamReader;/*
Улицы и дома
*/

public class Solution {
    public static void main(String[] args) throws Exception {
        int[] a = new int[15];
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int i = 0;
        while (i != 15) {
            a[i] = Integer.parseInt(reader.readLine());
            i++;
        }

        reader.close();

        int chet = a[0];
        int nechet = 0;

        for (int j = 1; j < a.length; j++) {
            if (j % 2 == 0) {
                chet += a[j];
            } else {
                nechet += a[j];
            }
        }

        if (chet > nechet) {
            System.out.println("В домах с четными номерами проживает больше жителей.");
        } else {
            System.out.println("В домах с нечетными номерами проживает больше жителей.");
        }
    }
}
