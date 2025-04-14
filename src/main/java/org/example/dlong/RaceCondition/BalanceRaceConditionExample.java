package org.example.dlong.RaceCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BalanceRaceConditionExample {
    private static final int TOTAL_REQUEST = 20000;

    private static int balance = 0;
    private static AtomicInteger sequence = new AtomicInteger(0);


    synchronized private static void increaseBalance() {
        balance += 1;
        System.out.println(sequence.incrementAndGet() + " + balance: " + balance);
    }

    synchronized private static void decreaseBalance() {
        balance -= 1;
        System.out.println(sequence.incrementAndGet() + " - balance: " +balance);
    }


    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        List<Thread> allThreads = new ArrayList<Thread>();
        for (int i = 0; i < TOTAL_REQUEST; i++) {
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    increaseBalance();
                    decreaseBalance();
                }
            });
            allThreads.add(t);
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
        System.out.println("Time execute: " + (System.currentTimeMillis() - start) + " ms");
    }

}
