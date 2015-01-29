package com.helwig.concurrent.executorService;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * @author Joerg Helwig
 *
 */
public class FindMinTask implements Callable<Integer> {
    private int[] numbers;
    private int startIndex;
    private int endIndex;
    private ExecutorService executorService;
    private static int counter = 0;
 
    public FindMinTask(ExecutorService executorService, int[] numbers, int startIndex, int endIndex) {
        this.executorService = executorService;
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
 
    public Integer call() throws Exception {
        int sliceLength = (endIndex - startIndex) + 1;
        if (sliceLength > 2) {
        	counter++;
            FindMinTask lowerFindMin = new FindMinTask(executorService, numbers, startIndex, startIndex + (sliceLength / 2) - 1);
            Future<Integer> futureLowerFindMin = executorService.submit(lowerFindMin);
            FindMinTask upperFindMin = new FindMinTask(executorService, numbers, startIndex + (sliceLength / 2), endIndex);
            Future<Integer> futureUpperFindMin = executorService.submit(upperFindMin);
            return Math.min(futureLowerFindMin.get(), futureUpperFindMin.get());
        } else {
            return Math.min(numbers[startIndex], numbers[endIndex]);
        }
    }
     
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] numbers = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
//        ExecutorService executorService = Executors.newFixedThreadPool(63);
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
        /*
         * this will result in an deadlock
         * 
         * The newCachedThreadPool factory is a good default choice for an Executor, 
         * providing better queuing performance than a fixed thread pool. A fixed size thread pool
         *  is a good choice when you need to limit the number of concurrent tasks for resource-management purposes. 
         *  Bounding either the thread pool or the work queue is suitable only when tasks are independent. 
         *  With tasks that depend on other tasks, bounded thread pools or queues can cause thread starvation, 
         *  deadlock; instead, use an unbounded pool configuration like newCachedThreadPool.
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> futureResult = executorService.submit(new FindMinTask(executorService, numbers, 0, numbers.length-1));
        System.out.println(futureResult.get());
        executorService.shutdown();
        System.out.println( "counter "+ counter);
    }
}