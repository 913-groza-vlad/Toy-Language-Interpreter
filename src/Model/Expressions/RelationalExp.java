package Model.Expressions;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIHeap;
import Model.Exceptions.ArithmException;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExp implements Exp {
    Exp exp1, exp2;
    String operator;

    public RelationalExp(Exp exp1, Exp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operator = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws MyException {
        Value val1 = exp1.eval(table, heap);
        Value val2;
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.eval(table, heap);
            if (val2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) val1;
                IntValue int2 = (IntValue) val2;
                int nr1 = int1.getVal();
                int nr2 = int2.getVal();
                if (operator.equals("<"))
                    return new BoolValue(nr1 < nr2);
                else if (operator.equals("<="))
                    return new BoolValue(nr1 <= nr2);
                else if (operator.equals("=="))
                    return new BoolValue(nr1 == nr2);
                else if (operator.equals("!="))
                    return new BoolValue(nr1 != nr2);
                else if (operator.equals(">"))
                    return new BoolValue(nr1 > nr2);
                else if (operator.equals(">="))
                    return new BoolValue(nr1 >= nr2);
                else
                    throw new ArithmException("Invalid operator");
            }
            else
                throw new TypeException("Second expression is not an int expression!");
        }
        else
            throw new TypeException("First expression is not an int expression!");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType()))
                return new BoolType();
            else
                throw new TypeException("Second expression is not an int expression!");
        }
        else
            throw new TypeException("First expression is not an int expression!");
    }

    @Override
    public String toString() {
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}
