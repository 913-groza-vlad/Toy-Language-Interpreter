package Model.Expressions;

import Model.Exceptions.*;
import Model.ADTstructures.MyIDictionary;
import Model.Values.*;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symTable) throws MyException{
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

}
