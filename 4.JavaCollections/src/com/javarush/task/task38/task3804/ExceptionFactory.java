package com.javarush.task.task38.task3804;

public class ExceptionFactory {

    public static Throwable getException(Enum en) {
        if (en != null) {
            String message = en.name().substring(0, 1).concat(en.name().substring(1).toLowerCase().replace("_", " "));

            if (en instanceof ExceptionApplicationMessage)
                return new Exception(message);
            if (en instanceof ExceptionDBMessage)
                return new RuntimeException(message);
            if (en instanceof ExceptionUserMessage)
                return new Error(message);
        }
        return new IllegalArgumentException();
    }
}
