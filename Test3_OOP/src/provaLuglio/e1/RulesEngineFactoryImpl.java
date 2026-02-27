package provaLuglio.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RulesEngineFactoryImpl implements RulesEngineFactory{

    //C'è MODO DI FARLO PIù COMPATTO CON LA SOLUZIONE DEL PROF MA è AI LIMITI DELL'IMPOSSIBILE

    public <T> List<T> replaceAtPosition(int index, List<T> source, List<T> news){
        List<T> list = new ArrayList<>(source);
        list.remove(index);
        var it = news.listIterator(news.size()); //così parte dal fondo
        while(it.hasPrevious()){
            list.add(index, it.previous());
        }
        return list;
    }

    @Override
    public <T> List<List<T>> applyRule(Pair<T, List<T>> rule, List<T> input) {
        return Stream.iterate(0, i -> i < input.size(), i -> i+1)
        .flatMap(i -> input.get(i).equals(rule.get1()) ? Stream.of(replaceAtPosition(i, input, rule.get2())) : Stream.empty())
        .toList();
    }

    @Override
    public <T> RulesEngine<T> singleRuleEngine(Pair<T, List<T>> rule) {
        return new RulesEngine<T>() {
            List<T> input;
            boolean foundSolution;
            @Override
            public void resetInput(List<T> input) {
                this.input = input;
                foundSolution = false;
            }

            @Override
            public boolean hasOtherSolutions() {
                if(applyRule(rule, input).isEmpty() && foundSolution){
                    return false;
                }
                else if(applyRule(rule, input).isEmpty()){
                    return true;
                }
                return true;
            }

            @Override
            public List<T> nextSolution() {
                List<T> l = input.stream().flatMap(el -> el.equals(rule.get1()) ? rule.get2().stream() : Stream.of(el)).toList();
                resetInput(l);
                foundSolution = true;
                return l;
            } 
        };
    }

    @Override
    public <T> RulesEngine<T> cascadingRulesEngine(Pair<T, List<T>> baseRule, Pair<T, List<T>> cascadeRule) {
        return new RulesEngine<T>() {
            List<T> input;
            @Override
            public void resetInput(List<T> input) {
                this.input = input;
            }

            @Override
            public boolean hasOtherSolutions() {
                return !applyRule(baseRule, input).isEmpty();
            }

            @Override
            public List<T> nextSolution() {
                List<T> l = input.stream().flatMap(el -> el.equals(baseRule.get1()) ? baseRule.get2().stream() : Stream.of(el)).toList();
                l = l.stream().flatMap(el -> el.equals(cascadeRule.get1()) ? cascadeRule.get2().stream() : Stream.of(el)).toList();
                resetInput(l);
                return l;
            }
            
        };
    }

    @Override
    public <T> RulesEngine<T> conflictingRulesEngine(Pair<T, List<T>> rule1, Pair<T, List<T>> rule2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conflictingRulesEngine'");
    }
    
}
