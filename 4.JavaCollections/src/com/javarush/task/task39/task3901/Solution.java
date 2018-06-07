package com.javarush.task.task39.task3901;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/* 
Уникальные подстроки
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please input your string: ");
        String s = bufferedReader.readLine();

        System.out.println("The longest unique substring length is: \n" + lengthOfLongestUniqueSubstring(s));
    }

    public static int lengthOfLongestUniqueSubstring(String s) {
        if (s == null || s.length() == 0)
            return 0;

        Set<Character> chars = new HashSet<>();

        int result = 0;
        for (Character c : s.toCharArray()) {
            if (chars.contains(c)) {
                if (result < chars.size()) {
                    result = chars.size();
                    chars.clear();
                }
            }
            chars.add(c);
        }
        return result < chars.size() ? chars.size() : result;
    }
}
