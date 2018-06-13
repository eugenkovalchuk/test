package ua.mk.kovalchuk;

import java.util.Random;

public class Threader {

    private static boolean exit = false;
    private static volatile Container items;
    private static Locker locker;
    private static Random rnd;

    public static void main(String[] args) throws InterruptedException {
        int X;
        long Y;
        if (args.length < 2) {
            System.out.println("Not enough parammetrs");
            return;
        }
        try {
            X = Integer.valueOf(args[0]);
            Y = Long.valueOf(args[1]);
        } catch (Exception err) {
            System.out.println("Wrong commandline arg: " + err.getMessage());
            System.console().readLine();
            return;
        }

        Thread[] threads = new Thread[X];
        items = new Container(Y);
        locker = new Locker(true);
        rnd = new Random();

        System.out.println("Threads count: " + X + " , container size: " + Y);

        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Added((long) i);
            threads[i].setDaemon(true);
            threads[i].start();
            Thread.sleep(100);
        }

        exit = true;
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            System.out.println("Thread :" + i + " " + items.getCount(i) + "elements");
        }
        System.out.println("Max elements : " + items.getMaximum());

    }

    private static class Added extends Thread {
        private long item;

        public Added(Long item) {
            this.item = item;
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    if (exit)
                        break;
                    locker.waitOne();
                    items.add(item);
                    locker.set();
                    Thread.sleep(rnd.nextInt(10000));
                } catch (Exception err) {
                    err.printStackTrace();
                    System.out.println("Error in thread" + item + " " + err.getMessage());
                    return;
                }
            }
        }
    }
}
