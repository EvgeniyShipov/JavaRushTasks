package com.javarush.task.task33.task3310.strategy;

import java.util.Arrays;
import java.util.Objects;

public class OurHashMapStorageStrategy implements StorageStrategy {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    private float loadFactor = DEFAULT_LOAD_FACTOR;

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        return getKey(value) != null;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int bucket = indexFor(hash, table.length);
        addEntry(hash, key, value, bucket);
    }

    @Override
    public Long getKey(String value) {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .filter(entry -> entry.getValue().equals(value))
                .map(Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getValue(Long key) {
        return getEntry(key) == null ? null : getEntry(key).getValue();
    }

    public int hash(Long key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .filter(entry -> entry.key.equals(key))
                .findFirst()
                .orElse(null);
    }

    public void resize(int newCapacity) {
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    public void transfer(Entry[] newTable) {
        int newCapacity = newTable.length;
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .forEach(entry -> {
                    int i = indexFor(entry.hash, newCapacity);
                    entry.next = newTable[i];
                    newTable[i] = entry;
                    entry = entry.next;
                });
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new Entry(hash, key, value, table[bucketIndex]);
        if (size++ >= threshold)
            resize(2 * table.length);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new Entry(hash, key, value, table[bucketIndex]);
        size++;
    }
}
