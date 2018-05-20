package com.javarush.task.task35.task3505;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConvertableUtil {

    public static <T, V> Map<T, V> convert(List<? extends Convertable<T>> list) {
        Map<T, V> result = new HashMap<>();
        list.forEach(value -> result.put(value.getKey(), (V) value));
        return result;
    }
}
