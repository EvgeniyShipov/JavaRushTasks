package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private Model model;
    private View view;
    private final static int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    public void resetGame() {
        model.score = 0;
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_ESCAPE)
            resetGame();
        if (keyCode == KeyEvent.VK_Z)
            model.rollback();
        if (keyCode == KeyEvent.VK_R)
            model.randomMove();
        if (keyCode == KeyEvent.VK_A)
            model.autoMove();

        if (!model.canMove())
            view.isGameLost = true;

        if (!view.isGameWon && !view.isGameLost) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    model.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    model.right();
                    break;
                case KeyEvent.VK_UP:
                    model.up();
                    break;
                case KeyEvent.VK_DOWN:
                    model.down();
                    break;
            }
        }
        if (model.maxTile == WINNING_TILE)
            view.isGameWon = true;

        view.repaint();
    }

    public View getView() {
        return view;
    }
}
