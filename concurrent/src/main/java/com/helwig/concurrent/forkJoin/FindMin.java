package com.helwig.concurrent.forkJoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


/**
 * 
 * 
In computer science this approach is also known as divide-and-conquer approach. 
Every time a problem is too complex to solve at once, it is divided into multiple 
smaller and easier to solve problems. 

The base class of the Fork/Join Framework is java.util.concurrent.ForkJoinPool.
 This class implements the two interfaces Executor and ExecutorService and subclasses 
 the AbstractExecutorService. Hence the ForkJoinPool is basically a thread pool that takes special kinds of tasks, 
 namely the ForkJoinTask. This class implements the already known interface Future and therewith methods
  like get(), cancel() and isDone(). Beyond that this class also provides two methods that gave the whole
   framework its name: fork() and join().

While a call of fork() will start an asynchronous execution of the task, a call of join() will wait until the task has finished and retrieve its result. Hence we can split a given task into multiple smaller tasks, fork each task and finally wait for all tasks to finish. This makes the implementation of complex problems easier.
 * 
 * @author Joerg Helwig
 *
 */
public class FindMin extends RecursiveTask<Integer> {
    private static final long serialVersionUID = 1L;
    private static int counter = 0;
    private int[] numbers;
    private int startIndex;
    private int endIndex;
 
    public FindMin(int[] numbers, int startIndex, int endIndex) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
 
    @Override
    protected Integer compute() {
        int sliceLength = (endIndex - startIndex) + 1;
        if (sliceLength > 2) {
        	counter++;
        	System.out.println("startIndex "+ startIndex);
            FindMin lowerFindMin = new FindMin(numbers, startIndex, startIndex + (sliceLength / 2) - 1);
            lowerFindMin.fork();
            FindMin upperFindMin = new FindMin(numbers, startIndex + (sliceLength / 2), endIndex);
            upperFindMin.fork();
            return Math.min(lowerFindMin.join(), upperFindMin.join());
        } else {
            return Math.min(numbers[startIndex], numbers[endIndex]);
        }
    }
 
    public static void main(String[] args) {
        int[] numbers = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        System.out.println("Number of availableProcessors " + Runtime.getRuntime().availableProcessors());
        Integer min = pool.invoke(new FindMin(numbers, 0, numbers.length - 1));
        System.out.println(min);
        System.out.println( "counter "+ counter);
        System.out.println("numbers " +numbers);
    }
}

