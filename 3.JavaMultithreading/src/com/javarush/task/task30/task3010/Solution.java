package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/

import java.math.BigDecimal;
import java.math.BigInteger;

public class Solution {
    public static void main(String[] args) {
        for (int i = 2; i <= 36; i++) {
            try {
                BigDecimal bi = new BigDecimal(new BigInteger(args[0], i));
                System.out.println(i);
                break;
            } catch (Exception e) {
                if (i == 36) {
                    System.out.println("incorrect");
                }
            }
        }
    }
}