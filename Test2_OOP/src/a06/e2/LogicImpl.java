package a06.e2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

public class LogicImpl implements Logic{
    
    private final Map<Pair<Integer,Integer>, Integer> map = new HashMap<>();
    int fireTimes;
    int sames;
    int size;

    public LogicImpl(int size, List<JButton> list){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                Pair<Integer, Integer> pair = new Pair<Integer,Integer>(i, j);
                map.put(pair, Integer.parseInt(list.get((i*size)+j).getText()));
            }
        }
        this.fireTimes=0;
        this.sames=0;
        this.size=size;
    }

    public void hitFire(List<JButton> list){
        fireTimes++;
        sames = 0;
        for(int j=0; j<size; j++){
            for(int i=(size - 1); i >= 0 && map.get(new Pair<>(fireTimes-1,j)) != 0; i--){
                Pair<Integer, Integer> pairDown = new Pair<Integer,Integer>(i, j);
                Pair<Integer, Integer> pairUp = new Pair<Integer,Integer>(i-1, j);
                if(map.get(pairDown).equals(map.get(pairUp)) && map.get(pairDown) != 0){ //unione valori dei buttons uguali
                    map.put(pairDown, (map.get(pairDown)*2));

                    list.get((i*size)+j).setText(String.valueOf((map.get(pairDown))));

                    for(int x = (i-1); x > 0; x--){ //slittamento valori verso il basso
                        map.put(new Pair<>(x,j), map.get(new Pair<>(x-1,j)));

                        if(map.get(new Pair<>(x-1,j)) != 0){
                            list.get((x*size)+j).setText(String.valueOf(map.get(new Pair<>(x-1,j))));
                        }
                    }
                    map.put(new Pair<>(fireTimes-1,j),0);

                    list.get(((fireTimes-1)*size)+j).setText("");
                }
            }
            sames += hasDuplicate(j);
        }
    }

    public boolean finish(){
        return this.sames == 0;
    }

    public int hasDuplicate(int column){
        for(int i = 0; i < Math.sqrt(map.size())-1; i++){
            if(map.get(new Pair<>(i, column)).equals(map.get(new Pair<>(i+1, column))) && map.get(new Pair<>(i, column)) != 0){
                return 1;
            }
        }
        return 0;
    }
}
