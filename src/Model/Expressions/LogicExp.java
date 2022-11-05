package Model.Expressions;

import Model.Exceptions.*;
import Model.ADTstructures.MyIDictionary;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp{
    Exp ex1;
    Exp ex2;
    int op; // 1 - and | 2 - or

    public LogicExp(Exp expression1, Exp expression2, int operator) {
        ex1 = expression1;
        ex2 = expression2;
        op = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = ex1.eval(table);
        if (v1.getType().equals(new BoolType())) {
            v2 = ex2.eval(table);
            if (v2.getType().equals(new BoolType())) {
                BoolValue bool1 = (BoolValue) v1;
                BoolValue bool2 = (BoolValue) v2;
                boolean b1 = bool1.getVal();
                boolean b2 = bool2.getVal();
                if (op == 1)
                    return new BoolValue(b1 && b2);
                else if (op == 2)
                    return new BoolValue(b1 || b2);
                else
                    throw new ArithmException("Invalid operator");
            }
            else
                throw new TypeException("Second operand is not a boolean");
        }
        else
            throw new TypeException("First operand is not a boolean");
    }

    @Override
    public String toString() {
        return ex1.toString() + " " + op + " "  + ex2.toString();
    }
}
