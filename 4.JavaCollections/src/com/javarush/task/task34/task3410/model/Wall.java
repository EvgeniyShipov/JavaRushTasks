package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawPolygon(new int[]{0, this.getHeight(), this.getHeight(), 0},
                new int[]{this.getWidth(), this.getWidth(), 0, 0}, 4);
    }
}
