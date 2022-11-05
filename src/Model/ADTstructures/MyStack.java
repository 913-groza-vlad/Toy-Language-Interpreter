package Model.ADTstructures;

import Model.Statements.IStmt;

import java.util.Iterator;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T>{
    Stack<T> stack;

    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return stack.iterator();
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (T statement : stack)
            buff.append(statement.toString()).append("\n");
        return buff.toString();
    }

}
