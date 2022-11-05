package Model.ADTstructures;

import Model.Values.Value;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    List<T> list;

    public MyList() {
        list = new LinkedList<>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (T output : list)
            buff.append("\t\t").append(output.toString()).append("\n");
        return buff.toString();
    }
}


