import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;

public class controller implements ActionListener{
    public model M;
    public view V;

    public controller(model m, view v){
        M=m;
        V=v;
    }

    public void actionPerformed(ActionEvent e){
        bg back= new bg(M,V);
        if(e.getSource()==V.start){
            //M.pause=false;
            back.start();
                //System.out.println(M.max);
            V.pause.setEnabled(true);
            V.start.setEnabled(false);
        }


        if(e.getSource()==V.stop){
            
            System.exit(0);
        }

        if(e.getSource()==V.pause){
            V.pause.setEnabled(false);
            V.start.setEnabled(true);
            
            M.pause=true;
        }
        if(e.getSource()==V.slower){
            M.setSpeed(M.getSpeed()+100);
            //System.out.println(M.getSpeed());
        }
        if(e.getSource()==V.faster){
            if(M.getSpeed()>=50){
                M.setSpeed(M.getSpeed()-50);
                //System.out.println(M.getSpeed());
            }
        }
    }
}
