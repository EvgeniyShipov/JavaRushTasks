package com.javarush.task.task40.task4009;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.format.TextStyle.FULL;
import static java.time.temporal.ChronoField.YEAR;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(weekDayOfBirthday("30.05.1984", "2015"));
        System.out.println(weekDayOfBirthday("1.12.2015", "2016"));
    }

    public static String weekDayOfBirthday(String birthday, String year) {
        LocalDate birthdayDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("d.M.yyyy"));
        Year ye = Year.parse(year);
        DayOfWeek dayOfWeek = birthdayDate.with(YEAR, ye.getValue()).getDayOfWeek();

        return dayOfWeek.getDisplayName(FULL , Locale.ITALIAN);
    }
}
