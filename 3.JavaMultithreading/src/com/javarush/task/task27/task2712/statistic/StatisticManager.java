package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private static StatisticManager ourInstance = new StatisticManager();
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    public static StatisticManager getInstance() {
        return ourInstance;
    }

    private StatisticManager() {
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        cooks.add(cook);
    }

    public Map<Date, Double> allAmount() {
        HashMap<Date, Double> map = new HashMap<>();
        Date currentDate = new Date();

        while (currentDate.getTime() > 0) {
            double amountForDay = statisticStorage.getAmountForAdvertisement(currentDate);
            if (amountForDay != 0) {
                map.put(new Date(currentDate.getTime()), amountForDay);
            }
            currentDate.setTime(currentDate.getTime() - 86400000);
        }
        return map;
    }

    public Map<Date, List<String>> cookTime() {
        HashMap<Date, List<String>> map = new HashMap<>();
        Date currentDate = new Date();
        while (currentDate.getTime() > 0) {
            Map<String, Integer> cookWorkingTime = statisticStorage.getCookWorkingTime(currentDate);
            if (!cookWorkingTime.isEmpty()) {
                ArrayList<String> cooksList = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : cookWorkingTime.entrySet()) {
                    String cook = entry.getKey();
                    int time = entry.getValue();
                    cooksList.add(cook + " - " + time + " min");
                }
                map.put(new Date(currentDate.getTime()), cooksList);
            }
            currentDate.setTime(currentDate.getTime() - 86400000);
        }
        return map;
    }


    private class StatisticStorage {
        private final Map<EventType, List<EventDataRow>> storage;

        public StatisticStorage() {
            storage = new HashMap<>();
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            storage.get(data.getType()).add(data);
        }

        private double getAmountForAdvertisement(Date date) {
            List<EventDataRow> eventDataRows = storage.get(EventType.SELECTED_VIDEOS);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            double allAmount = 0;
            for (EventDataRow eventDataRow : eventDataRows) {
                if (dateFormat.format(eventDataRow.getDate()).equals(dateFormat.format(date))) {
                    VideoSelectedEventDataRow video = (VideoSelectedEventDataRow) eventDataRow;
                    allAmount += video.getAmount();
                }
            }
            return allAmount;
        }

        private Map<String, Integer> getCookWorkingTime(Date date) {
            HashMap<String, Integer> map = new HashMap<>();
            List<EventDataRow> eventDataRows = storage.get(EventType.COOKED_ORDER);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            for (EventDataRow eventDataRow : eventDataRows) {
                if (dateFormat.format(eventDataRow.getDate()).equals(dateFormat.format(date))) {
                    CookedOrderEventDataRow cooked = (CookedOrderEventDataRow) eventDataRow;
                    if (map.containsKey(cooked.getCookName())) {
                        map.put(cooked.getCookName(), map.get(cooked.getCookName()) + (cooked.getTime() / 60));
                    } else {
                        map.put(cooked.getCookName(), (cooked.getTime() / 60));
                    }
                }
            }
            return map;
        }
    }
}
