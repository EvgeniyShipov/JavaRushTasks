package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) {
        testStrategy(new HashMapStorageStrategy(), 10_000);
        testStrategy(new OurHashBiMapStorageStrategy(), 10_000);
        testStrategy(new OurHashMapStorageStrategy(), 10_000);
        testStrategy(new HashBiMapStorageStrategy(), 10_000);
        testStrategy(new DualHashBidiMapStorageStrategy(), 10_000);
//        testStrategy(new FileStorageStrategy(), 100);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        return strings.stream().map(shortener::getId).collect(Collectors.toSet());
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        return keys.stream().map(shortener::getString).collect(Collectors.toSet());
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        System.out.println(strategy.getClass().getSimpleName());
        Set<String> strings = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            strings.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);

        Date beforeTest1 = new Date();
        Set<Long> ids = getIds(shortener, strings);
        System.out.println(new Date().getTime() - beforeTest1.getTime());

        Date beforeTest2 = new Date();
        Set<String> strings1 = getStrings(shortener, ids);
        System.out.println(new Date().getTime() - beforeTest2.getTime());

        System.out.println(strings1.size() == strings.size() ? "Тест пройден." : "Тест не пройден.");
    }
}
