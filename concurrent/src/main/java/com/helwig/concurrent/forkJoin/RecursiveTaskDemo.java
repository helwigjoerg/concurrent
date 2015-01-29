package com.helwig.concurrent.forkJoin;

import java.util.concurrent.ForkJoinPool;


/**
 * 
 * @author Joerg Helwig
 *
 */
public class RecursiveTaskDemo {
	
	public static void main(String[] args) {
		FibonacciCal fibonacciCal = new FibonacciCal(12);
		ForkJoinPool pool = new ForkJoinPool();
		int i = pool.invoke(fibonacciCal);
		System.out.println(i);
	}

}
