package Model.ADTstructures;

public interface MyIStack<T> extends Iterable<T> {
    void push(T v);
    T pop();
    boolean isEmpty();
}
