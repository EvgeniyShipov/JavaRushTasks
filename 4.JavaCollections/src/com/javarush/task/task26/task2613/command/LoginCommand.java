package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private static final String BEFORE = "before";
    private static final String SPECIFY_DATA = "specify.data";
    private static final String SUCCESS = "success.format";
    private static final String NOT_VERIFIED_FORMAT = "not.verified.format";
    private static final String TRY_AGAIN_OR_EXIT = "try.again.or.exit";
    private static final String TRY_AGAIN_WITH_DETAILS = "try.again.with.details";

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString(BEFORE));
        String enterNumber = "";
        String enterPin = "";

        while (true) {
            ConsoleHelper.writeMessage(res.getString(SPECIFY_DATA));
            enterNumber = ConsoleHelper.readString();
            enterPin = ConsoleHelper.readString();

            if (validCreditCards.containsKey(enterNumber)) {
                if (validCreditCards.getString(enterNumber).equals(enterPin)) {
                    ConsoleHelper.writeMessage(String.format(res.getString(SUCCESS), enterNumber));
                    break;
                }
                ConsoleHelper.writeMessage(String.format(res.getString(NOT_VERIFIED_FORMAT), enterNumber));
            }
            ConsoleHelper.writeMessage(res.getString(TRY_AGAIN_WITH_DETAILS));
            ConsoleHelper.writeMessage(res.getString(TRY_AGAIN_OR_EXIT));
        }
    }
}
