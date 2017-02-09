package com.javarush.task.task07.task0712;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
Самые-самые
*/

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(reader.readLine());
        }
        reader.close();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (String x : list) {
            if (min > x.length()) {
                min = x.length();
            }
            if (max < x.length()) {
                max = x.length();
            }
        }

        for (int j = 0; j < 10; j++) {
            if (min == list.get(j).length() || max == list.get(j).length()) {
                System.out.println(list.get(j));
                return;
            }
        }
    }
}
