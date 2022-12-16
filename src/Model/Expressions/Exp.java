package Model.Expressions;

import Model.ADTstructures.MyIHeap;
import Model.Exceptions.MyException;
import Model.ADTstructures.MyIDictionary;
import Model.Types.Type;
import Model.Values.*;

public interface Exp {
    Value eval(MyIDictionary<String, Value> symTable, MyIHeap heap) throws MyException;
    Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;

}

