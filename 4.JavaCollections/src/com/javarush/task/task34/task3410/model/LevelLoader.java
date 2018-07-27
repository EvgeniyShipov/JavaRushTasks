package com.javarush.task.task34.task3410.model;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.javarush.task.task34.task3410.model.Model.FIELD_CELL_SIZE;

public class LevelLoader {

    private final Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        Set<Wall> walls = new HashSet<>(Arrays.asList(
                new Wall(FIELD_CELL_SIZE, FIELD_CELL_SIZE),
                new Wall(FIELD_CELL_SIZE, FIELD_CELL_SIZE),
                new Wall(FIELD_CELL_SIZE, FIELD_CELL_SIZE)));
        Set<Box> boxes = new HashSet<>(Arrays.asList((new Box(FIELD_CELL_SIZE, FIELD_CELL_SIZE))));
        Set<Home> homes = new HashSet<>(Arrays.asList((new Home(FIELD_CELL_SIZE, FIELD_CELL_SIZE))));
        Player player = new Player(FIELD_CELL_SIZE, FIELD_CELL_SIZE);

        return new GameObjects(walls, boxes, homes, player);
    }

    private int random() {
        return FIELD_CELL_SIZE * new Random().nextInt(10);
    }
}
