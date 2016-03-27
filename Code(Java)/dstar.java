
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class dstar  extends Thread{
    public ArrayList open;
    public ArrayList closed;
    public ArrayList result;
    public ArrayList successors;
    public node start;
    public node goal;
    public model m;
    public view v;
    public boolean flag;       //if has a path
    public int robotPosition;
    public int type;          // coming or going, coming is 0, going is 1
    public int ele;
    public int carStep;
    public int carOptStep;
    public int blankSteps;
    public boolean noPathFound;
    public BufferedWriter out;
    
    public dstar(model mo,view vi, node s, node g, int t,int eleNum, BufferedWriter writer){
        open= new ArrayList();
        closed= new ArrayList();
        result= new ArrayList();
        successors= new ArrayList();
        start=s;
        goal=g;
        m=mo;
        v=vi;
        flag=false;
        type=t;
        ele=eleNum;
        robotPosition=start.x*20+start.y;
        carStep = 0;
        noPathFound = false;
        out = writer;
        if(type == 2){
        	  System.out.println("Type 2.1 request in dstar");
        	  
        	  v.map[start.x][start.y].setBackground(Color.green);
        	  
        	  //System.out.println("Goal cells status :" + m.map[goal.x][goal.y]);
              
              System.out.println("Dstar. Start node :" + start.x + "," + start.y);
        }
      
    }

    synchronized public void run() {
        long timerS=0,timerT=0;
        node n= new node();
        m.numOfThreads++;
        m.op+=Math.abs(start.x-goal.x)+Math.abs(start.y-goal.y);
        carOptStep = Math.abs(start.x-goal.x)+Math.abs(start.y-goal.y);;
                //while not reach the goal
        if(type==0){
            v.map[start.x][start.y].setBackground(Color.red);
            try{
                Thread.sleep(m.getSpeed());
                
                //System.out.println
                //m.eleStatus[ele]
                
                m.setEleStatus(ele,0);                          //release the elevator
                
            }
            catch(Exception e){}
        }
        if(type==1){
            //m.carPositions.remove(m.carPositions.indexOf(start.x*20+start.y));
        }
        if(type == 2){
        	//v.map[start.x][start.y].setBackground(Color.blue);
        }
        
        //System.out.println("Type 2 request in dstar");
        
        while(start.x!=goal.x||start.y!=goal.y){
            open.clear();
            closed.clear();
            if(m.getMap(goal.x, goal.y)==0||m.getMap(goal.x, goal.y)==2){
                open.add(goal);
                if (type == 2){
                	//System.out.println("Goal added to open list " + start.x + ", " + start.y);
                }
            }
            else{                               //wait for elevator is free
                try{
                    Thread.sleep(m.getSpeed());
                    continue;
                }
                catch(Exception ex){}
            }
            flag=false;
            
            timerS=System.nanoTime();
            while(!open.isEmpty()){
                int indexBest=0;                           //record the position the best node in open
                n=(node)open.get(0);
                for(int i=0;i<open.size();i++){            //get best f-value node off from list open
                    if(fValue(((node)open.get(i)))<=fValue(n)){
                        n=(node)open.get(i);
                        indexBest=i;
                    }
                }
                if(isStart(n)){
                    flag=true;
                    moveNext(n);
                    if(type == 2){
                    	//System.out.println("Car to be balanced moved");
                    }
                    try{
                        Thread.sleep(m.getSpeed());
                    }
                    catch(Exception e){
                        
                    }
                    break;
                }
                open.remove(indexBest);
                closed.add(n);
                expand(n);
                for(int i=0;i<successors.size();i++){                         //for each n'
                    boolean is_in_closed=false;
                    int indexClosed=-1;                                 //record the position of existing n' in closed
                    for(int j=0;j<closed.size();j++){
                        if(((node)closed.get(j)).x==((node)successors.get(i)).x&&((node)closed.get(j)).y==((node)successors.get(i)).y){         //if n' is in the set of closed
                            is_in_closed=true;
                            indexClosed=j;
                        /*if(fValue((node)closed.get(j))<=fValue((node)successors.get(i))){               //if the existing one is better
                            break;
                        }*/
                    }
                }

                    boolean is_in_open=false;
                    int indexOpen=-1;                                 //record the position of existing n' in closed
                    for(int j=0;j<open.size();j++){
                        if(((node)open.get(j)).x==((node)successors.get(i)).x&&((node)open.get(j)).y==((node)successors.get(i)).y){         //if n' is in the set of closed
                            is_in_open=true;
                            indexOpen=j;
                            /*if(fValue((node)open.get(j))<=fValue((node)successors.get(i))){               //if the existing one is better
                                break;
                            }*/
                        }
                    }

                    if(is_in_closed){                                //if n' is in closed and existing one is bad, remove it
                        //closed.remove(indexClosed);
                        continue;
                    }
                    if(is_in_open){
                        //open.remove(indexOpen);
                        continue;
                    }
                    open.add(((node)successors.get(i)));           //add n' to open
                }
            }
            timerT=System.nanoTime();
            m.searchTime=m.searchTime+(timerT-timerS);
            if(flag==false){
                //System.out.println("prepare to unblock");
            	
            		if( (start.x == 4) && (start.y == 7) ){
            			String filename= "4-7.txt";
                  	    FileWriter fw1 = null;
						try {
							fw1 = new FileWriter(filename, true);
							fw1.write("Start cell (4,7) found in dstar \n:");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} //then true will append the new data
                  	
                unblocker myun= new unblocker(start, goal, m, v, this, out);
                start=myun.run();
            	}

            }
        }
        if(flag==true){
            //reached the goal
            if(type==0){
                m.carPositions.add(goal.x*20+goal.y);
            }
            if(type==1){                               //wait for elevator
                try{
                    //System.out.println("Arrive"+ele);
                    m.setEleStatus(ele,1);
                    Thread.sleep(m.getSpeed());
                    //System.out.println("Gone"+ele);
                }
                catch(Exception e){}
                m.setMap(goal.x, goal.y, 0);
                v.map[goal.x][goal.y].setBackground(null);
                try{
                    Thread.sleep(200);
                    m.setEleStatus(ele,0);
                }
                catch(Exception e){}
                m.num--;
            }
        }
      
        
        double percent = (m.num / 400.0) * 100.0;
        //System.out.println("Percent = " + percent);
        double mod = percent % 10.0 ;
        //System.out.println("Mod = " + mod);
         
        /*
        if( (mod>0.0) && (mod < 1.0) ){
        	System.out.println("Percent of cars in parking lot : " + ( (m.num / 400.0) * 100.0) + ". Actual Steps = " + carStep + ". Opt Steps = " + carOptStep);        	
        }
        */
        if (percent > 60){
        	System.out.println("Percent of cars in parking lot : " + ( (m.num / 400.0) * 100.0) + ". Actual Steps = " + carStep + ". Optimal Steps = " + carOptStep + ". Blank Steps = " + blankSteps);        	
        }
        
    }


   synchronized public int fValue(node n){
        int h=0,f=0;
        h=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
        f=h+n.g;
        return f;
    }

    public boolean isStart(node n){
        if(n.x==start.x&&n.y==start.y){
            return true;
        }
        else
            return false;
    }

    synchronized public void expand(node n){
        successors.clear();
        //move up
        node newNode= new node();
        if((n.x>0&&m.getMap(n.x-1, n.y)==0)||start.x==n.x-1&&start.y==n.y){
            newNode.x=n.x-1;
            newNode.y=n.y;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
        }

        //move down
        newNode= new node();
        if((n.x<19&&m.getMap(n.x+1, n.y)==0)||start.x==n.x+1&&start.y==n.y){
            newNode.x=n.x+1;
            newNode.y=n.y;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
        }

        //move right
        newNode= new node();
        if((n.y<19&&m.getMap(n.x, n.y+1)==0)||start.x==n.x&&start.y==n.y+1){
            newNode.x=n.x;
            newNode.y=n.y+1;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
        }

        //move left
        newNode= new node();
        if((n.y>0&&m.getMap(n.x, n.y-1)==0)||start.x==n.x&&start.y==n.y-1){
            newNode.x=n.x;
            newNode.y=n.y-1;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
        }
    }

    synchronized public void moveNext(node n){

        try{
            Thread.sleep(100);
        }
        catch(Exception e){}
        
    	int x,y;
        v.map[start.x][start.y].setBackground(null);
        m.setMap(start.x,start.y, 0);
        
        if(type == 2){
        	//m.setMap(start.x,start.y, 5);
        }
        
        start.x=n.parent.x;
        start.y=n.parent.y;
        start.parent=null;
        x=start.x;
        y=start.y;
        m.setMap(x,y,1);                // a robot is an obstacle

        if(type == 2){
        	//m.setMap(x, y, 5);
        }
        
        robotPosition=x*20+y;
        if(type != 2){
        	v.map[x][y].setBackground(Color.red);
        }
        else{
        	v.map[x][y].setBackground(Color.blue);
        }
        m.steps++;
        carStep++;
       
    }

}
