import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
public class view extends JFrame{
    public JButton start = new JButton("Start");
    public JButton pause = new JButton("Pause");
    public JButton stop  = new JButton("Quit");
    public JButton faster= new JButton("Faster");
    public JButton slower= new JButton("Slower");
    public JLabel[][] map;

    public view(){
        model m = new model();
        controller c = new controller(m, this);
        JPanel con = new JPanel();
        con.setLayout(null);
        int[][] temp=m.map;
        
        String filename= "BlankCount.txt";
        String filename1= "4-7.txt";
	    try {
			FileWriter fw = new FileWriter(filename, false);
			FileWriter fw1 = new FileWriter(filename, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        map= new JLabel[20][20];
        for(int i=0;i<20;i++){
            for(int j=0;j<20;j++){
                map[i][j]=new JLabel("",JLabel.CENTER);
                map[i][j].setBounds(50+j*50, 50+i*25,50,25);
                map[i][j].setOpaque(true);
                map[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                if(temp[i][j]==10){
                    map[i][j].setBackground(Color.black);
                }
                //map[i][j].setText(Integer.toString(m.pri[i][j])+", "+Integer.toString(m.zone[i][j]));
                con.add(map[i][j]);
                }
        }
        map[4][3].setText("load");
        map[4][6].setText("load");
        map[4][13].setText("load");
        map[4][16].setText("load");
        map[15][3].setText("load");
        map[15][6].setText("load");
        map[15][13].setText("load");
        map[15][16].setText("load");

        start.setBounds(300,600,100,25);
        pause.setBounds(600,600,100,25);
        stop.setBounds(750,600,100,25);
        faster.setBounds(1060, 200, 100, 25);
        slower.setBounds(1060, 350, 100, 25);
        con.add(start);
        con.add(pause);
        con.add(stop);
        con.add(slower);
        con.add(faster);
        start.addActionListener(c);
        pause.addActionListener(c);
        stop.addActionListener(c);
        faster.addActionListener(c);
        slower.addActionListener(c);
        this.setContentPane(con);
        this.pack();
    }
    public static void main(String args[]){
        JFrame window= new view();
        window.setSize(1200,700);
        window.setTitle("Parking 20 X 20");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setVisible(true);
    }
}
