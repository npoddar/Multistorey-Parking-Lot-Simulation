
public class node {
    public int x;
    public int y;
    public int g;
    public node parent;

    public node(){
        x=0;
        y=0;
        g=0;
        parent=null;
    }

    public node(int a, int b){
        x=a;
        y=b;
        g=0;
        parent=null;
    }
}
