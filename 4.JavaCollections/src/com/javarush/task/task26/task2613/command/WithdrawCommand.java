package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;
import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private static final String BEFORE = "before";
    private static final String SUCCESS = "success.format";
    private static final String SPECIFY_AMOUNT = "specify.amount";
    private static final String SPECIFY_NOT_EMPTY_AMOUNT = "specify.not.empty.amount";
    private static final String NOT_ENOUGH_MONEY = "not.enough.money";
    private static final String AMOUNT_NOT_AVAILABLE = "exact.amount.not.available";

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString(BEFORE));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        int sum = 0;
        String sumString = null;
        while (sumString == null) {
            ConsoleHelper.writeMessage(res.getString(SPECIFY_AMOUNT));
            sumString = ConsoleHelper.readString();
            try {
                sum = Integer.parseInt(sumString);
                if (sum < 0) {
                    ConsoleHelper.writeMessage(res.getString(SPECIFY_NOT_EMPTY_AMOUNT));
                    throw new Exception();
                }
                if (!manipulator.isAmountAvailable(sum)){
                    ConsoleHelper.writeMessage(res.getString(NOT_ENOUGH_MONEY));
                    throw new Exception();
                }
            } catch (Exception e) {
                sumString = null;
                ConsoleHelper.writeMessage(res.getString(SPECIFY_NOT_EMPTY_AMOUNT));
            }
        }
        try {
            Map<Integer, Integer> amountMap = manipulator.withdrawAmount(sum);
            amountMap.forEach((k, v) -> System.out.println("\t" + k +" - " + v));
        } catch (NotEnoughMoneyException e) {
            ConsoleHelper.writeMessage(res.getString(AMOUNT_NOT_AVAILABLE));
        }

        ConsoleHelper.writeMessage(String.format(res.getString(SUCCESS), 0, sumString));
    }
}
