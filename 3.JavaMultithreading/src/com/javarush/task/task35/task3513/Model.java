package com.javarush.task.task35.task3513;

import java.util.*;
import java.util.stream.Stream;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
    int maxTile = 2;
    int score = 0;

    private Stack previousStates = new Stack();
    private Stack previousScores = new Stack();
    private boolean isSaveNeeded = true;

    public Model() {
        resetGameTiles();
    }

    public boolean canMove() {
        for (int i = 0; i < gameTiles.length - 1; i++)
            for (int j = 0; j < gameTiles[i].length - 1; j++)
                if (gameTiles[i][j].value == 0
                        || gameTiles[i][j].value == gameTiles[i][j + 1].value
                        || gameTiles[i][j].value == gameTiles[i + 1][j].value)
                    return true;
        return false;
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty())
            emptyTiles.get((int) (Math.random() * emptyTiles.size())).value = (Math.random() < 0.9 ? 2 : 4);
    }

    private List<Tile> getEmptyTiles() {
        ArrayList<Tile> list = new ArrayList<>();
        Stream.of(gameTiles).flatMap(Stream::of).filter(Tile::isEmpty).forEach(list::add);
        return list;
    }

    void resetGameTiles() {
        Stream.of(gameTiles).forEach(tiles -> {
            for (int i = 0; i < tiles.length; i++)
                tiles[i] = new Tile();
        });
        addTile();
        addTile();
    }

    void left() {
        if (isSaveNeeded)
            saveState(gameTiles);
        doStep();
        isSaveNeeded = true;
    }

    void right() {
        saveState(gameTiles);
        rotate();
        rotate();
        doStep();
        rotate();
        rotate();
    }

    void up() {
        saveState(gameTiles);
        rotate();
        rotate();
        rotate();
        doStep();
        rotate();
    }

    void down() {
        saveState(gameTiles);
        rotate();
        doStep();
        rotate();
        rotate();
        rotate();
    }

    private void doStep() {
        boolean isChanged = false;
        for (Tile[] tiles : gameTiles) {
            if (compressTiles(tiles) || mergeTiles(tiles))
                isChanged = true;
        }
        if (isChanged)
            addTile();
    }

    private void rotate() {
        Tile[][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles[i].length; j++) {
                temp[i][j] = gameTiles[gameTiles.length - j - 1][i];
            }
        }
        gameTiles = temp;
    }

    private boolean compressTiles(Tile[] tiles) {
        boolean isChange = false;
        int i = 0;
        boolean needRepeat = false;
        while (i < FIELD_WIDTH - 1) {
            if (tiles[i].isEmpty() && !tiles[i + 1].isEmpty()) {
                Tile temp = tiles[i + 1];
                tiles[i + 1] = tiles[i];
                tiles[i] = temp;
                needRepeat = true;
                isChange = true;
            }
            i++;
        }
        if (needRepeat)
            compressTiles(tiles);

        return isChange;
    }

    private boolean mergeTiles(Tile[] tiles) {
        int newScore = score;
        int i = 0;
        while (i < FIELD_WIDTH - 1) {
            if (tiles[i].value == tiles[i + 1].value) {
                tiles[i].value *= 2;
                tiles[i + 1].value = 0;
                compressTiles(tiles);
                newScore += tiles[i].value;
                if (tiles[i].value > maxTile)
                    maxTile = tiles[i].value;
            }
            i++;
        }
        if (newScore != score) {
            score = newScore;
            return true;
        }

        return false;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private void saveState(Tile[][] gameTiles) {
        Tile[][] tiles = new Tile[gameTiles.length][gameTiles.length];
        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++)
                tiles[i][j] = new Tile(gameTiles[i][j].value);
        }
        previousStates.push(tiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles = (Tile[][]) previousStates.pop();
            score = (int) previousScores.pop();
        }
    }

    public void randomMove() {
        int direction = ((int) (Math.random() * 100)) % 4;
        switch (direction) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    public boolean hasBoardChanged() {
        int sum = Arrays.stream(gameTiles)
                .flatMap(Arrays::stream)
                .map(tile -> tile.value)
                .reduce((a, b) -> a + b)
                .orElse(0);

        Tile[][] previous = previousStates.isEmpty() ? gameTiles : (Tile[][]) previousStates.peek();
        int sum2 = Arrays.stream(previous)
                .flatMap(Arrays::stream)
                .map(tile -> tile.value)
                .reduce((a, b) -> a + b)
                .orElse(0);

        return sum != sum2;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency result = hasBoardChanged() ?
                new MoveEfficiency(getEmptyTiles().size(), score, move) :
                new MoveEfficiency(-1, 0, move);

        rollback();
        return result;
    }

    public void autoMove() {
        PriorityQueue<MoveEfficiency> queue = new PriorityQueue<>(4, Collections.reverseOrder());
        queue.add(getMoveEfficiency(this::up));
        queue.add(getMoveEfficiency(this::down));
        queue.add(getMoveEfficiency(this::right));
        queue.add(getMoveEfficiency(this::left));
        queue.poll().getMove().move();
    }
}