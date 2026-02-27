package esPass2.p1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class InfiniteIteratorHelpersImpl implements InfiniteIteratorsHelpers{

    // private class InfiniteIteratorImpl<X> implements InfiniteIterator<X>{

    //     private X elem;

    //     public InfiniteIteratorImpl(X e){
    //         this.elem = e;
    //     }

    //     @Override
    //     public X nextElement() {
    //         return elem;
    //     }

    // }

    // @Override
    // public <X> InfiniteIterator<X> of(X x) {
    //     return new InfiniteIteratorImpl<>(x);
    // }

    // private class InfiniteIteratorImpl2<X> implements InfiniteIterator<X>{

    //     private List<X> elem;
    //     private int index;

    //     public InfiniteIteratorImpl2(List<X> e){
    //         this.elem = e;
    //         index = 0;
    //     }

    //     @Override
    //     public X nextElement() {
    //         X temp = this.elem.get(index);
    //         if(index + 1 < elem.size()){
    //             index++;
    //         }
    //         else{
    //             index = 0;
    //         }
    //         return temp;
    //     }

    // }

    // @Override
    // public <X> InfiniteIterator<X> cyclic(List<X> l) {
    //     return new InfiniteIteratorImpl2<X>(l);
    // }

    // private class InfiniteIteratorImpl3 implements InfiniteIterator<Integer>{

    //     private int elem;
    //     private int add;

    //     public InfiniteIteratorImpl3(int start, int inc){
    //         this.elem = start;
    //         this.add = inc;
    //     }

    //     @Override
    //     public Integer nextElement() {
    //         int temp = elem;
    //         this.elem += add;
    //         return temp;
    //     }

    // }

    // @Override
    // public InfiniteIterator<Integer> incrementing(int start, int increment) {
    //     return new InfiniteIteratorImpl3(start, increment);
    // }

    // private class InfiniteIteratorImpl4<X> implements InfiniteIterator<X>{

    //     private X elem;
    //     private boolean change;
    //     private InfiniteIterator<X> first;
    //     private InfiniteIterator<X> sec;

    //     public InfiniteIteratorImpl4(InfiniteIterator<X> i, InfiniteIterator<X> j){
    //         first = i;
    //         sec = j;
    //         elem = first.nextElement();
    //         change = false;
    //     }

    //     @Override
    //     public X nextElement() {
    //         X temp = elem;
    //         if(!change){
    //             elem = sec.nextElement();
    //             change = true;
    //         }
    //         else{
    //             elem = first.nextElement();
    //             change = false;
    //         }
    //         return temp;
    //     }

    // }

    // @Override
    // public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> i, InfiniteIterator<X> j) {
    //     return new InfiniteIteratorImpl4<>(i, j);
    // }

    // private class InfiniteIteratorImpl5<X> implements InfiniteIterator<List<X>>{

    //     private List<X> elem = new ArrayList<>();
    //     private InfiniteIterator<X> first;
    //     private int prox;
    //     private int index;

    //     public InfiniteIteratorImpl5(InfiniteIterator<X> i, int n){
    //         first = i;
    //         prox = n;
    //         index = 0;
    //     }

    //     @Override
    //     public List<X> nextElement() {           
    //         elem.add(first.nextElement());
    //         while(elem.size() < prox){
    //             elem.add(first.nextElement());
    //         }
    //         if(index == 0){
    //             index++;
    //             return elem.stream().toList();
    //         }
    //         return elem.stream().skip(elem.size() - prox).toList();
    //     }

    // }

    // @Override
    // public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> i, int n) {
    //     return new InfiniteIteratorImpl5<>(i, n);
    // }
    
    @Override
	public <X> InfiniteIterator<X> of(X x) {
		return () -> x;
	}

	@Override
	public <X> InfiniteIterator<X> cyclic(List<X> l) {
		return new InfiniteIterator<>() {
			LinkedList<X> ll = new LinkedList<>(l);
			@Override
			public X nextElement() {
				var e = ll.removeFirst();
				ll.addLast(e);
				return e;
			}			
		};
	}
	
	@Override
	public InfiniteIterator<Integer> incrementing(int start, int increment) {
		return new InfiniteIterator<>() {
			int state = start;
			@Override
			public Integer nextElement() {
				var itemp = state;
				state = state + increment;
				return itemp;
			}			
		};
	}

	@Override
	public <X> InfiniteIterator<X> alternating(InfiniteIterator<X> input1, InfiniteIterator<X> input2) {
		return new InfiniteIterator<X>() {
			private InfiniteIterator<X> i1 = input1;
			private InfiniteIterator<X> i2 = input2;
			@Override
			public X nextElement() {
				var itemp = i1;
				i1 = i2;
				i2 = itemp;
				return itemp.nextElement();
			}			
		};
	}

	@Override
	public <X> InfiniteIterator<List<X>> window(InfiniteIterator<X> s, int n) {
		return new InfiniteIterator<List<X>>() {
			LinkedList<X> cache = new LinkedList<>();
			@Override
			public List<X> nextElement() {
				cache.addLast(s.nextElement());
				while (cache.size()<n) {
					cache.addLast(s.nextElement());
                    //Stream<Integer> infinite = Stream.iterate(1, num -> num + 1);
				}
				return new LinkedList<>(cache.subList(cache.size()-n, cache.size()));
			}			
		};
	}
}
