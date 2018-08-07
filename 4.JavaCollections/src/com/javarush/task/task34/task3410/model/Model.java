package com.javarush.task.task34.task3410.model;

import com.javarush.task.task34.task3410.controller.EventListener;

import java.nio.file.Paths;

public class Model {
    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 1;
    private LevelLoader levelLoader = new LevelLoader(Paths.get("com/javarush/task/task34/task3410/res/levels.txt"));

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void restartLevel(int level) {
        gameObjects = levelLoader.getLevel(level);
    }

    public void restart() {
        restartLevel(currentLevel);
    }

    public void startNextLevel() {
        restartLevel(++currentLevel);
    }

    public void move(Direction direction) {
        Player player = gameObjects.getPlayer();
        if (checkWallCollision(player, direction) || checkBoxCollisionAndMoveIfAvaliable(direction))
            return;

        moveObject(player, direction);
        checkCompletion();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        return gameObjects.getWalls().stream()
                .anyMatch(wall -> gameObject.isCollision(wall, direction));
    }

    public boolean checkBoxCollisionAndMoveIfAvaliable(Direction direction) {
        for (Box box : gameObjects.getBoxes()) {
            if (gameObjects.getPlayer().isCollision(box, direction)) {
                for (Box anotherBox : gameObjects.getBoxes()) {
                    if (box.isCollision(anotherBox, direction) || checkWallCollision(box, direction))
                        return true;
                }
                moveObject(box, direction);
            }
        }
        return false;
    }

    private void moveObject(Movable object, Direction direction) {
        switch (direction) {
            case UP:
                object.move(0, -FIELD_CELL_SIZE);
                break;
            case DOWN:
                object.move(0, FIELD_CELL_SIZE);
                break;
            case LEFT:
                object.move(-FIELD_CELL_SIZE, 0);
                break;
            case RIGHT:
                object.move(FIELD_CELL_SIZE, 0);
                break;
        }
    }

    public void checkCompletion() {
        long boxes = gameObjects.getBoxes().stream()
                .mapToLong(box -> gameObjects.getHomes().stream()
                        .filter(home -> box.getX() == home.getX() && box.getY() == home.getY())
                        .count())
                .sum();

        if (boxes == gameObjects.getBoxes().size()) {
            eventListener.levelCompleted(currentLevel);
        }
    }
}
