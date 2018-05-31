package com.javarush.task.task39.task3913;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogString {
    private String ip;
    private String name;
    private Date date;
    private Event event;
    private Status status;
    private int task;

    public LogString(String logString) {
        String[] split = logString.split("\t");
        this.ip = split[0];
        this.name = split[1];
        this.date = convertToDate(split[2]);
        String[] eventTask = split[3].split(" ");
        this.event = Event.valueOf(eventTask[0]);
        if (eventTask.length > 1)
            this.task = Integer.parseInt(eventTask[1]);
        this.status = Status.valueOf(split[4]);
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Event getEvent() {
        return event;
    }

    public int getTask() {
        return task;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isInDate(Date after, Date before) {
        return date.before(before == null ? new Date(Long.MAX_VALUE) : before)
                && date.after(after == null ? new Date(0) : after);
    }

    static Date convertToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public boolean isIp(String ip) {
        return ip == null || this.getIp().equals(ip);
    }

    public boolean isName(String name) {
        return name == null || this.getName().equals(name);
    }

    public boolean isDate(Date date) {
        return date == null || this.getDate().equals(date);
    }

    public boolean isEvent(Event event) {
        return event == null || this.getEvent().equals(event);
    }

    public boolean isStatus(Status status) {
        return status == null || this.getStatus().equals(status);
    }
}
