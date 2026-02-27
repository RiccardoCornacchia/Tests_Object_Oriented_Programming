package esPass2.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicImpl {
    
    private List<Pair<Integer, Integer>> list = new ArrayList<>();
    private Pair<Integer, Integer> randP;
    private Random random = new Random();
    private int width;
    private int height;

    public LogicImpl(int x, int y){
        randP = new Pair<Integer,Integer>(random.nextInt(x), random.nextInt(y));
        width = y;
        height = x;
    }

    public List<Pair<Integer, Integer>> getList(){
        return this.list;
    }

    public Pair<Integer, Integer> getRand(){
        return this.randP;
    }

    public void resetList(){
        this.list = new ArrayList<>();
    }

    public void hit(Pair<Integer, Integer> p){
        list.add(p);
        int col = 1;
        Pair<Integer, Integer> temp;
        boolean ok = true;
        for(int i = 1; ok; i++){
            int k = i;
            while(k >= (-i)){
                temp = new Pair<>(p.getX()+k, p.getY()+col);
                if(temp.getX() == 0 || temp.getX() == (height - 1) || temp.getY() == 0 || temp.getY() == (width - 1)){
                    ok = false;
                }
                list.add(temp);
                k--;
            }
            col++;
        }
    }
}
