package Model.Expressions;

import Model.ADTstructures.MyIHeap;
import Model.Exceptions.*;
import Model.ADTstructures.MyIDictionary;
import Model.Types.Type;
import Model.Values.*;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws MyException{
        return e;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }

}
