package es1_24giugno.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WindowingFactoryImpl implements WindowingFactory{

    @Override
    public <X> Windowing<X, X> trivial() {
        return (input) -> Optional.of(input);
    }

    private <X> void changeList(List<X> list, int size, X input){
        if(list.size()==size){
            list.remove(0);
        }
        list.add(input);
    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        List<X> list = new ArrayList<>();
        return (input) -> {
            changeList(list, 2, input);
            return list.size() == 2 ? Optional.of(new Pair<X,X>(list.get(0), list.get(1))) : Optional.empty();
        };
    }

    @Override
    public Windowing<Integer, Integer> sumLastFour() {
        List<Integer> list = new ArrayList<>();
        return (input) -> {
            changeList(list, 4, input);
            return list.size() == 4 ? list.stream().reduce((a,b) -> a+b) : Optional.empty();
        };
    }

    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        List<X> list = new ArrayList<>();
        return (input) -> {
            changeList(list, n, input);
            return list.size() == n ? Optional.of(list.stream().collect(Collectors.toList())) : Optional.empty();
        };
    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        List<Integer> list = new ArrayList<>();
        return (input) -> {
            list.add(input);
            int i = 1;
            while(list.reversed().subList(0, i).stream().reduce((a,b) -> a+b).get() < n){
                i++;
                if((i-1)==list.size()){
                    return Optional.empty();
                }
            }
            return Optional.of(list.reversed().subList(0, i).reversed());
        };
    }
}
