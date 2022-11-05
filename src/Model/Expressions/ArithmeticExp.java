package Model.Expressions;

import Model.Exceptions.*;
import Model.ADTstructures.MyIDictionary;
import Model.Types.IntType;
import Model.Values.*;

public class ArithmeticExp implements Exp {
    Exp ex1;
    Exp ex2;
    String op; // + - plus | - - minus | * - star | / - divide

    public ArithmeticExp(Exp expression1, Exp expression2, String operator) {
        ex1 = expression1;
        ex2 = expression2;
        op = operator;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table) throws MyException {
        Value v1, v2;
        v1 = ex1.eval(table);
        if (v1.getType().equals(new IntType())) {
            v2 = ex2.eval(table);
            if (v2.getType().equals(new IntType())) {
                IntValue int1 = (IntValue) v1;
                IntValue int2 = (IntValue) v2;
                int n1 = int1.getVal();
                int n2 = int2.getVal();
                if (op.equals("+"))
                    return new IntValue(n1 + n2);
                else if (op.equals("-"))
                    return new IntValue(n1 - n2);
                else if (op.equals("*"))
                    return new IntValue(n1 * n2);
                else if (op.equals("/")) {
                    if (n2 == 0)
                        throw new ArithmException("Division by zero");
                    return new IntValue(n1 / n2);
                } else
                    throw new ArithmException("Invalid operator");
            } else
                throw new TypeException("Second operand is not an integer!");
        } else
            throw new TypeException("First operand is not an integer!");
    }

    @Override
    public String toString() {
        return ex1.toString() + " " + op + " "  + ex2.toString();
    }
}
