package com.helwig.concurrent.executorService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;


/**
 * TimingThreadPool that overrides afterExecute, beforeExecute and terminated
 * 
 *@author Joerg Helwig
 *
 */
public class TimingThreadPool  extends ThreadPoolExecutor{
	
	private Logger log = Logger.getLogger("TimingThreadPool");
	private final ThreadLocal<Long>  startTime= new ThreadLocal<Long>();
	private final AtomicLong numberOfTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();
	

	public TimingThreadPool(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			
			long endTime = System.nanoTime();
			long taskTime = endTime-startTime.get();
			numberOfTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.fine(String.format( "Thread %s end  %s,time=%dns", t, r,taskTime) );
			System.out.println(String.format( "Thread %s end  %s,time=%dns", t, r,taskTime) );
			
		}finally {
			super.afterExecute(r, t);
		}
		
	}


	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println(String.format( "Thread %s start  %s", t, r));
		log.fine(String.format( "Thread %s start  %s", t.getName(), r) );
		startTime.set(System.nanoTime());
	}


	@Override
	protected void terminated() {
try {
			
			long endTime = System.nanoTime();
			long taskTime = endTime-startTime.get();
			numberOfTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			System.out.println(String.format( "Terminated avg time=%dns",totalTime.get()/numberOfTasks.get()) );
			log.fine(String.format( "Terminated avg time=%dns",totalTime.get()/numberOfTasks.get()) );
			
		}finally {
			super.terminated();
		}
		
	
	}
	
	
	

}
