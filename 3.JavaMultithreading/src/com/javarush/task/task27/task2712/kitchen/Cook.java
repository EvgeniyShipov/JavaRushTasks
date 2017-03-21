package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer {
    private final String name;

    public Cook(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public void update(Observable o, Object order) {
        Order or = (Order) order;
        ConsoleHelper.writeMessage("Start cooking - " + or);
        setChanged();
        notifyObservers(or);
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(or.getTablet().toString(), this.name, or.getTotalCookingTime() * 60, or.getDishes()));
    }
}
