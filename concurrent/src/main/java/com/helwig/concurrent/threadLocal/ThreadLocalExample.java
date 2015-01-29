package com.helwig.concurrent.threadLocal;

/**
 * This example creates a single MyRunnable instance which is passed 
to two different threads. Both threads execute the run() method, and thus 
sets different values on the ThreadLocal instance. If the access to the set() 
call had been synchronized, and it had not been a ThreadLocal object, the second 
thread would have overridden the value set by the first thread.

However, since it is a ThreadLocal object then the
two threads cannot see each other's values. Thus, they set and get different values.

* @author Joerg Helwig
*/

public class ThreadLocalExample {
	
	
	
	public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal =
               new ThreadLocal<Integer>();

        public void run() {
        	
        	System.out.println(" before threadLocal "+ threadLocal.get());
        	
        	
        	
            threadLocal.set( (int) (Math.random() * 100D) );
    
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
    
            System.out.println("threadLocal "+ threadLocal.get());
        }
    }
	
	
	public static class MyRunnable2 implements Runnable {

        Integer sharedVariable ;

        public void run() {
        	
        	System.out.println(" before sharedVariable "+ sharedVariable);
        	
            sharedVariable= (int) (Math.random() * 100D) ;
    
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
    
            System.out.println(" end sharedVariable " +sharedVariable);
        }
               
       
    }


    public static void main(String[] args) {
        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();
        
        MyRunnable2 sharedRunnableInstance2 = new MyRunnable2();
        
        Thread thread3 = new Thread(sharedRunnableInstance2);
        Thread thread4 = new Thread(sharedRunnableInstance2);

        thread3.start();
        thread4.start();
    }

}
