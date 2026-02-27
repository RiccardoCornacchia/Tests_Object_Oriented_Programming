package a02b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> fromNonEmptyIterator(Iterator<X> iterator) {
        return new Cursor<X>() {

            X element = iterator.next();

            @Override
            public X getElement() {
                return element;
            }

            @Override
            public boolean advance() {
                if (iterator.hasNext()){
                    element = iterator.next();
                    return true;
                }
                return false;
            }
        };
    }
    
    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return this.fromNonEmptyIterator(list.iterator());
    }

    @Override
    public Cursor<Integer> naturals() {
        return this.fromNonEmptyIterator(Stream.iterate(0, x->x+1).iterator());
    }


    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return new Cursor<>(){

            int count = 0;

            @Override
            public X getElement() {
                return input.getElement();
            }

            @Override
            public boolean advance() {
                if (count < max-1){
                    count++;
                    return input.advance();
                }
                return false;
            }
        };
    }

    @Override
    public <X> void forEach(Cursor<X> cursor, Consumer<X> consumer) {
        do {
            consumer.accept(cursor.getElement());
        } while (cursor.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> cursor, int max) {
        var list = new ArrayList<X>();
        forEach(take(cursor, max), el -> list.add(el));
        return list;
    }
}

    // private class CursorNonEmpty<X> implements Cursor<X>{

    //     private List<X> list;
    //     private int index;

    //     public CursorNonEmpty(List<X> list){
    //         this.list = list;
    //         index = 0;
    //     }

    //     @Override
    //     public X getElement() {
    //         return this.list.get(index);
    //     }

    //     @Override
    //     public boolean advance() {
    //         if(index + 1 < this.list.size()){
    //             index++;
    //             return true;
    //         }
    //         return false;
    //     }

    // }

    // @Override
    // public <X> Cursor<X> fromNonEmptyList(List<X> list) {
    //     return new CursorNonEmpty<>(list);
    // }

    // private class CursorNat implements Cursor<Integer>{

    //     public static int MAXINT = 1000;
    //     private List<Integer> list = new ArrayList<>();
    //     private int index;

    //     public CursorNat(){
    //         for(int i = 0; i < MAXINT; i++){
    //             this.list.add(i);
    //         }
    //         index = 0;
    //     }

    //     @Override
    //     public Integer getElement() {
    //         return this.list.get(index);
    //     }

    //     @Override
    //     public boolean advance() {
    //         if(index + 1 < list.size()){
    //             index++;
    //             return true;
    //         }
    //         return false;
    //     }

    // }

    // @Override
    // public Cursor<Integer> naturals() {
    //     return new CursorNat();
    // }

    // private class CursorTake<X> implements Cursor<X>{

    //     private List<X> list = new ArrayList<>();
    //     private int index;

    //     public CursorTake(Cursor<X> input, int max){
    //         index = 0;
    //         int i = 0;
    //         do{
    //             list.add(input.getElement());
    //             i++;
    //         }while(i < max && input.advance());
    //     }

    //     @Override
    //     public X getElement() {
    //         return this.list.get(index);
    //     }

    //     @Override
    //     public boolean advance() {
    //         if(index + 1 < list.size()){
    //             index++;
    //             return true;
    //         }
    //         return false;
    //     }

    // }

    // @Override
    // public <X> Cursor<X> take(Cursor<X> input, int max) {
    //     return new CursorTake<>(input, max);
    // }

    // @Override
    // public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
    //     X s;
    //     do{
    //         s = input.getElement();
    //         consumer.accept(s);
    //     }while(input.advance());
    // }

    // @Override
    // public <X> List<X> toList(Cursor<X> input, int max) {
    //     List<X> list = new ArrayList<>();
    //     int i = 0;
    //     do{
    //         list.add(input.getElement());
    //         i++;
    //     }while(i < max && input.advance());

    //     return list;
    // }
