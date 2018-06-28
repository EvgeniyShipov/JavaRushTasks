package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.command.CommandExecutor;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.Locale;

import static com.javarush.task.task26.task2613.ConsoleHelper.askOperation;
import static com.javarush.task.task26.task2613.Operation.EXIT;
import static com.javarush.task.task26.task2613.Operation.LOGIN;

public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";

    public static void main(String[] args) {
        try {
            Locale.setDefault(Locale.ENGLISH);

            CommandExecutor.execute(LOGIN);

            Operation operation;
            do {
                operation = askOperation();
                CommandExecutor.execute(operation);
            } while (operation != EXIT);
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
