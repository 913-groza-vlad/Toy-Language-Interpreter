package Model.ADTstructures;

import Model.Values.Value;

import java.util.Set;

public interface MyIHeap {
    boolean isDefined(Integer addr);
    Value lookup(Integer addr);
    void update(Integer addr, Value content);
    Set<Integer> keys();
    Integer add(Value content);
    Integer getFreeLocation();
}
