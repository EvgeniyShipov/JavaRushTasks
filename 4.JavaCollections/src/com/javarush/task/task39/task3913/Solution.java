package com.javarush.task.task39.task3913;

import java.nio.file.Paths;

public class Solution {
    public static void main(String[] args) {
        LogParser logParser = new LogParser(Paths.get("c:/logs/"));

        System.out.println(logParser.execute("get ip"));
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\""));
        System.out.println(logParser.execute("get event for date = \"30.01.2014 12:56:22\""));
        System.out.println(logParser.execute("get date for event = LOGIN"));
        System.out.println(logParser.execute("get date for user = Amigo"));
        System.out.println(logParser.execute("get event for user = Amigo"));
        System.out.println(logParser.execute("get status for user = Amigo"));
        System.out.println(logParser.execute("get date for event = LOGIN"));
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));
    }
}