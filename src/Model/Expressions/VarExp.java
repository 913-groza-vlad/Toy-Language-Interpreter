package Model.Expressions;

import Model.Exceptions.MyException;
import Model.ADTstructures.MyIDictionary;
import Model.Values.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        return table.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
