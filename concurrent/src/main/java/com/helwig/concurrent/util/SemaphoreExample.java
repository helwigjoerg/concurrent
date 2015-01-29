package com.helwig.concurrent.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Semaphores are used to control access to a shared resource. In contrast to simple synchronized blocks 
 * a semaphore has an internal counter that is increased each time a thread acquires a lock and decreased 
 * each time a thread releases a lock it obtained before. 

So what is semaphore? The simplest way to think of a semaphore is to consider it an abstraction 
that allows n units to be acquired, and offers acquire and release mechanisms.
 It safely allows us to ensure that only n processes can access a certain resource at a given time.
 * 
 * @author Joerg Helwig
 *
 */
public class SemaphoreExample implements Runnable {
    private static final Semaphore semaphore = new Semaphore(3, true);
    private static final AtomicInteger counter = new AtomicInteger();
    private static final long endMillis = System.currentTimeMillis() + 10000;
 
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SemaphoreExample());
        }
        executorService.shutdown();
    }
 
    public void run() {
        while(System.currentTimeMillis() < endMillis) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                System.out.println("["+Thread.currentThread().getName()+"] Interrupted in acquire().");
            }
            int counterValue = counter.incrementAndGet();
            System.out.println("["+Thread.currentThread().getName()+"] semaphore acquired: "+counterValue);
            if(counterValue > 3) {
                throw new IllegalStateException("More than three threads acquired the lock.");
            }
            counter.decrementAndGet();
            semaphore.release();
        }
    }
}
