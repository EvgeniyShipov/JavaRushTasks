package com.javarush.task.task33.task3310.strategy;

import java.util.Arrays;
import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10_000;
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize;

    public FileStorageStrategy() {
        for (int i = 0; i < table.length; i++) {
            table[i] = new FileBucket();
        }
    }

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
                .map(FileBucket::getEntry)
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
                .map(FileBucket::getEntry)
                .filter(Objects::nonNull)
                .filter(entry -> entry.key.equals(key))
                .findFirst()
                .orElse(null);
    }

    public void resize(int newCapacity) {
        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) {
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .forEach(fileBucket -> {
                    Entry entry = fileBucket.getEntry();
                    Entry next = entry.next;
                    int index = indexFor(entry.hash, newTable.length);
                    entry.next = newTable[index].getEntry();
                    newTable[index].putEntry(entry);
                    entry = next;
                    fileBucket.putEntry(entry);
                    fileBucket.remove();
                });
    }

    private void addEntry(final int hash, final Long key, final String value, final int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = currentBucketSize > maxBucketSize ? currentBucketSize : maxBucketSize;
        if (maxBucketSize > bucketSizeLimit)
            resize(2 * table.length);

    }

    private void createEntry(final int hash, final Long key, final String value, final int bucketIndex) {
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
        long currentBucketSize = table[bucketIndex].getFileSize();
        maxBucketSize = currentBucketSize > maxBucketSize ? currentBucketSize : maxBucketSize;
    }

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }
}
