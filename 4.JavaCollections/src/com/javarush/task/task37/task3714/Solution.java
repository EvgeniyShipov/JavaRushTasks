package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String romanString) {
        int result = 0;

        while (romanString.length() > 0) {
            for (Roman roman : Roman.values()) {
                if (romanString.startsWith(roman.getRomanString())) {
                    result += roman.getArabInt();
                    romanString = romanString.replaceFirst(roman.getRomanString(), "");
                    break;
                }
            }
        }
        return result;
    }

    private enum Roman {
        M("M", 1000),
        CM("CM", 900),
        D("D", 500),
        CD("CD", 400),
        C("C", 100),
        XC("XC", 90),
        L("L", 50),
        XL("XL", 40),
        X("X", 10),
        IX("IX", 9),
        V("V", 5),
        IV("IV", 4),
        I("I", 1);

        private final String romanString;
        private final int arabInt;

        Roman(String romanString, int arabInt) {
            this.romanString = romanString;
            this.arabInt = arabInt;
        }

        public String getRomanString() {
            return romanString;
        }

        public int getArabInt() {
            return arabInt;
        }
    }
}
