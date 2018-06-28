package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private static final String BEFORE = "before";
    private static final String SUCCESS = "success.format";
    private static final String INVALID = "invalid.data";

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString(BEFORE));
        String currencyCode = "";
        int denomination = 0;
        int count = 0;

        try {
            currencyCode = ConsoleHelper.askCurrencyCode();
            String[] validTwoDigits = ConsoleHelper.getValidTwoDigits(currencyCode);
            denomination = Integer.parseInt(validTwoDigits[0]);
            count = Integer.parseInt(validTwoDigits[1]);
        } catch (Exception e) {
            ConsoleHelper.writeMessage(res.getString(INVALID));
            this.execute();
        }

        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        manipulator.addAmount(denomination, count);
        ConsoleHelper.writeMessage(String.format(res.getString(SUCCESS), denomination, count));
    }
}
