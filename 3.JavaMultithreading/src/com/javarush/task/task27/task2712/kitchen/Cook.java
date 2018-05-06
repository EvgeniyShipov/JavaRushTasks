package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

public class Cook extends Observable implements Runnable {
    private LinkedBlockingQueue<Order> queue;
    private final String name;
    private boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public Cook(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order) {
        busy = true;
        ConsoleHelper.writeMessage("Start cooking - " + order);
        try {
            Thread.sleep(10 * order.getTotalCookingTime());
        } catch (InterruptedException ignored) {
        }
        StatisticManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(), this.name,
                order.getTotalCookingTime() * 60, order.getDishes()));

        setChanged();
        notifyObservers(order);
        busy = false;
    }

    @Override
    public void run() {
        Thread thread = new Thread(() -> {
            while (true) {
                if (!queue.isEmpty())
                    this.startCookingOrder(queue.poll());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
