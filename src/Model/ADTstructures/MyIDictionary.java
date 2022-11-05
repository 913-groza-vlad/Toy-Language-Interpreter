package Model.ADTstructures;

import Model.Values.*;

import java.util.Set;


public interface MyIDictionary<T, T1> {
    boolean isDefined(T id);

    T1 lookup(T id);

    void update(T id, T1 val);

    Set<T> keys();
}