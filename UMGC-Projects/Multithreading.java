/*
NAME: Dmitry Landy
DATE: August 7, 2020
FILE: Multithreading.java
DESCRIPTION: This program will create 3 separate threads and time them to run
in consecutive order each iteration (1, 2, then 3). 
 */
package CMSC412;

import java.util.concurrent.*;

class MyThread implements Runnable{
    
    Semaphore sem = new Semaphore(1);
    String name;
    Thread currentThread;    
        
    //constructor
    public MyThread (String threadName){
    name = threadName;
    currentThread = new Thread(this, name);    
    currentThread.start();//calls run()    
    }
    
    //This is called when a thread is started
    public void run() {         
        try {
            //iterate through 5 times
            for (int counter= 1; counter <= 5; counter++) 
            {                
                if(name.equals("Thread 1"))
                {
                    sem.acquire();
                    System.out.println(name + "- iteration no." + counter + "\n");                    
                    sem.release();    
                    Thread.sleep(1000);
                }//end if(name.equals("Thread 1"))
                
                if(name.equals("Thread 2"))
                {
                    Thread.sleep(100);
                    sem.acquire();
                    System.out.println(name + "- iteration no." + counter + "\n");                    
                    sem.release();
                    Thread.sleep(1000);
                    
                }//end if(name.equals("Thread 2"))
                
                if(name.equals("Thread 3"))
                {                    
                    Thread.sleep(200);
                    sem.acquire();
                    System.out.println(name + "- iteration no." + counter + "\n");                    
                    sem.release();  
                    Thread.sleep(1000);
                }//end if(name.equals("Thread 3"))
                                            
            }//end  for loop           
            
        } catch (InterruptedException e) {
            System.out.println(name + "Interrupted");
        }//end catch        
    }//end run()   
 
    //main method creates 3 threads
    public static void main(String args[]) {
        MyThread thread1 = new MyThread("Thread 1");
        MyThread thread2 = new MyThread("Thread 2");
        MyThread thread3 = new MyThread("Thread 3");             
    }//end Main Method  

}//end Multithreading

