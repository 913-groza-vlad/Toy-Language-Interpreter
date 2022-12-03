package Model.ADTstructures;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap implements MyIHeap{
    Map<Integer, Value> heap;
    Integer freeLocation;

    public MyHeap() {
        this.heap = new HashMap<>();
        freeLocation = 1;
    }

    public Integer newValue() {
        if (heap.containsKey(freeLocation))
            freeLocation++;
        return freeLocation;
    }

    @Override
    public boolean isDefined(Integer addr) {
        return heap.containsKey(addr);
    }

    @Override
    public Value lookup(Integer addr) {
        return heap.get(addr);
    }

    @Override
    public void update(Integer addr, Value content) {
        heap.put(addr, content);
    }

    public Set<Integer> keys() {
        return heap.keySet();
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public Integer add(Value content) {
        heap.put(freeLocation, content);
        Integer address = freeLocation;
        freeLocation = newValue();
        return address;
    }

    @Override
    public void setContent(Map<Integer, Value> collector) {
        heap.clear();
        for (Integer k : collector.keySet())
            heap.put(k, collector.get(k));
    }

    @Override
    public Map<Integer, Value> getContent() {
        return heap;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (Integer id : heap.keySet())
            buff.append("\t   ").append(id).append(" -> ").append(lookup(id)).append("\n");
        return buff.toString();
    }

}
