package com.javarush.task.task05.task0507;

/* 
Среднее арифметическое
*/

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int sum = 0;
        int i = 0;
        while ((x = scanner.nextInt()) != -1) {
            i++;
            sum += x;
        }
        System.out.println((double) sum / i);
    }
}


