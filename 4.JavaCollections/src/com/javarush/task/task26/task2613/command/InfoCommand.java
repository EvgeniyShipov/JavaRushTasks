package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;


class InfoCommand implements Command {
    private static final String BEFORE = "before";
    private static final String NO_MONEY = "no.money";

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString(BEFORE));
        Collection<CurrencyManipulator> allCurrencyManipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        boolean hasMoney = allCurrencyManipulators.stream()
                .anyMatch(CurrencyManipulator::hasMoney);
        if (!hasMoney)
            ConsoleHelper.writeMessage(res.getString(NO_MONEY));
        else
            allCurrencyManipulators.forEach(manipulator ->
                    ConsoleHelper.writeMessage(manipulator.getCurrencyCode() + " - " + manipulator.getTotalAmount()));
    }
}
