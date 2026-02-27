package provaLuglio.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl {

    private int size;
    private Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    
    public LogicImpl(int size){
        this.size = size;
        clearMap();
    }

    public Pair<Integer, Integer> nextHit(Pair<Integer, Integer> pos){
        clearMap();
        for (int i=pos.getX(); i<=pos.getX()+4; i++){
            for (int j=pos.getY()-4; j<=pos.getY(); j++){
                var position = new Pair<>(i,j);
                map.put(position, true);
            }
        }
        var center = new Pair<>(pos.getX() + 2, pos.getY() - 2);
        for(Pair<Integer, Integer> p : getCorner(center)){
            map.put(p, false);
        }
        return center;
    }

    public void hit(Pair<Integer, Integer> pos){
        for (int i=pos.getX()-2; i<=pos.getX()+2; i++){
            for (int j=pos.getY()-2; j<=pos.getY()+2; j++){
                var position = new Pair<>(i,j);
                map.put(position, true);
            }
        }
        for(Pair<Integer, Integer> p : getCorner(pos)){
            map.put(p, false);
        }
    }

    public List<Pair<Integer, Integer>> getCorner(Pair<Integer, Integer> pos){
        List<Pair<Integer, Integer>> list = new ArrayList<>();
        list.add(new Pair<Integer,Integer>(pos.getX()-1, pos.getY()-1));
        list.add(new Pair<Integer,Integer>(pos.getX()+1, pos.getY()-1));
        list.add(new Pair<Integer,Integer>(pos.getX()-1, pos.getY()+1));
        list.add(new Pair<Integer,Integer>(pos.getX()+1, pos.getY()+1));
        return list;
    }

    public void clearMap(){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                var pos = new Pair<>(j,i);
                this.map.put(pos, false);
            }
        }
    }

    public Map<Pair<Integer, Integer>, Boolean> getMap(){
        return this.map;
    }
}
