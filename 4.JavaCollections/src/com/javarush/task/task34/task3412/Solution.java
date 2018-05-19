package com.javarush.task.task34.task3412;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/* 
Добавление логирования в класс
*/

public class Solution {
    private static final Logger logger = LoggerFactory.getLogger(Solution.class);

    private int value1;
    private String value2;
    private Date value3;

    public Solution(int value1, String value2, Date value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        logger.debug("создан новый объект Solution");
    }

    public static void main(String[] args) {

    }

    public void calculateAndSetValue3(long value) {
        logger.trace("Вызван метод calculateAndSetValue3");
        value -= 133;
        if (value > Integer.MAX_VALUE) {
            value1 = (int) (value / Integer.MAX_VALUE);
            logger.debug("Установлено новое значение value1 " + value1);
        } else {
            value1 = (int) value;
            logger.debug("Установлено новое значение value1 " + value1);
        }
    }

    public void printString() {
        logger.trace("Вызван метод printString");
        if (value2 != null) {
            System.out.println(value2.length());
        }
    }

    public void printDateAsLong() {
        logger.trace("Вызван метод printDateAsLong");
        if (value3 != null) {
            System.out.println(value3.getTime());
        }
    }

    public void divide(int number1, int number2) {
        logger.trace("Вызван метод divide");
        try {
            System.out.println(number1 / number2);
        } catch (ArithmeticException e) {
            logger.error("Произошла ошибка при делении чисел в методе - divide " + e.getMessage());
        }
    }

    public void setValue1(int value1) {
        this.value1 = value1;
        logger.debug("Значение поля value1 изменено на " + value1);
    }

    public void setValue2(String value2) {
        this.value2 = value2;
        logger.debug("Значение поля value2 изменено на " + value2);
    }

    public void setValue3(Date value3) {
        this.value3 = value3;
        logger.debug("Значение поля value3 изменено на " + value3);
    }
}
