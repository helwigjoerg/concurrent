package com.helwig.concurrent.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *The CountDownLatch class is another helpful class for thread synchronization from the JDK. 
 *Similar to the Semaphore class it provides a counter, but the counter of the CountDownLatch can only be decreased until it reaches zero.
 *Once the counter has reached zero all threads waiting on the CountDownLatch can proceed.
 * Such functionality is often necessary when all threads of a pool have to synchronize at some point in order to proceed.
 * A simple example would be an application that has to gather data from different sources before being able to store a new data set to the database.
 * 
 * @author Joerg Helwig
 *
 */
public class CountDownLatchExample implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
    private static Random random = new Random(System.currentTimeMillis());
 
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(new CountDownLatchExample());
        }
        executorService.shutdown();
    }
 
    public void run() {
        try {
            int randomSleepTime = random.nextInt(20000);
            System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
            Thread.sleep(randomSleepTime);
            latch.countDown();
            System.out.println("[" + Thread.currentThread().getName() + "] Waiting for latch.");
            latch.await();
            System.out.println("[" + Thread.currentThread().getName() + "] Finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}