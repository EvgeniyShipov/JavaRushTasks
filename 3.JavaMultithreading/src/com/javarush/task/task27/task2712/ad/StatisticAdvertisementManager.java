package com.javarush.task.task27.task2712.ad;

import java.util.List;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance = new StatisticAdvertisementManager();
    private final AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager() {
    }

    public static StatisticAdvertisementManager getInstance() {
        return instance;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisementStorage.list();
    }
}
