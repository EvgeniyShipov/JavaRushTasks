package com.javarush.task.task27.task2712.kitchen;

import java.util.Arrays;

public enum Dish {
    Fish(25),
    Steak(30),
    Soup(15),
    Juice(5),
    Water(3);

    Dish(int i) {
        duration = i;
    }

    public static String allDishesToString() {
        String allDishes = Arrays.toString(Dish.values());
        return allDishes.substring(1, allDishes.length() - 1);
    }

    private int duration;

    public int getDuration() {
        return duration;
    }
}
