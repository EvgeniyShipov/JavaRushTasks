package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.Advertisement;
import com.javarush.task.task27.task2712.ad.StatisticAdvertisementManager;
import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {
    public void printAdvertisementProfit() {
        Map<Date, Double> map = StatisticManager.getInstance().allAmount();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        double total = 0;
        for (Map.Entry<Date, Double> entry : map.entrySet()) {
            Date date = entry.getKey();
            Double amount = entry.getValue();
            System.out.println(dateFormat.format(date) + " - " + String.format(Locale.ENGLISH, "%(.2f", amount / 100));
            total += amount;
        }
        System.out.println("Total - " + String.format(Locale.ENGLISH, "%(.2f", total / 100));
        System.out.println();
    }

    public void printCookWorkloading() {
        Map<Date, List<String>> map = StatisticManager.getInstance().cookTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, List<String>> entry : map.entrySet()) {
            Date date = entry.getKey();
            List<String> cooks = entry.getValue();
            System.out.println(dateFormat.format(date));
            for (String s : cooks) {
                System.out.println(s);
            }
            System.out.println();
        }
    }

    public void printActiveVideoSet() {
        StatisticAdvertisementManager.getInstance().getAdvertisements().stream()
                .filter(advertisement -> advertisement.getHits() > 0)
                .sorted(Comparator.comparing(advertisement -> advertisement.getName().toLowerCase()))
                .forEach(advertisement -> System.out.println(String.format("%s - %d", advertisement.getName(), advertisement.getHits())));
    }

    public void printArchivedVideoSet() {
        StatisticAdvertisementManager.getInstance().getAdvertisements().stream()
                .filter(advertisement -> advertisement.getHits() == 0)
                .sorted(Comparator.comparing(advertisement -> advertisement.getName().toLowerCase()))
                .map(Advertisement::getName)
                .forEach(System.out::println);
    }
}
