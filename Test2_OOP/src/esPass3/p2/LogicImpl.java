package esPass3.p2;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class LogicImpl {
    
    private Map<Pair<Integer, Integer>, Integer> map = new HashMap<>();
    private Map<Pair<Integer, Integer>, Boolean> op = new HashMap<>();
    private final int size;
    private Random random = new Random();
    
    
    public LogicImpl(int size){
        this.size = size;
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                map.put(new Pair<>(i, j), 1+random.nextInt(6));
            }
        }
        for(int i = 0; i < this.size; i++){
            for(int j = 0; j < this.size; j++){
                op.put(new Pair<>(i, j), false);
            }
        }
    }

    public boolean open(Pair<Integer, Integer> p){
        boolean b = false;
        for (Map.Entry<Pair<Integer, Integer>, Boolean> entry : op.entrySet()) {
            if(entry.getKey().equals(p)){
                b = entry.getValue();
                op.put(p, !b);
            }
        }
        return b;
    }

    public void close(){
        for (Map.Entry<Pair<Integer, Integer>, Boolean> entry : op.entrySet()) {
            op.put(entry.getKey(), false);
        }
    }

    public boolean check(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2){
        //CONTROLLA SE HANNO IL NUMERO UGUALE
        if(map.get(p1).equals(map.get(p2))){
            return true;
        }
        return false;
    }

    public boolean end(List<Pair<Integer,Integer>> disable){
        if(map.entrySet().stream().filter(entry -> !disable.contains(entry.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values().stream().distinct().count()
            == map.entrySet().stream().filter(entry -> !disable.contains(entry.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).size()){
                return true;
        }
        return false;
    }

    public Map<Pair<Integer, Integer>, Integer> getMap(){
        return this.map;
    }


}
