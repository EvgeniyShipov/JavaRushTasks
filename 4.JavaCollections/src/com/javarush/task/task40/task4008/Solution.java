package com.javarush.task.task40.task4008;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoField.*;

/*
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("21.4.2014 17:33:40");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        DateTimeFormatter formatter;
        boolean printDate = false;
        boolean printTime = false;
        String[] dateTime = new String[2];

        if (date.contains(".")){
            printDate = true;
            dateTime[0] = date;
        }
        if (date.contains(":")){
            printTime = true;
            dateTime[1] = date;
        }
        if (date.contains(" ")){
            printDate = true;
            printTime = true;
            dateTime = date.split(" ");
        }

        if (printDate) {
            formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
            LocalDate localDate = LocalDate.parse(dateTime[0], formatter);
            System.out.println("День: " + localDate.getDayOfMonth());
            System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
            System.out.println("День месяца: " + localDate.getDayOfMonth());
            System.out.println("День года: " + localDate.getDayOfYear());
            System.out.println("Неделя месяца: " + localDate.get(ALIGNED_WEEK_OF_MONTH));
            System.out.println("Неделя года: " + localDate.get(ALIGNED_WEEK_OF_YEAR));
            System.out.println("Месяц: " + localDate.getMonth().getValue());
            System.out.println("Год: " + localDate.getYear());
        }
        if (printTime) {
            formatter = DateTimeFormatter.ofPattern("H:m:s");
            LocalTime localTime = LocalTime.parse(dateTime[1], formatter);
            System.out.println("AM или PM: " + (localTime.get(AMPM_OF_DAY) == 0 ? "AM" : "PM"));
            System.out.println("Часы: " + localTime.get(HOUR_OF_AMPM));
            System.out.println("Часы дня: " + localTime.getHour());
            System.out.println("Минуты: " + localTime.getMinute());
            System.out.println("Секунды: " + localTime.getSecond());
        }
    }
}
