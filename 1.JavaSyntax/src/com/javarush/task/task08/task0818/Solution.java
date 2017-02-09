package com.javarush.task.task08.task0818;

import java.util.HashMap;
import java.util.Map;

/* 
Только для богачей
*/

public class Solution {
    public static HashMap<String, Integer> createMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 450);
        map.put("2", 500);
        map.put("3", 550);
        map.put("4", 480);
        map.put("5", 610);
        map.put("6", 560);
        map.put("7", 1020);
        map.put("8", 325);
        map.put("9", 55);
        map.put("10", 500);
        return map;
    }

    public static void removeItemFromMap(HashMap<String, Integer> map) {
        HashMap<String, Integer> newMap = new HashMap<>(map);
        for (Map.Entry<String, Integer> entry: newMap.entrySet()) {
            if (entry.getValue() < 500) {
                map.remove(entry.getKey());
            }
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> map = createMap();
        removeItemFromMap(map);
    }
}