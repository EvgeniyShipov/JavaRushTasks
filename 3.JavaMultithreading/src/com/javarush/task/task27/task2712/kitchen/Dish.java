package com.javarush.task.task27.task2712.kitchen;

import java.util.Arrays;

public enum Dish {
    Fish,
    Steak,
    Soup,
    Juice,
    Water;

    public static String allDishesToString() {
        String allDishes = Arrays.toString(Dish.values());
        return allDishes.substring(1, allDishes.length() - 1);
    }
}
