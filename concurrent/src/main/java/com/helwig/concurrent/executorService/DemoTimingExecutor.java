package com.helwig.concurrent.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;


public class DemoTimingExecutor {
	
	
	/**
	 * 
	 *@author Joerg Helwig
	 *
	 */
	
	public static void main(String[] args) {
		
		 Integer threadCounter = 0;
		 BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(50);
		 
	        TimingThreadPool executor = new TimingThreadPool(10,
	                                            20, 5000, TimeUnit.MILLISECONDS, blockingQueue);
	        
	        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
	            public void rejectedExecution(Runnable r,
	                    ThreadPoolExecutor executor) {
	                System.out.println("DemoTask Rejected : "
	                        + ((DemoThread) r).getName());
//	                System.out.println("Waiting for a second !!");
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                System.out.println("Lets add another time : "
	                        + ((DemoThread) r).getName());
	                executor.execute(r);
	            }
	        });
	        // Let start all core threads initially
	        executor.prestartAllCoreThreads();
	        while (true) {
	            threadCounter++;
	            // Adding threads one by one
//	            System.out.println("Adding DemoTask : " + threadCounter);
	            executor.execute(new DemoThread(threadCounter.toString()));
	 
	            if (threadCounter == 100)
	                break;
	        }
	        
	        executor.shutdown();
	    }
		
	}


