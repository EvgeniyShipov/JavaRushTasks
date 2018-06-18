package com.javarush.task.task26.task2613;

import java.util.Locale;

import static com.javarush.task.task26.task2613.CurrencyManipulatorFactory.getManipulatorByCurrencyCode;

public class CashMachine {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = getManipulatorByCurrencyCode(currencyCode);
        String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(currencyCode);
        manipulator.addAmount(Integer.parseInt(validTwoDigits[0]), Integer.parseInt(validTwoDigits[1]));
        System.out.println( manipulator.getTotalAmount());
    }
}
