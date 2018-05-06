package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Restaurant {
    private static final LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();
    private final static int ORDER_CREATING_INTERVAL = 100;

    public static void main(String[] args) {
        Cook cook = new Cook("Amigo");
        cook.setQueue(orderQueue);
        Cook cook2 = new Cook("Jimbo");
        cook2.setQueue(orderQueue);

        StatisticManager statisticManager = StatisticManager.getInstance();
        statisticManager.register(cook);
        statisticManager.register(cook2);

        Waiter waiter = new Waiter();
        cook.addObserver(waiter);
        cook2.addObserver(waiter);

        ArrayList<Tablet> tablets = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Tablet tablet = new Tablet(i);
            tablet.setQueue(orderQueue);
            tablets.add(tablet);
        }
        RandomOrderGeneratorTask task = new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL);
        Thread thread = new Thread(task);
        thread.start();

        new Thread(cook).start();
        new Thread(cook2).start();

        try {
            Thread.sleep(1_000);
            thread.interrupt();
        } catch (InterruptedException ignored) {
        }

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkloading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }
}
