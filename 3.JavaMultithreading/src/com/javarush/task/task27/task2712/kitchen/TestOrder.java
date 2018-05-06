package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.Tablet;

import java.io.IOException;
import java.util.ArrayList;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected void initDishes() {
        dishes = new ArrayList<>();
        for (int i = 0; i < Math.random() * 5; i++) {
            dishes.add(Dish.values()[(int) (Math.random() * 5)]);
        }
    }
}
