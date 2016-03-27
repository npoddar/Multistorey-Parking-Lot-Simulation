
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cdou9
 */
public class unblocker{

    private node goal;
    private model m;
    private view v;
    private node start;
    private ArrayList open;
    private ArrayList closed;
    private ArrayList openA;
    private ArrayList closedA;
    private ArrayList successors;
    private ArrayList blanks;
    private ArrayList destinations;
    private dstar car;
    public BufferedWriter writer;

    public node b;
    public node d;

    public unblocker(node s, node g, model rm, view rv, dstar thread, BufferedWriter wr1){
        goal= g;
        start= s;
        m= rm;
        v= rv;
        open = new ArrayList();
        closed= new ArrayList();
        openA = new ArrayList();
        closedA= new ArrayList();
        successors= new ArrayList();
        blanks= new ArrayList();
        destinations= new ArrayList();
        b= new node();
        d= new node();
        car = thread;
        writer = wr1;
    }

    synchronized public node run(){

        long timerS=0,timerT=0;
        open.add(start);
        
        if(m.getMap(start.x, start.y) == 0){
    		return null;
        }
        
        //if(m.map[start.x][start.y] == 0){
        	
        //}
        
        //find closest blank nodes
        timerS=System.nanoTime();
        while(true){
            for(int i=0;i<open.size();i++){
                expand((node)open.get(i));
            }

            for(int j=0;j<successors.size();j++){
                int x= ((node)successors.get(j)).x;
                int y= ((node)successors.get(j)).y;
                if(m.getMap(x, y)==0){
                    blanks.add(((node)successors.get(j)));
                }
            }

            if(!blanks.isEmpty())
            	break;
            else{
                open.clear();
                for(int i=0;i<successors.size();i++){
                    open.add(((node)successors.get(i)));
                }
                successors.clear();
            }
        }
        timerT=System.nanoTime();
        m.searchTime=m.searchTime+(timerT-timerS);
        
        //System.out.println("step 1 done    "+blanks.size());
        //find desired destinations for blank cell

        ArrayList array= new ArrayList();
        if(start.x>0){
            if(m.getMap(start.x-1, start.y)!=10){
                node n= new node();
                n.x=start.x-1;
                n.y=start.y;
                n.g=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
                array.add(n);
            }
        }

        if(start.x<19){
            if(m.getMap(start.x+1, start.y) !=10){
                node n= new node();
                n.x=start.x+1;
                n.y=start.y;
                n.g=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
                array.add(n);
            }
        }

        if(start.y>0){
            if(m.getMap(start.x, start.y-1) !=10){
                node n= new node();
                n.x=start.x;
                n.y=start.y-1;
                n.g=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
                array.add(n);
            }
        }

        if(start.y<19){
            if(m.getMap(start.x, start.y+1)!=10){
                node n= new node();
                n.x=start.x;
                n.y=start.y+1;
                n.g=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
                array.add(n);
            }
        }

        int min=((node)array.get(0)).g;
        for(int i=0;i<array.size();i++){
            if(((node)array.get(i)).g<min){
                destinations.clear();
                destinations.add(((node)array.get(i)));
            }
            else if(((node)array.get(i)).g==min)
                destinations.add(((node)array.get(i)));
        }
        //System.out.println("step 2 done    "+destinations.size());
        //choose the best pair of blank and destination
        int indexB=0, indexD=0;
        int minh=((node)destinations.get(0)).g+Math.abs(((node)blanks.get(0)).x-((node)destinations.get(0)).x)+Math.abs(((node)blanks.get(0)).y-((node)destinations.get(0)).y);
        for(int i=0; i<blanks.size();i++)
            for(int j=0;j<destinations.size();j++){
                int h=((node)destinations.get(j)).g+Math.abs(((node)blanks.get(i)).x-((node)destinations.get(j)).x)+Math.abs(((node)blanks.get(i)).y-((node)destinations.get(j)).y);
                if(h<minh){
                    indexB=i;
                    indexD=j;
                }
            }
        b=(node)blanks.get(indexB);
        d=(node)destinations.get(indexD);
        //System.out.println("start.x="+start.x+"    start.y="+start.y+"    b.x="+b.x+"    b.y="+b.y+"    d.x="+d.x+"    d.y="+d.y);
        
        //System.out.println("Size of blanks :" + blanks.size());
        
        return astar(b,d,start);
    }

    synchronized public void expand(node n){
        int po = n.x*20+n.y;
        if(n.x>0&&po!=103&&po!=106&&po!=113&&po!=116&&po!=323&&po!=326&&po!=333&po!=336){
            node newNode= new node();
            newNode.x= n.x-1;
            newNode.y= n.y;
            if(!isInClosed(newNode)){
                successors.add(newNode);
                closed.add(newNode);
            }
        }

        if(n.x<19&&po!=63&&po!=66&&po!=73&&po!=76&&po!=283&&po!=286&&po!=293&po!=296){
            node newNode= new node();
            newNode.x= n.x+1;
            newNode.y= n.y;
            if(!isInClosed(newNode)){
                successors.add(newNode);
                closed.add(newNode);
            }

        }

        if(n.y<19&&po!=82&&po!=85&&po!=92&&po!=95&&po!=302&&po!=305&&po!=312&po!=315){
            node newNode= new node();
            newNode.x= n.x;
            newNode.y= n.y+1;
            if(!isInClosed(newNode)){
                successors.add(newNode);
                closed.add(newNode);
            }
        }
        if(n.y>0&&po!=84&&po!=87&&po!=94&&po!=97&&po!=304&&po!=307&&po!=314&po!=317){
            node newNode= new node();
            newNode.x= n.x;
            newNode.y= n.y-1;
            if(!isInClosed(newNode)){
                successors.add(newNode);
                closed.add(newNode);
            }
        }
    }

    public boolean isInClosed(node n){
        boolean inClosed= false;
        for(int i=0; i<closed.size();i++){
            if(n.x== ((node)closed.get(i)).x&&n.y== ((node)closed.get(i)).y){
                inClosed=true;
                break;
            }
        }
        if(inClosed)
            return true;
        else
            return false;
    }

    /*synchronized public node moveBlank(node blank, node desti, node start){
        node curr=new node();
        node my=new node();
        curr=blank;
        my.x=curr.x;
        my.y=curr.y;
        list.clear();
        list.add(my);
        while(curr.x!=desti.x||curr.y!=desti.y){
            System.out.println("curr.x="+curr.x+"    curr.y="+curr.y);
            System.out.println("list:");
            for(int i=0;i<list.size();i++)
                System.out.println("x=" + ((node) list.get(i)).x + "    y=" + ((node) list.get(i)).y);
                System.out.println();
            successors.clear();
            //expand this node
            //up
            int po=curr.x*20+curr.y;
            if(curr.x>0&&m.map[curr.x-1][curr.y]!=10&&(curr.x-1!=start.x||curr.y!=start.y)&&po!=103&&po!=106&&po!=113&&po!=116&&po!=323&&po!=326&&po!=333&po!=336){
                boolean isInList= false;
                for(int i=0; i<list.size();i++){
                    if(((node)list.get(i)).x==curr.x-1&&((node)list.get(i)).y==curr.y){
                        isInList=true;
                        break;
                    }
                    //System.out.println(isInList);
                }
                if(!isInList){
                    node newNode = new node();
                    newNode.x= curr.x-1;
                    newNode.y= curr.y;
                    successors.add(newNode);
                    list.add(newNode);
                }
            }
            //down
            if(curr.x<19&&m.map[curr.x+1][curr.y]!=10&&(curr.x+1!=start.x||curr.y!=start.y)&&po!=63&&po!=66&&po!=73&&po!=76&&po!=283&&po!=286&&po!=293&po!=296){
                boolean isInList= false;
                for(int i=0; i<list.size();i++){
                    if(((node)list.get(i)).x==(curr.x+1)&&((node)list.get(i)).y==curr.y){
                        isInList=true;
                        break;
                    }
                }
                if(!isInList){
                    node newNode = new node();
                    newNode.x= curr.x+1;
                    newNode.y= curr.y;
                    successors.add(newNode);
                    list.add(newNode);
                }
                //System.out.println(isInList);
            }
            //left
            if(curr.y>0&&m.map[curr.x][curr.y-1]!=10&&(curr.x!=start.x||curr.y-1!=start.y)&&po!=82&&po!=85&&po!=92&&po!=95&&po!=302&&po!=305&&po!=312&po!=315){
                boolean isInList= false;
                for(int i=0; i<list.size();i++){
                    if(((node)list.get(i)).x==curr.x&&((node)list.get(i)).y==curr.y-1){
                        isInList=true;
                        break;
                    }
                }
                if(!isInList){
                    node newNode = new node();
                    newNode.x= curr.x;
                    newNode.y= curr.y-1;
                    successors.add(newNode);
                    list.add(newNode);
                }
                //System.out.println(isInList);
            }
            //right
            if(curr.y<19&&m.map[curr.x][curr.y+1]!=10&&(curr.x!=start.x||curr.y+1!=start.y)&&po!=84&&po!=87&&po!=94&&po!=97&&po!=304&&po!=307&&po!=314&po!=317){
                boolean isInList= false;
                for(int i=0; i<list.size();i++){
                    if(((node)list.get(i)).x==curr.x&&((node)list.get(i)).y==curr.y+1){
                        isInList=true;
                        break;
                    }
                }
                if(!isInList){
                    node newNode = new node();
                    newNode.x= curr.x;
                    newNode.y= curr.y+1;
                    successors.add(newNode);
                    list.add(newNode);
                }
                //System.out.println(isInList);
            }
            int min, index=0;
            min=Math.abs(((node)successors.get(0)).x-desti.x)+Math.abs(((node)successors.get(0)).y-desti.y);
            for(int i=0;i<successors.size();i++){
                if((Math.abs(((node)successors.get(i)).x-desti.x)+Math.abs(((node)successors.get(i)).y-desti.y))<min){
                    min=Math.abs(((node)successors.get(i)).x-desti.x)+Math.abs(((node)successors.get(i)).y-desti.y);
                    index=i;
                }
            }
            //make one movement
            int temp;
            temp=m.map[((node)successors.get(index)).x][((node)successors.get(index)).y];
            m.map[((node)successors.get(index)).x][((node)successors.get(index)).y]= m.map[curr.x][curr.y];
            m.map[curr.x][curr.y]=temp;

            Color col;
            col=v.map[curr.x][curr.y].getBackground();
            v.map[curr.x][curr.y].setBackground(v.map[((node)successors.get(index)).x][((node)successors.get(index)).y].getBackground());
            v.map[((node)successors.get(index)).x][((node)successors.get(index)).y].setBackground(col);

            curr.x=((node)successors.get(index)).x;
            curr.y=((node)successors.get(index)).y;
            try{
                Thread.sleep(m.getSpeed());
            }
            catch(Exception e){System.out.println("sleep error");}

        }

        //move start to blank
        int temp;

        temp=m.map[start.x][start.y];
        m.map[start.x][start.y]= m.map[curr.x][curr.y];
        m.map[curr.x][curr.y]=temp;

        Color col;
        col=v.map[curr.x][curr.y].getBackground();
        v.map[curr.x][curr.y].setBackground(v.map[start.x][start.y].getBackground());
        v.map[start.x][start.y].setBackground(col);
        try{Thread.sleep(m.getSpeed());}
        catch(Exception e){System.out.println("sleep error");}
        System.out.println("move complete");
        return curr;
    }*/

    synchronized public node astar(node blank, node desti, node start){

    	System.out.println("Start : " + start.x + ", " + start.y + ".");
    	    		
        open.clear();
        closed.clear();
        successors.clear();
        
        node n= new node();
        node curr= new node();
        long timerS=0, timerT=0;
        boolean flag;

        open.add(blank);
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
            if(isGoal(n, desti)){
                flag=true;
                move(n,blank);
                moveCell(desti, start);
                return desti;
            }
            open.remove(indexBest);
            closed.add(n);
            expandA(n, start);
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
        	System.out.println("Can't find path. Size of blanks :" + blanks.size());
        	//v.map[start.x][start.y].setBackground(Color.yellow);
        	v.map[start.x][start.y].setText("b");
        	System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");  
        	try
        	{
        	    String filename= "BlankCount.txt";
        	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
        	    fw.write("Destination : " + desti.x + "," + desti.y + ".\n");
        	    fw.write("Blank : " + blank.x + "," + blank.y + ".\n");
        	    fw.write("Can't find path. Size of blanks :" + blanks.size() + "\n");//appends the string to the file
        	    fw.write("Start : " + start.x + "," + start.y + ".\n");
        	    fw.write("Status of cell start " + m.map[start.x][start.y] + "\n");
       	    
        	    fw.close();
        	    
        	    
        	    System.out.println("Destination : " + desti.x + "," + desti.y + ".");
        	    System.out.println("Blank : " + blank.x + "," + blank.y + ".");        	    
        	    
     	    
        	}
        	catch(IOException ioe)
        	{
        	    System.err.println("IOException: " + ioe.getMessage());
        	}
        	  
        	System.out.println("Can't find path!!");
            car.noPathFound = true;
        }
        return null;
    }

    synchronized public void expandA(node n, node start){
        successors.clear();
        //up
        int po=n.x*20+n.y;
        if(n.x>0&&m.getMap(n.x-1, n.y)!=10&&(n.x-1!=start.x||n.y!=start.y)&&po!=103&&po!=106&&po!=113&&po!=116&&po!=323&&po!=326&&po!=333&po!=336){
            node newNode = new node();
            newNode.x= n.x-1;
            newNode.y= n.y;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
            }
        //down
        if(n.x<19&&m.getMap(n.x+1, n.y) !=10&&(n.x+1!=start.x||n.y!=start.y)&&po!=63&&po!=66&&po!=73&&po!=76&&po!=283&&po!=286&&po!=293&po!=296){
            node newNode = new node();
            newNode.x= n.x+1;
            newNode.y= n.y;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
            }
        //left
        if(n.y>0&&m.getMap(n.x, n.y-1)!=10&&(n.x!=start.x||n.y-1!=start.y)&&po!=82&&po!=85&&po!=92&&po!=95&&po!=302&&po!=305&&po!=312&po!=315){
            node newNode = new node();
            newNode.x= n.x;
            newNode.y= n.y-1;
            newNode.g= n.g+1;
            newNode.parent=n;
            successors.add(newNode);
            }
        //right
        if(n.y<19&&m.getMap(n.x, n.y+1)!=10&&(n.x!=start.x||n.y+1!=start.y)&&po!=84&&po!=87&&po!=94&&po!=97&&po!=304&&po!=307&&po!=314&po!=317){
            node newNode = new node();
            newNode.x= n.x;
            newNode.y= n.y+1;
            newNode.g=n.g+1;
            newNode.parent=n;
            successors.add(newNode);
        }
    }

    synchronized public int fValue(node n){
        int h=0,f=0;
        h=Math.abs(n.x-goal.x)+Math.abs(n.y-goal.y);
        f=h+n.g;
        return f;
    }

    public boolean isGoal(node n,node goal){
        if(n.x==goal.x&&n.y==goal.y){
            return true;
        }
        else
            return false;
    }

    synchronized public void move(node n,node blank){
    	 try{
             Thread.sleep(100);
         }
         catch(Exception e){}
    	 
    	ArrayList path= new ArrayList();
        //build a path
        path.add(n);
        while(n.x!=blank.x||n.y!=blank.y){
            path.add(n.parent);
            n=n.parent;
        }
        path.add(n);

        // move along the path
        for(int i=path.size()-2;i>=0;i--){
            int x=((node)path.get(i)).x;
            int y=((node)path.get(i)).y;
            if(m.getMap(x, y) !=0){
                //move the blank
                int temp;
                int tempx=((node)path.get(i+1)).x;
                int tempy=((node)path.get(i+1)).y;

                temp=m.getMap(x, y);
                m.setMap(x, y, m.getMap(tempx, tempy));
                m.setMap(tempx, tempy, temp);

                Color col;
                col=v.map[x][y].getBackground();
                v.map[x][y].setBackground(v.map[tempx][tempy].getBackground());
                v.map[tempx][tempy].setBackground(col);
                try{
                    Thread.sleep(m.getSpeed());
                }
                catch(Exception e){}
                m.steps++;
                //car.carStep++;  - Not counted. Blank cell moves //
                car.blankSteps++; //Counting the blank cell movement;
            }
            else{
                continue;
            }
        }
    }

    synchronized public void moveCell(node desti, node start){

    	 try{
             Thread.sleep(100);
         }
         catch(Exception e){}
    	
        int temp;

        temp=m.getMap(start.x, start.y);
        m.setMap(start.x, start.y, m.getMap(desti.x, desti.y));
        m.setMap(desti.x, desti.y, temp);

        Color col;
        col=v.map[desti.x][desti.y].getBackground();
        v.map[desti.x][desti.y].setBackground(v.map[start.x][start.y].getBackground());
        v.map[start.x][start.y].setBackground(col);
        try{Thread.sleep(m.getSpeed());}
        catch(Exception e){System.out.println("sleep error");}
        m.steps++;
        car.carStep++;
        
       


        //System.out.println("Move complete");
    }

}
