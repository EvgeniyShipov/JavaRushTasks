package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static final String END = "the.end";
    private static final String CHOOSE_OPERATION = "choose.operation";
    private static final String INFO = "operation.INFO";
    private static final String DEPOSIT = "operation.DEPOSIT";
    private static final String WITHDRAW = "operation.WITHDRAW";
    private static final String EXIT = "operation.EXIT";
    private static final String INVALID_DATA = "invalid.data";
    private static final String CHOOSE_CURRENCY_CODE = "choose.currency.code";
    private static final String CHOOSE_DENOMINATION_AND_COUNT = "choose.denomination.and.count.format";


    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.class.getPackage().getName() + "/resources/common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String string = bis.readLine();
            if (res.getString(EXIT).equalsIgnoreCase(string)) {
                printExitMessage();
                throw new InterruptOperationException();
            }
            return string;
        } catch (IOException e) {
        }
        return null;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString(CHOOSE_CURRENCY_CODE));
        String string = readString();
        if (string == null || string.length() != 3) {
            writeMessage(res.getString(INVALID_DATA));
            string = askCurrencyCode();
        }

        return string.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        String[] result = new String[2];
        try {
            writeMessage(res.getString(CHOOSE_DENOMINATION_AND_COUNT));
            result = readString().split(" ");
            if (Integer.parseInt(result[0]) < 0)
                throw new Exception();
            if (Integer.parseInt(result[1]) < 0)
                throw new Exception();
        } catch (Exception e) {
            writeMessage(res.getString(INVALID_DATA));
            result = getValidTwoDigits(currencyCode);
        }
        return result;
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString(CHOOSE_OPERATION));
        int i = Integer.parseInt(readString());
        if (i < 1 || i > 4) {
            writeMessage(res.getString(INVALID_DATA));
            return askOperation();
        }
        Operation operation = Operation.getAllowableOperationByOrdinal(i);
        switch (operation) {
            case INFO:
                writeMessage(res.getString(INFO));
                break;
            case DEPOSIT:
                writeMessage(res.getString(DEPOSIT));
                break;
            case WITHDRAW:
                writeMessage(res.getString(WITHDRAW));
                break;
            case EXIT:
                writeMessage(res.getString(EXIT));
                break;
        }
        return operation;
    }

    public static void printExitMessage() {
        writeMessage(res.getString(END));
    }
}
