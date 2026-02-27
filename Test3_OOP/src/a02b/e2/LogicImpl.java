package a02b.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogicImpl implements Logic{

    private Map<Pair<Integer, Integer>, Boolean> map = new HashMap<>();
    List<Pair<Integer,Integer>> list = new ArrayList<>();
    int size;
    
    public LogicImpl(int size) {
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                map.put(new Pair<>(i, j), false);
            }
        }
        this.size=size;
    }

    @Override
    public boolean hit(Integer x, Integer y) {
        var val = map.get(new Pair<>(x,y));
        map.put(new Pair<>(x,y), !val);
        return !val;
    }

    @Override
    public boolean check() {
        List<Pair<Integer,Integer>> listC = new ArrayList<>();
        //manca da verificare le diagonali da questo senso: /  -> FATTO
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(map.get(new Pair<>(i,j))){
                    int x=i+1;
                    int y=j+1;
                    while(x < size && y < size){
                        if(map.get(new Pair<>(x,y))){
                            listC.add(new Pair<>(x,y));
                        }
                        x++;
                        y++;
                    }
                    if(listC.size() == 2){
                        return updateList(listC);
                    }
                }
            }
        }
        listC.clear();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(map.get(new Pair<>(i,j))){
                    int xl=i+1;
                    int yl=j-1;
                    while(xl < size && yl >= 0){
                        if(map.get(new Pair<>(xl,yl))){
                            listC.add(new Pair<>(xl,yl));
                        }
                        xl++;
                        yl--;
                    }
                    if(listC.size() == 2){
                        return updateList(listC);
                    }
                }
            }
        }
        return false;
    }

    public boolean updateList(List<Pair<Integer,Integer>> listC){
        if(listC.size() == 2){
            setList(listC);
            return true;
        }
        listC.clear();
        return false;
    }

    public void setList(List<Pair<Integer,Integer>> list){
        this.list = list;
    }    

    public List<Pair<Integer,Integer>> toDisable(){
        return this.list;
    }

    @Override
    public void clean() {
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                map.put(new Pair<>(i, j), false);
            }
        }
    }
    
}
