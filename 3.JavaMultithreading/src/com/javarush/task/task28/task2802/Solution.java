package com.javarush.task.task28.task2802;


import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* 
Пишем свою ThreadFactory
*/
public class Solution {

    public static class AmigoThreadFactory implements ThreadFactory {
        private static AtomicInteger factoryCount = new AtomicInteger(0);
        private AtomicInteger factory = new AtomicInteger(0);
        private AtomicInteger threadNumber = new AtomicInteger(0);

        public AmigoThreadFactory() {
            factory.set(factoryCount.incrementAndGet());
        }

        public Thread newThread(Runnable r) {
            threadNumber.incrementAndGet();
            Thread thread = new Thread(r);
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            String threadGroup = Thread.currentThread().getThreadGroup().getName();
            String threadName = String.format("%s-pool-%s-thread-%s", threadGroup, factory.get(), threadNumber.get());
            thread.setName(threadName);
            return thread;
        }
    }

    public static void main(String[] args) {
        class EmulateThreadFactoryTask implements Runnable {
            @Override
            public void run() {
                emulateThreadFactory();
            }
        }

        ThreadGroup group = new ThreadGroup("firstGroup");
        Thread thread = new Thread(group, new EmulateThreadFactoryTask());

        ThreadGroup group2 = new ThreadGroup("secondGroup");
        Thread thread2 = new Thread(group2, new EmulateThreadFactoryTask());

        thread.start();
        thread2.start();
    }

    private static void emulateThreadFactory() {
        AmigoThreadFactory factory = new AmigoThreadFactory();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };
        factory.newThread(r).start();
        factory.newThread(r).start();
        factory.newThread(r).start();
    }
}
