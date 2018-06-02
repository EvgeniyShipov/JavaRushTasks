package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    @Test
    public void testHashMapStorage() {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());
        Set<String> origStrings = new HashSet<>();
        for (int i = 0; i < 10_000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        HashSet<Long> objects1 = new HashSet<>();
        HashSet<Long> objects2 = new HashSet<>();
        long time1 = getTimeForGettingIds(shortener1, origStrings, objects1);
        long time2 = getTimeForGettingIds(shortener2, origStrings, objects2);

        Assert.assertTrue(time1 > time2);

        long time11 = getTimeForGettingStrings(shortener1, objects1, origStrings);
        long time22 = getTimeForGettingStrings(shortener2, objects2, origStrings);

        Assert.assertEquals(time11, time22, 30);
    }

    public long getTimeForGettingIds(Shortener shortener, Set<String> strings, Set<Long> ids) {
        Date date = new Date();
        strings.stream().map(shortener::getId).forEach(ids::add);
        return new Date().getTime() - date.getTime();
    }

    public long getTimeForGettingStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {
        Date date = new Date();
        ids.stream().map(shortener::getString).forEach(strings::add);
        return new Date().getTime() - date.getTime();
    }
}
