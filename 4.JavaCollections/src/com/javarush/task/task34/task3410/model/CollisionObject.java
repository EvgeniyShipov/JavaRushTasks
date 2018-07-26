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
                return y + Model.FIELD_CELL_SIZE == this.y && x == this.x;
            case DOWN:
                return y - Model.FIELD_CELL_SIZE == this.y && x == this.x;
            case RIGHT:
                return x - Model.FIELD_CELL_SIZE == this.x && y == this.y;
            case LEFT:
                return x + Model.FIELD_CELL_SIZE == this.x && y == this.y;
        }
        return false;
    }
}
