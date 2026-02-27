package a02b.e2;

import java.util.List;

public interface Logic {
    
    public boolean hit(Integer x, Integer y);

    public boolean check();

    public void clean();

    public List<Pair<Integer,Integer>> toDisable();
}
