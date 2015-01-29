package com.helwig.concurrent.util;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * n contrast to the CountDownLatch, the CyclicBarrier class implements a counter that can be reset after being counted down to zero. 
 * All threads have to call its method await() until the internal counter is set to zero. 
 * The waiting threads are then woken up and can proceed. Internally the counter is then reset to its original value
 *  and the whole procedure can start again:
 * 
 * @author Joerg Helwig
 *
 */
public class CyclicBarrierExample implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private static AtomicInteger counter = new AtomicInteger();
    private static Random random = new Random(System.currentTimeMillis());
    private static final CyclicBarrier barrier = new CyclicBarrier(5, new Runnable() {
        public void run() {
            counter.incrementAndGet();
        }
    });
 
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(new CyclicBarrierExample());
        }
        executorService.shutdown();
    }
 
    public void run() {
        try {
            while(counter.get() < 3) {
                int randomSleepTime = random.nextInt(10000);
                System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
                Thread.sleep(randomSleepTime);
                System.out.println("[" + Thread.currentThread().getName() + "] Waiting for barrier.");
                barrier.await();
                System.out.println("[" + Thread.currentThread().getName() + "] Finished.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
