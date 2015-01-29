package com.helwig.concurrent.executorService;

/**
 * a simple Thread
 * 
 *@author Joerg Helwig
 *
 */
public class DemoThread implements Runnable {

	 private String name = null;
	 
	    public DemoThread(String name) {
	        this.name = name;
	    }
	 
	    public String getName() {
	        return this.name;
	    }
	 
	    public void run() {
	        try {
	            Thread.sleep(500);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
//	        System.out.println("Executing : " + name);
	    }

}
