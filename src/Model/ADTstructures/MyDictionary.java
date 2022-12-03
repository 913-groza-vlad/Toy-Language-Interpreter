package Model.ADTstructures;

import Model.Values.Value;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<T, T1> implements MyIDictionary<T, T1> {
    Map<T, T1> dict;

    public MyDictionary() {
        dict = new HashMap<>();
    }

    @Override
    public boolean isDefined(T id) {
        return dict.containsKey(id);
    }

    @Override
    public T1 lookup(T id) {
        return dict.get(id);
    }

    @Override
    public void update(T id, T1 val) {
        dict.put(id, val);
    }

    public Set<T> keys() {
        return dict.keySet();
    }

    /*@Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (T id : this.keys())
            buff.append("\t   ").append(id).append(" -> ").append(this.lookup(id)).append("\n");
        return buff.toString();
    } */

    @Override
    public void delete(T id) {
        dict.remove(id);
    }

    @Override
    public Map<T, T1> getContent() {
        return dict;
    }
}
