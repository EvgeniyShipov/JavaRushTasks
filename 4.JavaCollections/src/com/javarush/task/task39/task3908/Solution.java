package com.javarush.task.task39.task3908;

import java.util.HashSet;

/*
Возможен ли палиндром?
*/
public class Solution {
    public static void main(String[] args) {
    }

    public static boolean isPalindromePermutation(String s) {
        HashSet<Character> characters = new HashSet<>();
        char[] chars = s.toLowerCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (characters.contains(chars[i]))
                characters.remove(chars[i]);
            else
                characters.add(chars[i]);
        }
        return characters.size() <= 1;
    }
}
