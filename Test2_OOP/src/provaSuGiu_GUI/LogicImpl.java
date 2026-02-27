package provaSuGiu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl {
    
    Map<Pair<Integer,Integer>, Integer> map = new HashMap<>();
    int size;
    int count;
    boolean incastro = false;

    public LogicImpl(int size){
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map.put(new Pair<>(i, j), 0);
            }
        }
    }
    
    public int hit(Pair<Integer,Integer> p){
        int val;
        val = map.get(p);
        val++;
        if(val > 2){
            val = 0;
        }
        map.put(p, val);
        return val;
    }

    public void update(Pair<Integer,Integer> p){
        map.put(p, 0);
    }

    public void go(){
        count = 0;
        List<Pair<Integer,Integer>> dup = new ArrayList<>();
        Pair<Integer,Integer> remember = new Pair<>(-1, -1);
        for(Map.Entry<Pair<Integer,Integer>, Integer> entry : map.entrySet()){
            if(entry.getValue() == 1){
                Pair<Integer,Integer> pUp = new Pair<>(entry.getKey().e1() - 1, entry.getKey().e2());
                // if(pUp.e1() < 0);
                // else if(map.get(pUp) == 2 || map.get(pUp) == 1);
                if(pUp.e1() >= 0 && map.get(pUp) != 2 && map.get(pUp) != 1){
                    update(entry.getKey());
                    map.put(pUp, 1);
                }
            }
            else if(entry.getValue() == 2 && !dup.contains(entry.getKey()) || entry.getKey().equals(remember)){
                Pair<Integer,Integer> pDo = new Pair<>(entry.getKey().e1() + 1, entry.getKey().e2());
                if(pDo.e1() >= size);
                // else if(map.get(pDo) == 1);
                else if(map.get(pDo) == 2 && pDo.e1() != (size-1)){
                    remember = pDo;
                    map.put(pDo, 2);
                    if(!entry.getKey().equals(remember)){
                        update(entry.getKey());
                    }
                    
                    //incastro = true;
                }
                else if(pDo.e1() < size && map.get(pDo) != 1){
                    map.put(pDo, 2);
                    if(!entry.getKey().equals(remember)){
                        update(entry.getKey());
                    }
                    dup.add(pDo);
                }
            }
        }
    }

    public Map<Pair<Integer,Integer>, Integer> getMap(){
        return map;
    }
}
