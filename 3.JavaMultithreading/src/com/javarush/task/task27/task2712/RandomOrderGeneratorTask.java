package com.javarush.task.task27.task2712;

import java.util.List;

public class RandomOrderGeneratorTask implements Runnable {
    private final List<Tablet> tablets;
    private final int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        if (!tablets.isEmpty()) {
            Tablet tablet = tablets.get((int) (Math.random() * tablets.size()));
            while (true) {
                tablet.createTestOrder();
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}
