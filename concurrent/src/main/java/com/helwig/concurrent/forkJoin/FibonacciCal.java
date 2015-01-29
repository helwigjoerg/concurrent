package com.helwig.concurrent.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * 
 * @author Joerg Helwig
 *
 */
public class FibonacciCal extends RecursiveTask<Integer>{
	private static final long serialVersionUID = 1L;
	final int num;
	FibonacciCal(int num) { this.num = num; }
	@Override
	protected Integer compute() {
	     if (num <= 1){
	        return num;
	     }
	     FibonacciCal fcal1 = new FibonacciCal(num - 1);
	     fcal1.fork();
	     FibonacciCal fcal2 = new FibonacciCal(num - 2);
	     return fcal2.compute() + fcal1.join();
	}

}
