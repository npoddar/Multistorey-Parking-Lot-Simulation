import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class bg extends Thread{
	 
	//private Object synchObj = new Object();  
    public model m;
    public view v;
    
    private int carsMoved = 0;
    
    private Queue<Integer> one = new LinkedList<Integer>(); 
    private Queue<Integer> two = new LinkedList<Integer>();
    private Queue<Integer> three = new LinkedList<Integer>(); 
    private Queue<Integer> four = new LinkedList<Integer>(); 
    private Queue<Integer> five = new LinkedList<Integer>(); 
    private Queue<Integer> six = new LinkedList<Integer>(); 
    private Queue<Integer> seven = new LinkedList<Integer>(); 
    private Queue<Integer> eight = new LinkedList<Integer>(); 
    private Queue<Integer> eleAll = new LinkedList<Integer>();
    public FileWriter fstream = null;
    public BufferedWriter out = null;
    private int ele2 = 0;
    private boolean balanceBool = false;
    public int balanced = 0;
    public balancer b1;
    public int[][] adjacent = { {1, 4, 5} ,
    							{0, 2, 4, 5, 6},
    							{3, 1, 5, 6, 7},
    							{2, 6, 7},
    							{0, 1, 5},
    							{0, 1, 4, 2, 6},
    							{7, 5, 2, 3},
    							{6, 2, 3} };
    
    private static int carsInQueue = 0;

    public bg(model mo,view vi){
        m=mo;
        v=vi;
        try {
			fstream = new FileWriter("out.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        out = new BufferedWriter(fstream);
        
       
    }

    public void run(){
            period1();
            FileWriter fstream = null;
			

    }

    synchronized public void period1(){
        System.out.println("P1....");
        
        b1 = new balancer(this);
        b1.start();
        int i = 0;
        int run = 0;
       while(m.num<=m.max){
         // while( (m.num<=m.max) || (run < 650) ){

        	run++;
            Random ran= new Random();
            int num= ran.nextInt(100);
            i++;
            
            //synchronized (b1.synchObj) {  
            	//System.out.println("Entered notify bg");
                //(b1.synchObj).notify();
            
            
            //b1.myNotify();
            
            //Object str = b1.getState();
            
           // Thread.State b1;
            
            
            //System.out.println("Value of blocked :" + valueOf("Blocked"));
            
            if( (b1.getState().valueOf("WAITING") == b1.getState()) || (b1.getState().valueOf("BLOCKED") == b1.getState()) ){
            	System.out.println("If statement. Thread State : " + b1.getState());
            	System.out.println("Entered blocked/waiting method");
            	//b1.notify();
            	//b1.myNotify();
            	
            	synchronized (b1.synchObj) {  
            		System.out.println("Entered notify bg");
            		b1.synchObj.notify();
            		//b1.synchObj.notifyAll();
            		//b1.notify();
            	}
            }//if statement
            
            //System.out.println("Thread State : " + b1.getState().valueOf("BLOCKED"));
            
            
            //b1.notify();
              //}
          
            
            /*
            if(i == 40){
            	if(balanceBool == false){
            		balance();
            	   	balanceBool = true;
            	}	
            }
            */
            
            //if(num<=89) {
            if(num<=95) {
            	simComing();
            	
            	
          	
                	
            }
            else if(num>89&&num<=94) simGoing();
            else continue;
            
            
            
            
            //b1.notify();
            /*
            try {
    			this.sleep(10);
    		} catch (InterruptedException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		/*
            try {
				//b1.wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            */
            if( (balanced < 20) || (false) ) {
            	///balance();///////////////////////////////////////
            }
            
            
            try{
                Thread.sleep(m.getSpeed());
            }
            catch(Exception e){}
        }
       
        
        
    	
        
       if(m.pause==true)
            return;
        period2();
    }
    synchronized public void period2(){
        System.out.println("P2....");
        int i=0;
     
        
        while(i<=9){
            Random ran= new Random();
            int num= ran.nextInt(100);
            if(num<=4){
                simComing();
                i+=1;
            }
            else if(num>4&&num<=9){
                simGoing();
                i+=1;
            }
            else{
                continue;
            }
            try{
                Thread.sleep(m.getSpeed());
            }
            catch(Exception e){}
        }
        if(m.pause==true)
            return;
        period3();
    }
    synchronized public void period3(){
        System.out.println("P3....");
        
        /*
        if(balanceBool == false){
    		balance();
    	}
    	*/
    	int count = 0;
    	boolean boolBalance = true;
        while(m.num>=376*0.1){
            Random ran= new Random();
            int num= ran.nextInt(100);
            count++;
            if(num<=4)    
            {//simComing();
            	
            }
            else if(num>4&&num<=94) {
            	simGoing();
            }
            else continue;
            
            if( (m.num < 376*0.5)  && (boolBalance)){
            	System.out.println("Entered case m.num < 376*0.5 \n");
            	
            	//balanceThread();
            	
            	boolBalance = false;
            }
            
            try{
                Thread.sleep(m.getSpeed());
            }
            catch(Exception e){}
        }
        if(m.pause==true)
            return;
        period4();
    }

    synchronized public void balanceThread(){
    	
    	while(balanced < 5){
    	
    		balance();
    	}
    	
    }
    
	synchronized public void period4(){
        System.out.println("P4....");
        int i=0;
        while(i<=4){
            Random ran= new Random();
            int num= ran.nextInt(100);
            if(num<=1){
                //simComing();
                i+=1;
            }
            else if(num>1&&num<=2){
                simGoing();
                i+=1;
            }
            else{
                continue;
            }
            try{
                Thread.sleep(m.getSpeed());
            }
            catch(Exception e){}
        }
        
        //b1.stop();
        
        if(m.pause==true)
            return;
        summery();
    }
    
    synchronized public void enqueue(){
    	//put a car into an elevator queue based size//
    	
    	    	
    	//find the smallest queue //
    	long length = 999999999;
    	int smallest = 0;
    	
    	boolean ele[] = new boolean[8];
    	
    	for(int i = 0; i<8; i++){
    		ele[i] = true;
    	}
    	
    	for(int elevator = 0; elevator < 8; elevator++){
    		int numOfcars=0;
    		for(int i=0;i<20;i++){
    			for(int j=0;j<20;j++){
    				if(m.zone[i][j]==elevator&&m.getMap(i, j)==0){
    					numOfcars++;
                	}
            	}
        	}
    		if( !(numOfcars>(int)(47*(1-0.9))) ){
                ele[elevator] = false;
                System.out.println("Zone " + elevator + " is full");
            }
    		
        
    	}
    	int oneS = one.size();
    	int twoS = two.size();
    	int threeS = three.size();
    	int fourS = four.size();
    	int fiveS = five.size();
    	int sixS = six.size();
    	int sevenS = seven.size();
    	int eightS = eight. size();
    	////////////////////////////////////////
    	
    	
    	
    	//////////////////////////////////////////
    	if(one.size() < length){
    		length = one.size();
    		if(ele[0] == true){
    			smallest = 1;
    		}
    	}
    	if(two.size() < length){
    		length = two.size();
    		if(ele[1] == true){
    			smallest = 2;
    		}
    	}
    	if(three.size() < length){
    		length = three.size();
    		if(ele[2] == true){
    			smallest = 3;
    		}
    	}
    	if(four.size() < length){
    		length = four.size();
    		if(ele[3] == true){
    			smallest = 4;
    		}
    		//smallest = 4;
    	}
    	if(five.size() < length){
    		length = five.size();
    		if(ele[4] == true){
    			smallest = 5;
    		}    		
    		//smallest = 5;
    	}
    	if(six.size() < length){
    		length = six.size();
    		if(ele[5] == true){
    			smallest = 6;
    		}
    		//smallest = 6;
    	}
    	if(seven.size() < length){
    		length = seven.size();
    		if(ele[6] == true){
    			smallest = 7;
    		}    		
    		//smallest = 7;
    	}
    	if(eight.size() < length){
    		length = eight.size();
    		if(ele[7] == true){
    			smallest = 8;
    		}
    		//smallest = 8;
    	}
    	
    	System.out.println("Smallest = " + smallest);
    	
    	
    	//Put the car into the smallest queue//
    	switch(smallest){
    	
    	case 1:
    		one.add(1);
    		eleAll.add(1);
    		System.out.println("Car added to elevator 0");
    		break;
    		
    	case 2:
    		two.add(2);
    		eleAll.add(2);
    		System.out.println("Car added to elevator 1");
    		break;
    		
    	case 3:
    		three.add(3);
    		eleAll.add(3);
    		System.out.println("Car added to elevator 2");
    		break;
    	
    	case 4:
    		four.add(4);
    		eleAll.add(4);
    		System.out.println("Car added to elevator 3");
    		break;
    		
    	case 5: 
    		five.add(5);
    		eleAll.add(5);
    		System.out.println("Car added to elevator 4");
    		break;
    		
    	case 6:
    		six.add(6);
    		eleAll.add(6);
    		System.out.println("Car added to elevator 5");
    		break;
    		
    	case 7:
    		seven.add(7);
    		eleAll.add(7);
    		System.out.println("Car added to elevator 6");
    		break;
    		
    	case 8:
    		eight.add(8);
    		eleAll.add(8);
    		System.out.println("Car added to elevator 7");
    		break;
    	}
    	   	
    	
    	System.out.println(one.size() + " " + two.size() + " " + three.size() + " " + four.size() + " " + five.size() + " " + six.size() + " " + seven.size() + " " + eight.size());
    	
    }
    
    synchronized public Queue<Integer> dequeue(){
    	
    	//dequeue a car from the longest queue first//
    	
    	//order the elevator queues with longest queue first//
  
    	//return an array of elevators with longest queue first//
   
    	//sizes of elevators//
    	int oneS = one.size();
    	int twoS = two.size();
    	int threeS = three.size();
    	int fourS = four.size();
    	int fiveS = five.size();
    	int sixS = six.size();
    	int sevenS = seven.size();
    	int eightS = eight.size();
    	
    	//will contain the number of cars queued in elevators 1 through 8
    	ArrayList<Integer> elevator = new ArrayList<Integer>();
    	
    	//Queue containing sorting of elevators, longest first.
    	Queue<Integer> elevatorSortName = new LinkedList<Integer>();
    	
    	elevator.add(oneS);
    	elevator.add(twoS);
    	elevator.add(threeS);
    	elevator.add(fourS);
    	elevator.add(fiveS);
    	elevator.add(sixS);
    	elevator.add(sevenS);
    	elevator.add(eightS);
    	    	
    	int[] sort1 = {oneS, twoS, threeS, fourS, fiveS, sixS, sevenS, eightS};
    	//int[] unsorted = {oneS, twoS, threeS, fourS, fiveS, sixS, sevenS, eightS};
    	
    	//sorted elevator list//
    	Arrays.sort(sort1);
    	
    	
    	for(int i = 7; i > -1 ; i--){
    		int sortX = sort1[i]; 	//sortX is the highest elevator value//
    		for(int j = 0; j < 8; j++){
    			if(elevator.get(j) == sortX){
    				elevator.set(j, -100);
    				elevatorSortName.add(j);
    				break;
    			}
    			
    		}
    	}
    	
    	int firstCarEle = eleAll.remove();
    	
    	
    	
    	//return firstCarEle;
    return elevatorSortName;	
    	
    }
    
    
    
    synchronized public void simComing() throws NullPointerException {
        int ele = 900;
        node start= new node();
        node goal= new node();
        
        /*
        b1.notify();
        try {
			this.sleep(10);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        b1.yield();
        */
        
        //b1.interrupt();
        
        /*
        b1.run();
        try {
			b1.join(0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
			this.sleep(1);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        b1.yield();
        */
        System.out.println("State of balancer thread " + b1.getState());
        
        
        if(m.pause==true)
            return;
      
        while(!(m.num<=m.max)){
        	return;
        }
        
        int carsInside = m.num;
        
        int test = 0;
        
        /*
        while ((carsInQueue + carsInside) < 400){
        		System.out.println("Entered enqueue case in Simcoming");
        		enqueue();
        		carsInQueue++;
        	}
        */
        	//else
        		//break;
        //}
        
      //put a car in queue//
       // enqueue();
        
        
        //Queue<Integer> eleSort = dequeue();
        //for(Integer i : eleSort){
    	//	System.out.print(i.toString() + " ");
       	//}
    	//System.out.print("\n");
        
        boolean elevatorFound = false;
        
    	
        while( (test < 10) || (true) ){
        	
        	test++;
        
        	/*
        	while ((carsInQueue + carsInside) < 400){
        		System.out.println("Entered enqueue case in Simcoming");
        		enqueue();
        		carsInQueue++;
        	}
        	*/
        	
        	/*
        	while ((carsInQueue + carsInside) < 400){
        		enqueue();
        		carsInQueue++;
        	}
        	*/
        	
        	//Random ran= new Random();
            //int eleNum=ran.nextInt(8);
        

    	//int eleNum = eleAll.peek();
    	
    	 	
    	//System.out.println("Elevator = " + eleNum + " exception?");
    	
        	/*
        	
    	if(eleAll.peek() == null){
    		while(true){
    			System.out.println("eleNum = " + eleNum);
    			;
    		}
    	}
    	*/
    	//System.out.println("Print value of eleAll top :" + eleNum);
    	//eleNum = eleNum - 1;
    	
    	/////////////////////////////////////////
    	
    	/*int numOfcars1=0;
    	for(int elevator = 0; elevator < 8; elevator++){
    		for(int i=0;i<20;i++){
    			for(int j=0;j<20;j++){
    				if(m.zone[i][j]==eleNum&&m.getMap(i, j)==0){
    					numOfcars1++;
    				}
    			}
    		}
    	}
    	*/
    	
    	/////////////////////////////////
        int eleNum;
        	
    	while(true){
    		Random ran= new Random();
    		eleNum=ran.nextInt(8);
    		
    		if( (eleNum == 0) || (eleNum == 7) ) {
    			break;
    		}
    		else{
    			Random ran1 = new Random();
    			double prob = ran1.nextDouble();
    			//System.out.println("Prob " + prob);
    			if(prob < 0.2){
    				break;
    			}
       		}
      	}
        /////////////////////////////////
    	
    	int numOfcars=0;
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
            	if(m.zone[i][j]==eleNum&&m.getMap(i, j)==0){
                    numOfcars++;
                }
            }
        }
        
        if( !(numOfcars>(int)(47*(1-0.9))) ){
            //eleAll.remove();
        	continue;
        }

    	
    	/////////////////////////////////////////
    	
    	    	
    	if(m.eleStatus[eleNum]==1){
            continue;
        }
    	else{
    		elevatorFound = true;
    		//eleAll.remove();
    		
    		/*
    		switch(eleNum + 1){
            
        	case 1:
        		one.remove();
        		break;
        		
        	case 2:
        		two.remove();
        		break;
        		
        	case 3:
        		three.remove();
        		break;
        	
        	case 4:
        		four.remove();
        		break;
        		
        	case 5: 
        		five.remove();
        		break;
        		
        	case 6:
        		six.remove();
        		break;
        		
        	case 7:
        		seven.remove();
        		break;
        		
        	case 8:
        		eight.remove();
        		break;
               	
        	}
    		*/
        	
        	
        	System.out.println("Elevelator number :" + (eleNum+1));
        	    		
    		m.setEleStatus(eleNum,1);               //occupy the elevator
    		try{
    			Thread.sleep(200);    //simulation of elevator moving
    		}
    		catch(Exception e){}
    		ele=eleNum;
    		break;
        }
       }//end of while loop
        
        if(elevatorFound == false){
        	return;
        }
    	  /*	
        while(true){
        	
        	//remove a car from queue//
        	int eleNum;
        	try{
        		eleNum = eleSort.remove();
        	}
        	catch(NoSuchElementException e){
        		eleSort = dequeue();
        		eleNum = eleSort.remove();
        	}
        	//Random ran= new Random();
            //int eleNum=ran.nextInt(8);
            
        	if(m.eleStatus[eleNum]==1){
                continue;
            }
            else{
            	
            	 for(Integer i : eleSort){
             		System.out.print(i.toString() + " ");
                }
             	System.out.print("\n");
            	
             	/*
            	switch(eleNum){
        
            	case 1:
            		one.remove();
            		break;
            		
            	case 2:
            		two.remove();
            		break;
            		
            	case 3:
            		three.remove();
            		break;
            	
            	case 4:
            		four.remove();
            		break;
            		
            	case 5: 
            		five.remove();
            		break;
            		
            	case 6:
            		six.remove();
            		break;
            		
            	case 7:
            		seven.remove();
            		break;
            		
            	case 8:
            		eight.remove();
            		break;
                   	
            	}
            	
            	
                m.setEleStatus(eleNum,1);               //occupy the elevator
                try{
                    Thread.sleep(200);    //simulation of elevator moving
                }
                catch(Exception e){}
                ele=eleNum;
                break;
            }
        }
        */
        
        //int ele;
        
        int numOfb=0;
        for(int i=0;i<20;i++)
            for(int j=0;j<20;j++){
            	if(test == 100){
                	//return;
                	}
            	if(m.zone[i][j]==ele&&m.getMap(i, j)==0){
                    numOfb++;
                }
            }
        
        if(numOfb>(int)(47*(1-0.9))){
            m.num++;
        }
        else
        {
        	m.setEleStatus(ele,0);
            return;
        }
        
        //ele  = 0;	////////////////////////////////line modified///////////////////////////////
        
        switch(ele){
            case 0:
                start.x=4;
                start.y=3;
                break;
            case 1:
                start.x=4;
                start.y=6;
                break;
            case 2:
                start.x=4;
                start.y=13;
                break;
            case 3:
                start.x=4;
                start.y=16;
                break;
            case 4:
                start.x=15;
                start.y=3;
                break;
            case 5:
                start.x=15;
                start.y=6;
                break;
            case 6:
                start.x=15;
                start.y=13;
                break;
            case 7:
                start.x=15;
                start.y=16;
                break;

        }

        //int p=storageStrategy(ele);
        int p=storageStrategy(ele);
        
        goal.x=p/20;
        goal.y=p%20;
        
        if( (m.getMap(goal.x, goal.y)) != 0){
        	  return;      	
        }

        m.setMap(goal.x, goal.y, 2);

        //System.out.println("storageX="+goal.x+"  storageY="+goal.y);
        /*while(true){
            Random ran= new Random();
            int position=ran.nextInt(400);
            if(m.map[position/20][position%20]==1||m.map[position/20][position%20]==2||m.map[position/20][position%20]==10)
                continue;
            else if(position==83||position==93||position==106||position==116||position==283||position==293||position==306||position==316)
                continue;
            else{
                goal.x=position/20;
                goal.y=position%20;
                m.map[position/20][position%20]=2;
                break;
            }
        }*/

        if( (start.x == 4) && (start.y == 7) ){
        	//System.out.println("Start cell (4,7) belongs to zone :" + ele);
        	//m.pause = true;
        	
        	try
        	{
        	    String filename= "4-7.txt";
        	    FileWriter fw = new FileWriter(filename); //the true will append the new data
        	
        	    fw.write("Start cell (4,7) belongs to zone :" + ele);
        	}
        	catch(IOException ioe)
        	{
        	    System.err.println("IOException: " + ioe.getMessage());
        	}
        	
        }
        
        if( (start.x == 4) && (start.y == 7) ){
        	//System.out.println("Start cell (4,7) belongs to zone :" + ele);
        	//m.pause = true;
        	
        	try
        	{
        	    String filename= "4-7.txt";
        	    FileWriter fw = new FileWriter(filename); //the true will append the new data
        	
        	    fw.write("Start cell (4,7) belongs to zone :" + ele);
        	}
        	catch(IOException ioe)
        	{
        	    System.err.println("IOException: " + ioe.getMessage());
        	}
        	
        }
        dstar my=new dstar(m,v,start,goal,0,ele, out);
        
        my.start();
    }
    
    public void autoBalance(){
    	
    	
    	
    }
    
    public void balance(){
    	
    	int[] random = new int[4000];
    	
    	for(int i = 0; i < 4000; i++){
    		random[i] = 0;
    	}
    	
    	int count = 0;
    	
    	while( (true) && (count < 1) ) {
    		
    	count++;
    		
    	System.out.println("Balancer working");
        int ele = 0;
        node start= new node();
        node goal= new node();
    	
        
        
        Random ran= new Random();
    	int index= ran.nextInt(400);
    	
    	/*
    	if(random[index] != 0){
    		break;
    	}
    	*/
    	
    	 index = retrievalStrategy(ele);
    	 
         
         if(index == 5000){
        	 System.out.println("////// Car picked from zone 0. But list is empty ////////");
        	 break;
         }
         
        int x=index/20;
        int y=index%20;
        
        //x = 0;
        //y = 0;
        
       //x = 0;
       //y = 0;
        
        if(m.getMap(x, y) ==1){
        	System.out.println("!!!!!!!! Picking car from " + x + "," + y);
        	
            start.x=x;
            start.y=y;
            ele = selectEle(start);
        
            if(ele == 0){
            	ele = 1;
        
            	int numOfb1=0;
                for(int i=0;i<20;i++)
                    for(int j=0;j<20;j++){
                    	if(m.zone[i][j]==ele&&m.getMap(i, j)==0){
                            numOfb1++;
                        }
                    }
                
                        
                if(numOfb1>(int)(47*(1-0.8))){
                    //m.num++;
                }
                else{
                	System.out.println("Not going to zone 0 from zone 1 " + " as cars = " + numOfb1);
                	return;
                }
            
            int p=storageStrategy(ele);
            
            if(p == 5000){
            	break;
            }
            
            if(m.getMap(x, y) == 5){
            	System.out.println("Entered blue cell");
            	break;
            }
        
            goal.x=p/20;
            goal.y=p%20;
            
      	  //System.out.println("Goal cells status :" + m.map[goal.x][goal.y]);
           
            //m.setMap(start.x, start.y, 0);
            
            if(m.getMap(goal.x, goal.y) != 0){
            	break;
            }
            
            ///////////////////////////////
            
            
            int numOfb=0;
            for(int i=0;i<20;i++)
                for(int j=0;j<20;j++){
                	if(m.zone[i][j]==ele&&m.getMap(i, j)==0){
                        numOfb++;
                    }
                }
            
                    
            if(numOfb>(int)(47*(1-0.8))){
                //m.num++;
            }
            else{
            	System.out.println("Not going to zone 0 from zone 1 " + " as cars = " + numOfb);
            	return;
            }
            
            
            ///////////////////////////////
            
      	  	System.out.println("Goal cells status :" + m.getMap(goal.x, goal.y));

            		
            System.out.println("???????Start x = " + start.x + " Start y = " + start.y + " Goal x = " + goal.x + " Goal y = " + goal.y);

            //System.out.println("Value of goal " + m.map[goal.x][goal.y]);
            m.setMap(goal.x, goal.y, 2);
            m.setMap(start.x, start.y, 5);
            dstar my=new dstar(m,v,start,goal,2,ele, out);
            random[index] = 1;
            carsMoved++;
            /*
            try{
                Thread.sleep(m.getSpeed());
                continue;
            }
            catch(Exception ex){}
            */
            my.start();
            balanced++;
            
            }
        	
        	}//end of if statement. that is if car found on spot
    		
    	}
    }

    synchronized public int storageStrategy(int ele){

        int position=0, x=0, y=0;
        ArrayList list= new ArrayList();

        switch(ele){
            case 0:
                x=4;
                y=3;
                break;
            case 1:
                x=4;
                y=6;
                break;
            case 2:
                x=4;
                y=13;
                break;
            case 3:
                x=4;
                y=16;
                break;
            case 4:
                x=15;
                y=3;
                break;
            case 5:
                x=15;
                y=6;
                break;
            case 6:
                x=15;
                y=13;
                break;
            case 7:
                x=15;
                y=16;
                break;
        }

        for(int pri=2;pri>=0;pri--){
            for(int i=0;i<20;i++)
                for(int j=0;j<20;j++){
                    if(m.pri[i][j]==pri&& m.getMap(i, j)==0&&m.zone[i][j]==ele){
                        list.add(i*20+j);
                    }
                }
            if(!list.isEmpty()){
                break;
            }
        }
        
        if(list.isEmpty()){
        	return 5000;
        }
        
        int max=0, index=0;
        for(int i=0;i<list.size();i++){
            int po=(Integer)list.get(i);
            int tempx= po/20;
            int tempy= po%20;
            int d=Math.abs(x-tempx)+Math.abs(y-tempy);

            if(d>max){
                max=d;
                index=i;
            }            
        }


        return position=(Integer)list.get(index);
    }

    
    synchronized public int retrievalStrategy(int ele){

        int position=0, x=0, y=0;
        ArrayList list= new ArrayList();

        switch(ele){
            case 0:
                x=4;
                y=3;
                break;
            case 1:
                x=4;
                y=6;
                break;
            case 2:
                x=4;
                y=13;
                break;
            case 3:
                x=4;
                y=16;
                break;
            case 4:
                x=15;
                y=3;
                break;
            case 5:
                x=15;
                y=6;
                break;
            case 6:
                x=15;
                y=13;
                break;
            case 7:
                x=15;
                y=16;
                break;
        }

        for(int pri=0;pri<=2;pri++){
            for(int i=0;i<20;i++)
                for(int j=0;j<20;j++){
                    if(m.pri[i][j]==pri&& m.getMap(i, j)==1&&m.zone[i][j]==ele){
                        list.add(i*20+j);
                    }
                }
            if(!list.isEmpty()){
                break;
            }
        }
        
        if(list.isEmpty()){
        	return 5000;
        }
        
        int min=999999999, index=0;
        for(int i=0;i<list.size();i++){
            int po=(Integer)list.get(i);
            int tempx= po/20;
            int tempy= po%20;
            int d=Math.abs(x-tempx)+Math.abs(y-tempy);

            if(d<min){
                min=d;
                index=i;
            }            
        }

        return position=(Integer)list.get(index);
    }

    
    
    synchronized public void simGoing(){
        node start=new node();
        node goal= new node();
        int ele;
        if(m.num==0||m.carPositions.isEmpty())
            return;
        
        if(m.pause==true)
            return;
        
        int oldEle = 0; //retrieve only if its in zone 2, and going to zone 3//
        
        Random ran= new Random();
        while(true){
            int index= ran.nextInt(400);
            int x=index/20;
            int y=index%20;
            if(m.getMap(x, y)==1){
                start.x=x;
                start.y=y;
                ele = selectEle(start);
                
                if( (x == 4) && (y == 7) ){
                	//System.out.println("Start cell (4,7) belongs to zone :" + ele);
                	//m.pause = true;
                	
                	try
                	{
                	    String filename= "4-7.txt";
                	    FileWriter fw = new FileWriter(filename); //the true will append the new data
                	
                	    fw.write("Start cell (4,7) belongs to zone :" + ele);
                	}
                	catch(IOException ioe)
                	{
                	    System.err.println("IOException: " + ioe.getMessage());
                	}
                	
                }
                
                oldEle = ele;
                
                if(ele == 1){
                	ele =0; /////////////////////////////////////////////
                }
                             
                //ele = 1;
                
                break;
                /*
                if(ele == 4){
                	break;
                }
                else{
                	double prob = ran.nextDouble();
                	if(prob < 0.1){
                		break;
                	}
                }
                */
                
            }
            else
                continue;
        }

        //ele=selectEle(start);
        switch(ele){
            case 0:
                goal.x=4;
                goal.y=3;
                break;
            case 1:
                goal.x=4;
                goal.y=6;
                break;
            case 2:
                goal.x=4;
                goal.y=13;
                break;
            case 3:
                goal.x=4;
                goal.y=16;
                break;
            case 4:
                goal.x=15;
                goal.y=3;
                break;
            case 5:
                goal.x=15;
                goal.y=6;
                break;
            case 6:
                goal.x=15;
                goal.y=13;
                break;
            case 7:
                goal.x=15;
                goal.y=16;
                break;
        }

        if( (oldEle == 1) || (true) ){
        	  if( (start.x == 4) && (start.y == 7) ){
              	//System.out.println("Start cell (4,7) belongs to zone :" + ele);
              	//m.pause = true;
              	
              	try
              	{
              	    String filename= "4-7.txt";
              	    FileWriter fw = new FileWriter(filename, true); //the true will append the new data
              	
              	    fw.write("Start cell (4,7) belongs to zone :" + ele);
              	}
              	catch(IOException ioe)
              	{
              	    System.err.println("IOException: " + ioe.getMessage());
              	}
              	
              }
        	
        	  if(ele == 2){
        		  ele2++;
        	  }
        	dstar my=new dstar(m,v,start,goal,1,ele, out);
        	my.start();
        }
    }

    public int selectEle(node n){
        int minDis,x,y,index;
        int[] dis=new int[8];

        x= n.x;
        y=n.y;
        dis[0]=Math.abs(x-4)+Math.abs(y-3);
        dis[1]=Math.abs(x-4)+Math.abs(y-6);
        dis[2]=Math.abs(x-4)+Math.abs(y-13);
        dis[3]=Math.abs(x-4)+Math.abs(y-16);
        dis[4]=Math.abs(x-15)+Math.abs(y-3);
        dis[5]=Math.abs(x-15)+Math.abs(y-6);
        dis[6]=Math.abs(x-15)+Math.abs(y-13);
        dis[7]=Math.abs(x-15)+Math.abs(y-16);

        minDis=dis[0];
        index=0;
        for(int i=1;i<=7;i++){
            if(dis[i]<=minDis){
                index=i;
                minDis=dis[i];
            }
        }

        return index;
    }
    
    public void summery(){
        //System.out.println("Number of Threads: "+m.numOfThreads);
        System.out.println("Average steps for each car: "+(float)m.steps/m.numOfThreads);
        System.out.println("Average optimal steps for each car: "+(float)m.op/m.numOfThreads);
        System.out.println("Total search time: "+ (m.searchTime-m.steps*m.getSpeed()*1000000)+"ns");
        System.out.println("Average search time :" + (double)(m.searchTime-m.steps*m.getSpeed()*1000000)/m.numOfThreads+"ns");
        System.out.println("Number of retrieval requests from zone 2 " + ele2);
        //System.out.println("Average search time per step: " +(double)m.searchTime/m.steps);
        System.out.println("Number of cars moved from zone 0 to 1 " + balanced);
        
    }


}
