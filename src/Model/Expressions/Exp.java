package Model.Expressions;

import Model.Exceptions.MyException;
import Model.ADTstructures.MyIDictionary;
import Model.Values.*;

public interface Exp {
    Value eval(MyIDictionary<String, Value> symTable) throws MyException;
}

