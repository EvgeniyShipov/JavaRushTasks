package com.javarush.task.task25.task2515;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Главный класс игры - Космос (Space)
 */
public class Space {
    private int width;
    private int height;

    private SpaceShip ship;
    private ArrayList<Ufo> ufos = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private ArrayList<Rocket> rockets = new ArrayList<>();

    public Space(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Основной цикл программы.
     * Тут происходят все важные действия
     */
    public void run() {
        Canvas canvas = new Canvas(width, height);

        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (ship.isAlive()) {
            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();
                System.out.print(event.getKeyCode());
                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    ship.moveLeft();
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    ship.moveRight();
                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ship.fire();
            }
            moveAllItems();
            checkBombs();
            checkRockets();
            removeDead();
            createUfo();
            canvas.clear();
            draw(canvas);
            canvas.print();
            Space.sleep(300);
        }
        System.out.println("Game Over!");
    }

    /**
     * Двигаем все объекты игры
     */
    public void moveAllItems() {
        getAllItems().forEach(BaseObject::move);
    }

    /**
     * Метод возвращает общий список, который содержит все объекты игры
     */
    public List<BaseObject> getAllItems() {
        ArrayList<BaseObject> list = new ArrayList<>(ufos);
        list.add(ship);
        list.addAll(bombs);
        list.addAll(rockets);
        return list;
    }

    /**
     * Создаем новый НЛО. 1 раз на 10 вызовов.
     */
    public void createUfo() {
        if (ufos.size() > 0)
            return;

        int random10 = (int) (Math.random() * 10);
        if (random10 == 0) {
            double x = Math.random() * width;
            double y = Math.random() * height / 2;
            ufos.add(new Ufo(x, y));
        }
    }

    /**
     * Проверяем бомбы.
     * а) столкновение с кораблем (бомба и корабль умирают)
     * б) падение ниже края игрового поля (бомба умирает)
     */
    public void checkBombs() {
        bombs.forEach(bomb -> {
            if (bomb.isIntersect(ship)) {
                ship.die();
                bomb.die();
            }
            if (bomb.getY() >= height)
                bomb.die();
        });
    }

    /**
     * Проверяем рокеты.
     * а) столкновение с НЛО (ракета и НЛО умирают)
     * б) вылет выше края игрового поля (ракета умирает)
     */
    public void checkRockets() {
        rockets.forEach(rocket -> ufos.forEach(ufo -> {
            if (rocket.isIntersect(ufo)) {
                ufo.die();
                rocket.die();
            }
            if (rocket.getY() <= 0)
                rocket.die();
        }));
    }

    /**
     * Удаляем умерсшие объекты (бомбы, ракеты, НЛО) из списков
     */
    public void removeDead() {
        bombs = (ArrayList<Bomb>) bombs.stream()
                .filter(Bomb::isAlive)
                .collect(Collectors.toList());
        rockets = (ArrayList<Rocket>) rockets.stream()
                .filter(Rocket::isAlive)
                .collect(Collectors.toList());
        ufos = (ArrayList<Ufo>) ufos.stream()
                .filter(Ufo::isAlive)
                .collect(Collectors.toList());
    }

    /**
     * Отрисовка всех объектов игры:
     * а) заполняем весь холст точесками.
     * б) отрисовываем все объекты на холст.
     */
    public void draw(Canvas canvas) {
        for (int i = 0; i < width + 2; i++) {
            for (int j = 0; j < height + 2; j++) {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++) {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++) {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }

        for (BaseObject object : getAllItems()) {
            object.draw(canvas);
        }
    }

    public SpaceShip getShip() {
        return ship;
    }

    public void setShip(SpaceShip ship) {
        this.ship = ship;
    }

    public ArrayList<Ufo> getUfos() {
        return ufos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public ArrayList<Rocket> getRockets() {
        return rockets;
    }

    public static Space game;

    public static void main(String[] args) throws Exception {
        game = new Space(20, 20);
        game.setShip(new SpaceShip(10, 18));
        game.run();
    }

    /**
     * Метод делает паузу длинной delay миллисекунд.
     */
    public static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }
}
