package com.javarush.task.task26.task2613;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return bis.readLine();
        } catch (IOException e) {
        }
        return null;
    }

    public static String askCurrencyCode() {
        writeMessage("Введите код валюты");
        String string = readString();
        if (string == null || string.length() != 3) {
            writeMessage("Неверный код");
            string = askCurrencyCode();
        }

        return string.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        String[] result = new String[2];
        try {
            writeMessage("Введите номинал и количество банкнот");
            result = readString().split(" ");
            if (Integer.parseInt(result[0]) < 0)
                throw new Exception();
            if (Integer.parseInt(result[1]) < 0)
                throw new Exception();
        } catch (Exception e) {
            writeMessage("Вы ввели некорректное значение");
            result = getValidTwoDigits(currencyCode);
        }
        return result;
    }

    public static Operation askOperation() {
        writeMessage("Введите операцию");
        int i = Integer.parseInt(readString());
        if (i < 1 || i > 4) {
            writeMessage("Некорректное значение");
            return askOperation();
        }
        return Operation.getAllowableOperationByOrdinal(i);
    }
}
