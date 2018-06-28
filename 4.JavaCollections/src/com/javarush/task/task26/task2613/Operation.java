package com.javarush.task.task26.task2613;

public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i < 1 || i > Operation.values().length - 1)
            throw new IllegalArgumentException();

        return Operation.values()[i];
    }
}
