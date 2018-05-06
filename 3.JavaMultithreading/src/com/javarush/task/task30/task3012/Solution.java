package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(74);
    }

    public void createExpression(int number) {
        StringBuilder result = new StringBuilder()
                .append(number)
                .append(" =");
        String[] formats = {"", " + %d", " - %d"};
        int[] numbers = {1, 3, 9, 27, 81, 243, 729, 2187};
        int i = 0;

        while (number > 0) {
            result.append(String.format(formats[number % 3], numbers[i++]));
            number = ++number / 3;
        }

        System.out.println(result);
    }
}