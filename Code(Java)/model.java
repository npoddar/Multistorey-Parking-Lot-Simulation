
import java.util.ArrayList;


public class model {
    public int[][] map;
    public int speed;
    public int num;                    //number of cars
    public ArrayList carPositions;         // positions of cars
    public int[] eleStatus;            // if elevator is free, 0 is free, 1 is occupied
    public int max;
    public int[][] pri;
    public int[][] zone;
    public boolean pause;
    public int steps;                 //total steps
    public int op;                   //total steps on optimal paths
    public int numOfThreads;         //total numbers of threads
    public long searchTime;          //total search time
    public long totalTime;

    public model(){
        synchronized (model.class) {
        map=new int[20][20];
        pri= new int[20][20];
        zone= new int[20][20];
        speed=100;
        num=0;
        carPositions= new ArrayList();
        max= (int)((400- 400/50*3)*0.90);
        pause= false;
        steps=0;
        op=0;
        numOfThreads=0;
        searchTime=0;
        totalTime = 0;


        eleStatus= new int[8];

        //set elevators
        for(int i=4;i<=5;i++){
            map[4][i]=10;
            map[5][i]=10;
            map[14][i]=10;
            map[15][i]=10;
            map[4][i+10]=10;
            map[5][i+10]=10;
            map[14][i+10]=10;
            map[15][i+10]=10;
        }


        // set priorities
        for(int i=0;i<20;i++){
            pri[i][0]=2;
            pri[i][19]=2;
        }
        for(int i=2;i<18;i++){
            pri[0][i]=2;
            pri[19][i]=2;
            if(i==4||i==15)
                continue;
            pri[i][2]=1;
            pri[i][8]=1;
            pri[i][11]=1;
            pri[i][17]=1;
        }
        for(int i=3;i<8;i++){
            pri[2][i]=1;
            pri[17][i]=1;
        }
        for(int i=12;i<17;i++){
            pri[2][i]=1;
            pri[17][i]=1;
        }
        for(int i=7;i<13;i++){
            pri[i][3]=1;
            pri[i][5]=1;
            pri[i][6]=1;
            pri[i][13]=1;
            pri[i][14]=1;
            pri[i][16]=1;
        }
        
        for(int i=4;i<=5;i++){
            pri[4][i]=-1;
            pri[5][i]=-1;
            pri[14][i]=-1;
            pri[15][i]=-1;
            pri[4][i+10]=-1;
            pri[5][i+10]=-1;
            pri[14][i+10]=-1;
            pri[15][i+10]=-1;
        }
        pri[4][3]=-1;
        pri[4][6]=-1;
        pri[4][13]=-1;
        pri[4][16]=-1;
        pri[15][3]=-1;
        pri[15][6]=-1;
        pri[15][13]=-1;
        pri[15][16]=-1;

        pri[2][3]=0;
        pri[2][6]=0;
        pri[2][13]=0;
        pri[2][16]=0;
        pri[17][3]=0;
        pri[17][6]=0;
        pri[17][13]=0;
        pri[17][16]=0;

        //set zones
        for(int z=0;z<4;z++)
            for(int i=0;i<10;i++)
                for(int j=0;j<5;j++){
                    zone[i][j+z*5]=z;
                }
        for(int z=0;z<4;z++)
            for(int i=10;i<20;i++)
                for(int j=0;j<5;j++){
                    zone[i][j+z*5]=z+4;
                }

        for(int i=4;i<=5;i++){
            zone[4][i]=-1;
            zone[5][i]=-1;
            zone[14][i]=-1;
            zone[15][i]=-1;
            zone[4][i+10]=-1;
            zone[5][i+10]=-1;
            zone[14][i+10]=-1;
            zone[15][i+10]=-1;
        }
        zone[4][3]=-1;
        zone[4][6]=-1;
        zone[4][13]=-1;
        zone[4][16]=-1;
        zone[15][3]=-1;
        zone[15][6]=-1;
        zone[15][13]=-1;
        zone[15][16]=-1;
        
        }

    }

    public int getSpeed(){
        synchronized (model.class) {
        	return this.speed;
        }
    }
    public void setSpeed(int s){
        synchronized (model.class) {
        speed=s;
        }
    }

    public void setEleStatus(int num, int value){
        synchronized (model.class) {
        	this.eleStatus[num]=value;
        }
    }

    public synchronized void setMap(int x,int y,int value){
        synchronized (model.class) {
        	this.map[x][y]=value;
        }
    }
    
    public synchronized int getMap(int x, int y){
        synchronized (model.class) {
        	return this.map[x][y];
        }
    }
    
}
