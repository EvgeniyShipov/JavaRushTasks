package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return READER.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        List<Dish> dishList = new ArrayList<>();
        writeMessage("What ypu want to eat? We have : " + Dish.allDishesToString() + ".");
        String dish;
        while (!((dish = readString()).equals("exit"))) {
            try {
                dishList.add(Dish.valueOf(dish));
            } catch (IllegalArgumentException e) {
                ConsoleHelper.writeMessage("Try again");
            }
        }
        return dishList;
    }
}
