package a02c.e2;

import java.util.HashMap;
import java.util.Map;

public class LogicImpl {
    
    private Map<Pair<Integer,Integer>, Boolean> map = new HashMap<>();
    private int expand = 2;

    public LogicImpl(int size){
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                map.put(pos, false);
            }
        }
    }

    public void firstHit(Pair<Integer,Integer> pos){
        int x = pos.getX();
        int y = pos.getY();
        // for(int k = 1; k < 11; k += 4){
            for(int i = x-1; i <= x+1; i++){
                for(int j = y-1; j <= y+1; j++){
                    map.put(new Pair<Integer,Integer>(i, j), true);
                }
            }
        //     map.put(new Pair<Integer,Integer>(k, k), false);
        // }
        
        map.put(pos, false);
    }

    //le X sono quelle orizzontali, Y verticali
    public String isVertex(Pair<Integer,Integer> pos){
        if((map.get(new Pair<>(pos.getX(), pos.getY() - 1)) && map.get(new Pair<>(pos.getX() + 1, pos.getY())))){
            return "bottom left";
        }
        if (map.get(new Pair<>(pos.getX(), pos.getY() + 1)) && map.get(new Pair<>(pos.getX() + 1, pos.getY()))){
            return "top left";
        }
        if (map.get(new Pair<>(pos.getX(), pos.getY() + 1)) && map.get(new Pair<>(pos.getX() - 1, pos.getY()))){
            return "top right";
        }
        if (map.get(new Pair<>(pos.getX(), pos.getY() - 1)) && map.get(new Pair<>(pos.getX() - 1, pos.getY()))){
            return "bottom right";
        }
        return "";
    }

    private void clearMap(){
        map.entrySet().forEach(entry -> entry.setValue(false));
    }

    public void hitPoint(Pair<Integer,Integer> pos){
        String area = isVertex(pos);
        clearMap();
        int x = pos.getX();
        int y = pos.getY();
        if(area.equals("top right")){
            for(int i = x-expand; i <= x+1; i++){
                for(int j = y-1; j <= y+expand; j++){
                    map.put(new Pair<Integer,Integer>(i, j), true);
                }
            }
            for(int i = x-expand + 1; i <= x; i++){
                for(int j = y; j <= y+expand - 1; j++){
                    map.put(new Pair<Integer,Integer>(i, j), false);
                }
            }
        }
        if(area.equals("top left")){
            for(int i = x+expand; i >= x-1; i--){
                for(int j = y-1; j <= y+expand; j++){
                    map.put(new Pair<Integer,Integer>(i, j), true);
                }
            }
            for(int i = x+expand - 1; i >= x; i--){
                for(int j = y; j <= y+expand - 1; j++){
                    map.put(new Pair<Integer,Integer>(i, j), false);
                }
            }
        }
        expand++;
    }

    // public void fillBoolRight(String corner, Pair<Integer,Integer> pos){
    //     int one = 1;
    //     if(corner.equals("bottom right")){
    //         one = -one;
    //     }
    //     int x = pos.getX();
    //     int y = pos.getY();
    //     for(int i = x-expand; i <= x+1; i++){
    //         for(int j = y-one; j <= y+expand; j++){
    //             map.put(new Pair<Integer,Integer>(i, j), true);
    //         }
    //     }
    //     for(int i = x-expand + 1; i <= x; i++){
    //         for(int j = y; j <= y+expand - 1; j++){
    //             map.put(new Pair<Integer,Integer>(i, j), false);
    //         }
    //     }
    //     expand++;
    // }

    public Map<Pair<Integer,Integer>, Boolean> getMap(){
        return this.map;
    }
}
