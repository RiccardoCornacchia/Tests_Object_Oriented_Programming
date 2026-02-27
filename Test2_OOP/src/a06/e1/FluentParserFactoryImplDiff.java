package a06.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluentParserFactoryImplDiff implements FluentParserFactory {

    private <T> FluentParser<T> fromIterator(Iterator<T> iterator){
        return t -> {
            if (iterator.hasNext() && iterator.next().equals(t)){ 
                return fromIterator(iterator);
            } else {
                throw new IllegalStateException();
            }
        };
    }

    @Override
    public FluentParser<Integer> naturals() {
        return fromIterator(IntStream.iterate(0, i->i+1).iterator());
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return fromIterator(Stream.iterate(0, i->i+1)
                .map(i -> IntStream.range(0,i).mapToObj(j->j).collect(Collectors.toList()))
                .iterator());
    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return fromIterator(Stream.iterate(1, i->i+1) // 0,1,2,3,....
                .flatMap(i -> Stream.iterate(0, j->j+1).limit(i))
                .iterator());
                /*O ANCHE
                 *FromIterator(Stream.iterate(0, i->i+1)
                   .flatMap(i -> IntStream.range(0,i).mapToObj(j->j))
                   .iterator());
                 */
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return fromIterator(Stream.iterate(1, i->i+1) // 1,2,3,....
                .flatMap(i -> Stream.iterate(1, j->j+1).limit(i)) // 1,1,2,1,2,3,...
                .map(j -> Stream.generate(()->s).limit(j).reduce(String::concat).get())
                .iterator());
                //prende una sequenza da 1 a infinito -> per ogni elemento cambia la sequenza aggiungendo un nuovo stream, composto dalla sequenza
                //da 1 all'elemento in questione -> per ogni elemento del nuovo stream genera tante s quanto l'elemento e le unisce con
                //concat ottenendo uno stream di string, ogni string sono tante s quanto il numero dello stream precedente ->
                //crea l'iteratore

                //quindi il flatMap a partire da un elemento lo modifica in uno stream (1 diventa 1. 2 diventa 1,2. 3 diventa 1,2,3)
                //il map a partire da un elemento lo modifica in un altro elemento (1 diventa "s". 2 diventa "ss". 3 diventa "sss")
    }

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return fromIterator(Stream.iterate(i0, op)
                .map(i -> new Pair<>(i, Stream.generate(()->s).limit(i).collect(Collectors.toList())))
                .iterator()); 
    }
}
