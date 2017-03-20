package com.javarush.task.task27.task2712.statistic.event;

import java.util.Date;

public class NoAvailableVideoEventDataRow implements EventDataRow {
    private final int totalDuration;
    private final Date currentDate;

    public NoAvailableVideoEventDataRow(int totalDuration) {
        this.totalDuration = totalDuration;
        currentDate = new Date();
    }

    public EventType getType() {
        return EventType.NO_AVAILABLE_VIDEO;
    }


    public Date getDate() {
        return currentDate;
    }

    public int getTime() {
        return totalDuration;
    }
}
