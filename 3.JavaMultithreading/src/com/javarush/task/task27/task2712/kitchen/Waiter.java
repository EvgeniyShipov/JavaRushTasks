package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.util.Observable;
import java.util.Observer;

public class Waiter implements Observer {

    public void update(Observable observable, Object order) {
        ConsoleHelper.writeMessage(String.format("%s was cooked by %s", order, observable));
    }
}
