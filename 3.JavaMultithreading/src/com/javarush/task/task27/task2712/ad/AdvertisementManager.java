package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private final int timeSeconds;
    private List<Advertisement> bestList = null;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {
        makeAllSet(storage.list());
        if (bestList.isEmpty()) {
            throw new NoVideoAvailableException();
        } else {
            bestList.sort((o1, o2) -> {
                if (o2.getAmountPerOneDisplaying() != o1.getAmountPerOneDisplaying()) {
                    return (int) ((o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying()));
                } else {
                    return (int) (((o1.getAmountPerOneDisplaying() * 1000) / o1.getDuration() - (o2.getAmountPerOneDisplaying() * 1000) / o2.getDuration()));
                }
            });
            StatisticManager.getInstance().register(new VideoSelectedEventDataRow(bestList, allAmount(bestList), allDuration(bestList)));
            bestList.forEach(ad -> {
                ad.revalidate();
                ConsoleHelper.writeMessage(String.format("%s is displaying... %d, %d", ad.getName(),
                        ad.getAmountPerOneDisplaying(), ((ad.getAmountPerOneDisplaying() * 1000) / ad.getDuration())));
            });
        }
    }

    private void checkList(List<Advertisement> list) {
        boolean allHitsIsPositove = true;

        for (Advertisement ad : list) {
            if (ad.getHits() <= 0) {
                allHitsIsPositove = false;
            }
        }
        if (allHitsIsPositove) {
            if (bestList == null) {
                if (allDuration(list) <= timeSeconds) {
                    bestList = list;
                }
            } else if (allDuration(list) <= timeSeconds && allAmount(list) >= allAmount(bestList)) {
                if (allAmount(list) == allAmount(bestList)) {
                    if (allDuration(list) > allDuration(bestList)) {
                        bestList = list;
                    }
                    if (allDuration(list) == allDuration(bestList) && list.size() < bestList.size()) {
                        bestList = list;
                    }
                } else {
                    bestList = list;
                }
            }
        }
    }

    private void makeAllSet(List<Advertisement> list) {
        if (list.size() > 0) {
            checkList(list);
        }
        for (int i = 0; i < list.size(); i++) {
            List<Advertisement> advertisements = new ArrayList<>(list);
            advertisements.remove(i);
            makeAllSet(advertisements);
        }
    }

    private int allDuration(List<Advertisement> list) {
        return list.stream()
                .map(Advertisement::getDuration)
                .reduce((a, b) -> a + b)
                .orElse(0);
    }

    private long allAmount(List<Advertisement> list) {
        return list.stream()
                .map(Advertisement::getAmountPerOneDisplaying)
                .reduce((a, b) -> a + b)
                .orElse(0L);
    }
}
