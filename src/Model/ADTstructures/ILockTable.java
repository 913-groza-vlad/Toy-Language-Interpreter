package Model.ADTstructures;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface ILockTable {

    int getFreeLocation();
    boolean isDefined(Integer pos);
    void update(Integer key, Integer value);
    Integer lookUp(Integer pos);

    Set<Integer> keySet();
    Map<Integer, Integer> getContent();
}
