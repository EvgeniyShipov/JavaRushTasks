package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        return (int) map.values().stream().mapToLong(Collection::size).sum();
    }

    @Override
    public V put(K key, V value) {
        List<V> list;
        V val = null;
        if ((list = map.get(key)) != null) {
            val = list.get(list.size() - 1);
            if (list.size() >= repeatCount)
                list.remove(0);
        } else
            list = new ArrayList<>();

        list.add(value);
        map.put(key, list);
        return val;
    }

    @Override
    public V remove(Object key) {
        V value = null;
        List<V> list = map.get(key);
        if (list != null) {
            if (list.size() == 1) {
                value = list.get(0);
                map.remove(key);
            } else {
                value = list.get(0);
                list.remove(0);
                return value;
            }
        }
        return value;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values().stream().flatMap(Collection::stream).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.values().stream().flatMap(Collection::stream).anyMatch(val -> val.equals(value));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}