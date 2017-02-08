package com.javarush.task.task01.task0132;

/* 
Сумма цифр трехзначного числа
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(sumDigitsInNumber(546));
    }

    public static int sumDigitsInNumber(int number) {
        int x1 = (number % 100) / 10;
        int x2 = number % 10;
        int x3 = number / 100;

        return x1 + x2 + x3;
        //напишите тут ваш код
    }
}