package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;
import java.util.stream.Collectors;

public class CurrencyManipulator {

    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        int countInManipulator = denominations.getOrDefault(denomination, 0);
        denominations.put(denomination, count + countInManipulator);
    }

    public int getTotalAmount() {
        return denominations.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue())
                .reduce((a, b) -> a + b).orElse(0);
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> result = new HashMap<>();

        Set<Integer> keys = getSortedSet();
        Iterator<Integer> iterator = keys.iterator();

        while (iterator.hasNext()) {
            int denomination = iterator.next();
            int count = denominations.get(denomination);
            while (denomination <= expectedAmount && count > 0) {
                count--;
                expectedAmount -= denomination;
                result.put(denomination, result.getOrDefault(denomination, 0) + 1);
            }
        }

        if (expectedAmount == 0) {
            result.forEach((k, v) -> denominations.put(k, denominations.get(k) - v));
            return result;
        } else
            throw new NotEnoughMoneyException();
    }

    private Set<Integer> getSortedSet() {
        return denominations.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new))
                .keySet();
    }
}