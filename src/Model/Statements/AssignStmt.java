package Model.Statements;

import Model.*;
import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIStack;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp exp;

    public AssignStmt(String id, Exp exp) {
        this.id = id;
        this.exp = exp;
    }

    public String toString() {
        return id + " = " + exp.toString();
    }


    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(id)) {
            Value val = exp.eval(symTable, state.getHeap());
            Type typeId = (symTable.lookup(id)).getType();
            if ((val.getType()).equals(typeId))
                symTable.update(id, val);
            else
                throw new MyException("Declared type of variable " + id + " and type of the assigned expression do not match");
        } else
            throw new StmtExecException("The used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typeCheck(typeEnv);
        if (!typeVar.equals(typeExp))
            throw new TypeException("Assignment: right hand side and left hand side have different types");
        return typeEnv;
    }

    @Override
    public Object clone() {
        try {
            return (AssignStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new AssignStmt(id, exp);
        }
    }
}
