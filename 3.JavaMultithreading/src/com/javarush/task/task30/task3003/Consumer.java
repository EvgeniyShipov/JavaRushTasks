package com.javarush.task.task30.task3003;

import java.util.concurrent.TransferQueue;


public class Consumer implements Runnable {
    private TransferQueue<ShareItem> queue;

    public Consumer(TransferQueue<ShareItem> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            Thread.sleep(450);
            while (true) {
                ShareItem take = queue.take();
                System.out.format("Processing " + take.toString() + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
