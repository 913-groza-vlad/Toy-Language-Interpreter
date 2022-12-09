package Model.Statements;

import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt {

    Exp exp;
    IStmt inWhileStmt;

    public WhileStmt(Exp exp, IStmt inWhileStmt) {
        this.exp = exp;
        this.inWhileStmt = inWhileStmt;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new BoolType()))
            throw new TypeException("While expression is not boolean");
        BoolValue boolVal = (BoolValue) value;
        if (boolVal.getVal()) {
            state.getStk().push(this);
            state.getStk().push(inWhileStmt);
        }
        return null;
    }

    public String toString() {
        return "(WHILE (" + exp.toString() + ") " + inWhileStmt.toString() + ")";
    }

    @Override
    public Object clone() {
        try {
            return (WhileStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new WhileStmt(exp, inWhileStmt);
        }
    }
}
