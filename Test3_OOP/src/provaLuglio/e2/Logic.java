package provaLuglio.e2;

import java.util.List;
import java.util.Map;

public interface Logic {

    public Pair<Integer, Integer> nextHit(Pair<Integer, Integer> pos);

    public void hit(Pair<Integer, Integer> pos);

    public List<Pair<Integer, Integer>> getCorner(Pair<Integer, Integer> pos);

    public void clearMap();

    public Map<Pair<Integer, Integer>, Boolean> getMap();
}
