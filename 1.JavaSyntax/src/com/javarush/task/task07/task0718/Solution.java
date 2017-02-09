package com.javarush.task.task07.task0718;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Проверка на упорядоченность
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i ++) {
            list.add(reader.readLine());
        }
        reader.close();

        int x = list.get(0).length();
        for (int j = 1; j < 10; j ++) {
            if (list.get(j).length() == x + 1) {
                x = list.get(j).length();
            } else {
                System.out.println(j + 1);
                return;
            }
        }
    }
}

