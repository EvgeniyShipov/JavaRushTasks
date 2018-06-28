package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command {
    private static final String EXIT_QUESTION = "exit.question.y.n";
    private static final String EXIT_MESAGE = "thank.message";

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString(EXIT_QUESTION));
        String exit = ConsoleHelper.readString();
        if ("y".equals(exit))
            ConsoleHelper.writeMessage(res.getString(EXIT_MESAGE));

    }
}
