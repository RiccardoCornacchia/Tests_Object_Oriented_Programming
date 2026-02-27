package a02c.e1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ReplacersFactoryImpl implements ReplacersFactory {

    private <T> List<Integer> getIndexes(List<T> input, T t){
        return Stream.iterate(0, i -> i < input.size(), i -> i+1).filter(i -> input.get(i).equals(t)).toList();
    }

    @Override
    public <T> Replacer<T> noReplacement() {
        return (input,t) -> List.of();
    }

    @Override
    public <T> Replacer<T> duplicateFirst() {
        return (input, t) -> {
                List<List<T>> list = new ArrayList<>();
                List<T> l = new LinkedList<>(input);
                List<Integer> indexes = getIndexes(input, t);
                if(!indexes.isEmpty()){
                    l.add(indexes.getFirst(), t);
                    list.add(l);
                }
                return list;
        };
    }

    @Override
    public <T> Replacer<T> translateLastWith(List<T> target) {
        return (input, t) -> {
            List<List<T>> list = new ArrayList<>();
            List<T> l = new LinkedList<>(input);
            List<Integer> indexes = getIndexes(input, t);
            if(!indexes.isEmpty()){
                l.remove((int) indexes.getLast());
                l.addAll(indexes.getLast(), target);
                list.add(l);
            }
            return list;
        };
    }

    @Override
    public <T> Replacer<T> removeEach() {
        return (input, t) -> {
            List<List<T>> list = new ArrayList<>();
            List<T> l = new LinkedList<>(input);
            List<Integer> indexes = getIndexes(input, t);
            var iterator = indexes.listIterator();
            while(iterator.hasNext()){
                var temp = new LinkedList<>(l);
                temp.remove((int) iterator.next());
                list.add(temp);
            }
            return list;
        };
    }

    @Override
    public <T> Replacer<T> replaceEachFromSequence(List<T> sequence) {
        return (input, t) -> {
            List<List<T>> list = new ArrayList<>();
            List<T> l = new LinkedList<>(input);
            List<Integer> indexes = getIndexes(input, t);
            var iteratorL = indexes.listIterator();
            var iteratorS = sequence.listIterator();
            while(iteratorS.hasNext() && iteratorL.hasNext()){
                var temp = new LinkedList<>(l);
                int index = iteratorL.next();
                temp.remove(index);
                temp.add(index, iteratorS.next());
                list.add(temp);
            }
            return list;
        };
    }
}
