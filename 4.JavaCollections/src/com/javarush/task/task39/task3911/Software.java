package com.javarush.task.task39.task3911;

import java.util.*;

public class Software {
    int currentVersion;

    private Map<Integer, String> versionHistoryMap = new LinkedHashMap<>();

    public void addNewVersion(int version, String description) {
        if (version > currentVersion) {
            versionHistoryMap.put(version, description);
            currentVersion = version;
        }
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public Map<Integer, String> getVersionHistoryMap() {
        return Collections.unmodifiableMap(versionHistoryMap);
    }

    public boolean rollback(int rollbackVersion) {
        if (!versionHistoryMap.containsKey(rollbackVersion))
            return false;

        Map<Integer, String> newHistory = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : versionHistoryMap.entrySet()) {
            Integer version = entry.getKey();
            String description = entry.getValue();
            newHistory.put(version, description);

            if (version == rollbackVersion)
                break;
        }

        versionHistoryMap = newHistory;
        currentVersion = rollbackVersion;
        return true;
    }
}
