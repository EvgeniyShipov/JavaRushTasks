package com.javarush.task.task26.task2613;

import java.util.HashMap;
import java.util.Map;

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
        int countInManipulator = denominations.get(denomination) == null ? 0 : denominations.get(denomination);
        denominations.put(denomination, count + countInManipulator);
    }

    public int getTotalAmount() {
        return denominations.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue())
                .reduce((a, b) -> a + b).orElse(0);
    }
}
