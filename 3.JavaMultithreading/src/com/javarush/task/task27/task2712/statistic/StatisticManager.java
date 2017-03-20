package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

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

    public Map<Date, Long> allAmount() {
        HashMap<Date, Long> map = new HashMap<>();
        Date currentDate = new Date();

        while (currentDate.getDay() > 0) {
            long amountForDay = statisticStorage.getAmountForAdvertisement(currentDate);
            if (amountForDay != 0) {
                map.put(currentDate, amountForDay);
            }
            currentDate.setTime(currentDate.getTime() - 86400);
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

        private long getAmountForAdvertisement(Date date) {
            List<EventDataRow> eventDataRows = storage.get(EventType.SELECTED_VIDEOS);
            long allAmount = 0;
            for (EventDataRow eventDataRow: eventDataRows) {
                if (eventDataRow.getDate().getDay() == date.getDay()) {
                    VideoSelectedEventDataRow video = (VideoSelectedEventDataRow) eventDataRow;
                    long amount = video.getAmount();
                    allAmount = +amount;
                }
            }
            return allAmount;
        }
    }
}
