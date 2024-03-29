package Model.Expressions;

import Model.ADTstructures.MyIHeap;
import Model.Exceptions.MyException;
import Model.ADTstructures.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws MyException {
        return table.lookup(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
