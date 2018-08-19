package com.javarush.task.task34.task3410.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class LevelLoader {

    private final Path levels;

    public LevelLoader(Path levels) {
        this.levels = levels;
    }

    public GameObjects getLevel(int level) {
        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = null;
        if (level > 60)
            level = level % 60;

        int readLevel = 0;
        int x;
        int y = Model.FIELD_CELL_SIZE / 2;
        boolean isLevelMap = false;

        try {
            for (String line : Files.readAllLines(levels)) {
                if (line.contains("Maze:")) {
                    readLevel = Integer.valueOf(line.split(" ")[1]);
                    continue;
                }
                if (readLevel == level) {
                    if (line.length() == 0) {
                        boolean isEnd = isLevelMap;
                        isLevelMap = !isLevelMap;
                        if (isEnd && !isLevelMap) {
                            break;
                        } else {
                            continue;
                        }
                    }
                    if (isLevelMap) {
                        x = Model.FIELD_CELL_SIZE / 2;

                        for (char c : line.toCharArray()) {
                            switch (c) {
                                case 'X':
                                    walls.add(new Wall(x, y));
                                    break;
                                case '*':
                                    boxes.add(new Box(x, y));
                                    break;
                                case '.':
                                    homes.add(new Home(x, y));
                                    break;
                                case '&':
                                    boxes.add(new Box(x, y));
                                    homes.add(new Home(x, y));
                                    break;
                                case '@':
                                    player = new Player(x, y);
                            }
                            x += Model.FIELD_CELL_SIZE;
                        }
                        y += Model.FIELD_CELL_SIZE;
                    }
                }
            }
        } catch (IOException e) {

        }
        return new GameObjects(walls, boxes, homes, player);
    }
}
