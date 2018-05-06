package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet {
    private LinkedBlockingQueue<Order> queue;
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());


    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            createAdvertisementManager(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
        return order;
    }

    public String toString() {
        return "Tablet{number=" + number + "}";
    }

    public void createTestOrder() {
        try {
            TestOrder order = new TestOrder(this);
            createAdvertisementManager(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void createAdvertisementManager(Order order) {
        ConsoleHelper.writeMessage(order.toString());
        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        if (!order.isEmpty()) {
            advertisementManager.processVideos();
        }
        queue.add(order);
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }
}
