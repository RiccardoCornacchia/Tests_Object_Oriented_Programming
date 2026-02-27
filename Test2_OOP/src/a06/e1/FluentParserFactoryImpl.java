package a06.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class FluentParserFactoryImpl implements FluentParserFactory{


    private static class FluentParserImplNaturals implements FluentParser<Integer>{

        private final int next;

        public FluentParserImplNaturals(int next){
            this.next = next;
        }

        @Override
        public FluentParser<Integer> accept(Integer value) {
            if(value == next){
                return new FluentParserImplNaturals(next + 1);
            }
            throw new IllegalStateException();
        }

    }

    @Override
    public FluentParser<Integer> naturals() {
        final FluentParser<Integer> parser = new FluentParserImplNaturals(0);
        return parser;
    }

    private static class FluentParserImplList implements FluentParser<List<Integer>>{

        private final List<Integer> list;
        private int next;

        public FluentParserImplList(List<Integer> list, int next){
            this.list = list;
            this.next = next;
        }

        @Override
        public FluentParser<List<Integer>> accept(List<Integer> value) {
            if(value.equals(list)){
                list.add(next);
                return new FluentParserImplList(list, next + 1);
            }
            throw new IllegalStateException();
        }

    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        final FluentParser<List<Integer>> parser = new FluentParserImplList(new ArrayList<>(), 0);
        return parser;
    }

    private static class FluentParserImplRep implements FluentParser<Integer>{

        private final int next;
        private int max;

        public FluentParserImplRep(int next, int max){
            this.next = next;
            this.max = max;
        }

        @Override
        public FluentParser<Integer> accept(Integer value) {
            if(value == next){
                if(value == max){
                    return new FluentParserImplRep(0, max + 1);
                }
                return new FluentParserImplRep(next + 1, max);
            }
            throw new IllegalStateException();
        }

    }

    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        final FluentParser<Integer> parser = new FluentParserImplRep(0, 0);
        return parser;
    }

    private static class FluentParserImplStr implements FluentParser<String>{

        private final String next;
        private int max;

        public FluentParserImplStr(String next, int max){
            this.next = next;
            this.max = max;
        }

        @Override
        public FluentParser<String> accept(String value) {
            if(value.equals(next)){
                if(value.length() == max){
                    max++;
                    return new FluentParserImplStr("a", max);
                }
                return new FluentParserImplStr(next + "a", max);
            }
            throw new IllegalStateException();
        }

    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        final FluentParser<String> parser = new FluentParserImplStr("a", 1);
        return parser;
    }

    public static class FluentParserImplPair implements FluentParser<Pair<Integer, List<String>>>{

        private Pair<Integer, List<String>> pair;
        private String str;
        private UnaryOperator<Integer> op;
        private int index0;

        public FluentParserImplPair(Pair<Integer, List<String>> pair, int i0, UnaryOperator<Integer> op, String str){
            this.pair = pair;
            this.str = str;
            this.op = op;
            this.index0 = i0;
        }

        @Override
        public FluentParser<Pair<Integer, List<String>>> accept(Pair<Integer, List<String>> value) {
            if(value.equals(pair)){
                int nuovoI = op.apply(index0);
                List<String> list = new ArrayList<>();
                for(int i = 0; i < nuovoI; i++){
                    list.add(str);
                }
                pair = new Pair<>(nuovoI, list);
                return new FluentParserImplPair(pair, nuovoI, op, str);
            }
            throw new IllegalStateException();
        }

    }
    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < i0; i++){
            list.add(s);
        }
        FluentParser<Pair<Integer, List<String>>> parser = new FluentParserImplPair(new Pair<>(i0, list), i0, op, s);
        return parser;
    }

}
