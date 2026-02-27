import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class SpreadRowImpl implements SpreadRow{
    
    List<Pair<Optional<Integer>, Optional<Function<Integer,Integer>>>> list = new ArrayList<>();
    int size;

    public SpreadRowImpl(int size){
        this.size=size;
        for(int i = 0; i < size; i++){
            list.add(i, new Pair<>(Optional.empty(), Optional.empty()));
        }
    }

    public int size(){
        return this.size;
    }

    
    public boolean isFormula(int index){
        return list.get(index).get2().isPresent();
    }

    
    public boolean isNumber(int index){
        return list.get(index).get1().isPresent();
    }

    
    public boolean isEmpty(int index){
        if(list.get(index).get1().isPresent() || list.get(index).get2().isPresent()){
            return false;
        }
        return true;
    }

    
    public List<Optional<Integer>> computeValues(){
        List<Optional<Integer>> newList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            if(list.get(i).get1().isPresent()){
                newList.add(list.get(i).get1());
            }
            else{
                
            }
        }
        return newList;
    }

    
    public void putNumber(int index, int number){
        list.put(index, new Pair<>(Optional.of(number), Optional.empty()));
    }
    
    
    public void putSumOfTwoFormula(int resultIndex, int index1, int index2){
        list.put(resultIndex, new Pair<>(Optional.empty(), Optional.of(new Function<Integer,Integer>())));
    }

    
    public void putMultiplyElementsFormula(int resultIndex, Set<Integer> indexes){

    }
}
