package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.util.Date;
import java.util.Map;

public class DirectorTablet {
    public void printAdvertisementProfit() {
        Map<Date, Long> map = StatisticManager.getInstance().allAmount();
        long total = 0;
        for (Map.Entry<Date, Long> entry : map.entrySet()) {
            Date date = entry.getKey();
            Long amount = entry.getValue();
            System.out.println(date + " - " + amount);
            total += amount;
        }
        System.out.println("Total - " + total);
    }

    public void printCookWorkloading() {

    }

    public void printActiveVideoSet() {

    }

    public void printArchivedVideoSet() {

    }
}
