package com.javarush.task.task39.task3909;

/*
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {

    }

    public static boolean isOneEditAway(String first, String second) {
        if (first == null || second == null)
            return false;

        long firstLength = first.length();
        long secondLength = second.length();
        long delta = Math.abs(firstLength - secondLength);

        if (delta > 1)
            return false;

        char[] charsFirst = first.toCharArray();
        char[] charsSecond = second.toCharArray();

        if (firstLength == secondLength) {
            int count = 0;
            for (int i = 0; i < firstLength; i++) {
                if (charsFirst[i] != charsSecond[i])
                    count++;
            }
            return count < 2;
        }

        int offset = 0;

        if (firstLength > secondLength) {
            for (int i = 0; i < secondLength; i++) {
                if (charsFirst[i + offset] != charsSecond[i]) {
                    offset++;
                    i--;
                    if (offset == 2)
                        return false;
                }
            }
            return true;
        }

        if (secondLength > firstLength) {
            for (int i = 0; i < firstLength; i++) {
                if (charsFirst[i] != charsSecond[i + offset]) {
                    offset++;
                    i--;
                    if (offset == 2)
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}