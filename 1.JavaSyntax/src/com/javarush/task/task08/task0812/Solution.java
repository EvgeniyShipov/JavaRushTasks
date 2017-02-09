package com.javarush.task.task08.task0812;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* 
Cамая длинная последовательность
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            list.add(Integer.parseInt(reader.readLine()));
        }
        reader.close();

        int max = Integer.MIN_VALUE;
        int x = 1;

        for (int j = 0; j < list.size() - 1; j++) {
            if (list.get(j).equals(list.get(j + 1))) {
                x++;
            } else {
                x = 1;
            }
            if (max < x) {
                max = x;
            }
        }

        System.out.println(max);
    }
}