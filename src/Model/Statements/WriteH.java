package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class WriteH implements IStmt {
    Exp exp;
    String varName;

    public WriteH(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIHeap heapTable = state.getHeap();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new MyException(varName + " is not defined int the symTable");
        Value variableValue = symTable.lookup(varName);
        if (!(variableValue instanceof RefValue refVarValue))
            throw new TypeException("Variable value is not a RefValue");
        if (!heapTable.isDefined(refVarValue.getAddress()))
            throw new StmtExecException(String.format("Address %d is not a key in the Heap", refVarValue.getAddress()));
        Value expValue = exp.eval(symTable, heapTable);
        if (!expValue.getType().equals(refVarValue.getLocationType()))
            throw new TypeException("Expression type is not equal with the location type of " + varName);
        heapTable.update(refVarValue.getAddress(), expValue);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = exp.typeCheck(typeEnv);
        if (!typeEnv.lookup(varName).equals(new RefType(typeExp)))
            throw new TypeException("Right hand side and left hand side of the WriteHeap operation have different types");
        return typeEnv;
    }

    public String toString() {
        return "WriteHeap(" + varName + " -> " + exp.toString() + ")";
    }

    @Override
    public Object clone() {
        try {
            return (WriteH) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new WriteH(varName, exp);
        }
    }
}
