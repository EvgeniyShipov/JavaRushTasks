package com.javarush.task.task38.task3802;

/* 
Проверяемые исключения (checked exception)
*/

import java.io.FileNotFoundException;
import java.io.FileReader;

public class VeryComplexClass {
    public void veryComplexMethod() throws FileNotFoundException {
        FileReader reader = new FileReader("sdfsd");
    }

    public static void main(String[] args) {

    }
}
