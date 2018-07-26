package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Box extends CollisionObject implements Movable {

    public Box(int x, int y) {
        super(x, y);
    }

    @Override
    public void move(int x, int y) {
        setX(getX() + x);
        setY(getY() + y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.drawPolygon(new int[]{0, this.getHeight(), this.getHeight(), 0},
                new int[]{this.getWidth(), this.getWidth(), 0, 0}, 4);
    }
}
