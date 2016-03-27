
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;



public class balancer extends Thread{
	
	public Object synchObj = new Object();  	
	public bg bg1;
	public model m;
	
    public balancer(bg bg)
    	{
    		this.bg1 = bg;
    		m = bg.m;
       	}
    
    
    synchronized public void run(){
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    	
    	int count = 0;

    	while(true){
    		
    		System.out.println("Balancer method. Test.");
        
    		count++;
    		int ele;
    		node start= new node();
    		node goal= new node();
    	
    		Random ran= new Random();
    		int index= ran.nextInt(400);
    		int x=index/20;
    		int y=index%20;
        
    		x = ran.nextInt(6);
    		y = ran.nextInt(6);
        
    		if(m.map[x][y]==1){
    			start.x=x;
    			start.y=y;
    			ele = bg1.selectEle(start);
        
    			if(ele == 0){
    				ele = 1;
            
            
    				int p=bg1.storageStrategy(ele);
            
    				if(p == 5000){
    					break;
    				}
        
    				goal.x=p/20;
    				goal.y=p%20;
            
            if(m.map[goal.x][goal.y] == 1){
            	break;
            }
            		
            System.out.println("Thread balancer working. Goal x = " + goal.x + " Goal y = " + goal.y);

            m.map[goal.x][goal.y]=2;


            //dstar my=new dstar(m, bg1.v, start,goal,2,ele, null);
            //my.start();
            //bg1.balanced++;
            }
        	
        	}//end of if statement. that is if car found on spot
    		
    		System.out.println("Count = " + count + ". Balancer working");
    		
    		if( (count % 20) == 0){
    			System.out.println("Entered this.yield part");
    			//this.yield();
    			
    			
    			 synchronized (synchObj) {  
    			        try { synchObj.wait(); } catch (InterruptedException ie) {}  
    			      }
    			 
    			
    			//synchronized(synchObj){
    			/*
    			try {
					this.sleep(1000);
				} 
    			catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			//}//synchronized
    			 */
    			
    		}//if(count % 20 = 0)
    		
    	}
    }//synchronized run
    
    public void myNotify() {  
        System.out.println("Completed search");  
          synchronized (synchObj) {  
            synchObj.notify();  
          }  
      }
      
}