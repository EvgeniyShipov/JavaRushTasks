package com.javarush.task.task38.task3812;

/* 
Обработка аннотаций
*/

import java.util.Arrays;

public class Solution {
    public static void main(String[] args) {
        printFullyQualifiedNames(Solution.class);
        printFullyQualifiedNames(SomeTest.class);

        printValues(Solution.class);
        printValues(SomeTest.class);
    }

    public static boolean printFullyQualifiedNames(Class c) {
        String[] fullyQualifiedNames = Arrays.stream(c.getAnnotations())
                .filter(annotation -> annotation instanceof PrepareMyTest)
                .map(annotation -> (PrepareMyTest) annotation)
                .map(PrepareMyTest::fullyQualifiedNames)
                .findFirst().orElse(new String[]{});
        if (fullyQualifiedNames.length > 0) {
            Arrays.stream(fullyQualifiedNames).forEach(System.out::println);
            return true;
        }
        return false;
    }

    public static boolean printValues(Class c) {
        Class<?>[] value = Arrays.stream(c.getAnnotations())
                .filter(annotation -> annotation instanceof PrepareMyTest)
                .map(annotation -> (PrepareMyTest) annotation)
                .map(PrepareMyTest::value)
                .findFirst().orElse(new Class[]{});
        if (value.length > 0) {
            Arrays.stream(value).forEach(System.out::println);
            return true;
        }
        return false;
    }
}
