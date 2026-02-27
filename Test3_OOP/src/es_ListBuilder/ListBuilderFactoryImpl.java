package esPass;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListBuilderFactoryImpl implements ListBuilderFactory{

    private static class ListBuilderImpl<T> implements ListBuilder<T>{

        private List<T> initialList;

        //non capisco perchè funziona con Stream e non con List
        public ListBuilderImpl(Stream<T> l){
            this.initialList = l.toList();
        }

        @Override
        public ListBuilder<T> add(List<T> list) {
            return new ListBuilderImpl<>(Stream.concat(initialList.stream(), list.stream()));
        }

        @Override
        public List<T> build() {
            return this.initialList;
        }

        @Override
        public ListBuilder<T> concat(ListBuilder<T> lb) {  
            return new ListBuilderImpl<>(Stream.concat(initialList.stream(), lb.build().stream()));
        }

        @Override
        public ListBuilder<T> replaceAll(T t, ListBuilder<T> lb) {
            Stream<T> str;
            str = initialList.stream().flatMap(e -> e.equals(t) ? lb.build().stream() : Stream.of(e));
            return new ListBuilderImpl<>(str);
        }

        @Override
        public ListBuilder<T> reverse() {
            Stream<T> str;
            str = initialList.reversed().stream();
            return new ListBuilderImpl<>(str);
        }

    }

    @Override
    // il <T> davanti al tipo di ritorno del metodo indica che il metodo è generico
    public <T> ListBuilder<T> empty() {
        ListBuilder<T> listEmpty = new ListBuilderImpl<>(Stream.empty());
        return listEmpty;
    }

    @Override
    public <T> ListBuilder<T> fromElement(T t) {
        List<T> listElem = new ArrayList<>();
        listElem.add(t);
        return new ListBuilderImpl<>(listElem.stream());
    }

    @Override
    public <T> ListBuilder<T> fromList(List<T> list) {
        List<T> listLi = new ArrayList<>();
        listLi.addAll(list);
        return new ListBuilderImpl<>(listLi.stream());
    }

    @Override
    public <T> ListBuilder<T> join(T start, T stop, List<ListBuilder<T>> builderList) {
        List<T> joinList = new ArrayList<>();
        joinList.add(start);
        joinList.addAll(builderList.stream().flatMap(le -> le.build().stream()).toList());
        joinList.add(stop);
        return new ListBuilderImpl<>(joinList.stream());
    }
    
}
