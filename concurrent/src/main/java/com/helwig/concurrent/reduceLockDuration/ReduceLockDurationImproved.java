package com.helwig.concurrent.reduceLockDuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
The scope of the lock is reduced. (synchronize only what is nesecarry)
here access to map
 * 
 * @author Joerg Heliwg
 *
 */
public class ReduceLockDurationImproved implements Runnable {
    private static final int NUMBER_OF_THREADS = 5;
    private static final Map<String, Integer> map = new HashMap<String, Integer>();
 
    public void run() {
        for (int i = 0; i < 10000; i++) {
            
                UUID randomUUID = UUID.randomUUID();
                Integer value = Integer.valueOf(42);
                String key = randomUUID.toString();
                
            synchronized (map) {
                map.put(key, value);
            }
            Thread.yield();
        }
    }
 
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new Thread(new ReduceLockDurationImproved());
        }
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].join();
        }
        System.out.println((System.currentTimeMillis()-startMillis)+"ms");
    }
}


