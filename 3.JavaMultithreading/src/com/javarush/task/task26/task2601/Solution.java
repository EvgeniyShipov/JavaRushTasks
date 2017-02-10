package com.javarush.task.task26.task2601;

import java.util.Arrays;
import java.util.Comparator;

/*
Почитать в инете про медиану выборки
*/
public class Solution {

    public static void main(String[] args) {

    }

    public static Integer[] sort(Integer[] array) {
        Arrays.sort(array);
        int mediana = array.length % 2 == 0? (array[Math.round(array.length / 2) - 1] + array[Math.round(array.length / 2)])/2 : array[Math.round(array.length / 2)];
        Arrays.sort(array, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return -((mediana - o1) + (mediana - o2));
            }
        });

        return array;
    }
}
