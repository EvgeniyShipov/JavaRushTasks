package com.javarush.task.task34.task3410.model;

public abstract class CollisionObject extends GameObject {

    public CollisionObject(int x, int y) {
        super(x, y);
    }

    public boolean isCollision(GameObject gameObject, Direction direction) {
        int x = gameObject.getX();
        int y = gameObject.getY();
        switch (direction) {
            case UP:
                return y + Model.FIELD_CELL_SIZE == getY() && x == getX();
            case DOWN:
                return y - Model.FIELD_CELL_SIZE == getY() && x == getX();
            case RIGHT:
                return x - Model.FIELD_CELL_SIZE == getX() && y == getY();
            case LEFT:
                return x + Model.FIELD_CELL_SIZE == getX() && y == getY();
        }
        return false;
    }
}
