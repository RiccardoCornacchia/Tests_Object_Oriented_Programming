package esPass3.p1;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory{

    private static <X, Y, Z> ZipCombiner<X, Y, Z> generic(BiFunction<X, Iterator<Y>, Stream<Z>> extractor){
        return new ZipCombiner<X,Y,Z>() {

            @Override
            public List<Z> zipCombine(List<X> l1, List<Y> l2) {
                var it2 = l2.iterator();
                return l1.stream().flatMap(x -> extractor.apply(x, it2)).toList();
            }
        };
    }

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
        return generic((x, it) -> Stream.of(new Pair<>(x, it.next())));
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return generic((x, it) -> Stream.of(mapper.apply(it.next())).filter(v -> predicate.test(x)));
    }

    @Override
    public <X> ZipCombiner<Integer, X, List<X>> taker() {
        return generic((x, it) -> Stream.of(Stream.iterate(0, n -> n+1).limit(x).map(n -> it.next()).toList()));
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return generic((x, it) -> Stream.of(new Pair<>(x, (int)Stream.iterate(it.next(), n -> n != 0, n -> it.next()).count())));
    }

    // private class ZipClassic<X,Y,Z> implements ZipCombiner<X,Y,Pair<X,Y>>{

    //     private List<Pair<X,Y>> list = new ArrayList<>();

    //     @Override
    //     public List<Pair<X,Y>> zipCombine(List<X> l1, List<Y> l2) {
    //         if(l1.size() < l2.size()){
    //             for(int i = 0; i < l1.size(); i++){
    //                 Pair<X,Y> p = new Pair<>(l1.get(i), l2.get(i));
    //                 list.add(p);
    //             }
    //         }
    //         else{
    //             for(int i = 0; i < l2.size(); i++){
    //                 Pair<X,Y> p = new Pair<>(l1.get(i), l2.get(i));
    //                 list.add(p);
    //             }
    //         }
    //         return list;
    //     }

    // }
    // @Override
    // public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {
    //     return new ZipClassic<>();
    // }

    // private class ZipFilter<X,Y,Z> implements ZipCombiner<X,Y,Z>{

    //     private List<Z> list = new ArrayList<>();
    //     Predicate<X> predicate;
    //     Function<Y, Z> sign;

    //     public ZipFilter(Predicate<X> predicate, Function<Y, Z> mapper){
    //         this.predicate = predicate;
    //         this.sign = mapper;
    //     }

    //     @Override
    //     public List<Z> zipCombine(List<X> l1, List<Y> l2) {
    //         for(int i = 0; i < l1.size(); i++){
    //             if(predicate.test(l1.get(i))){
    //                 list.add(sign.apply(l2.get(i)));
    //             }
    //         }
    //         return list;
    //     }

    // }
    // @Override
    // public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
    //     return new ZipFilter<>(predicate, mapper);
    // }

    // private class ZipRepeat<X,Y,Z> implements ZipCombiner<Integer, Y, List<Y>>{

    //     private List<List<Y>> list = new ArrayList<>();

    //     @Override
    //     public List<List<Y>> zipCombine(List<Integer> l1, List<Y> l2) {
    //         List<Y> piece = new ArrayList<>();
    //         piece.addAll(l2);
    //         int times = 0;
    //         for(int i = 0; i < l1.size(); i++){
    //             list.add(piece.stream().toList().subList(times, times+l1.get(i)));
    //             times += l1.get(i);
    //         }
    //         return list;
    //     }

    // }
    // @Override
    // public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
    //     return new ZipRepeat<>();
    // }

    // private class ZipToZero<X,Y,Z> implements ZipCombiner<X, Integer, Pair<X, Integer>>{

    //     private List<Pair<X, Integer>> list = new ArrayList<>();

    //     @Override
    //     public List<Pair<X, Integer>> zipCombine(List<X> l1, List<Integer> l2) {
    //         int ind = 0;
    //         for(int i = 0; i < l1.size(); i++){
    //             int count = 0;
    //             while(l2.get(ind) != 0){
    //                 count++;
    //                 ind++;
    //             }
    //             ind++;
    //             Pair<X, Integer> p = new Pair<X,Integer>(l1.get(i), count);
    //             list.add(p);
    //         }

    //         return list;
    //     }

    // }
    // @Override
    // public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
    //     return new ZipToZero<>();
    // }
    
}
