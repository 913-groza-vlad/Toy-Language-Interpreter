package Model.Statements;

import Model.Exceptions.StmtExecException;
import Model.Expressions.Exp;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Values.*;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS, elseS;

    public IfStmt(Exp exp, IStmt t, IStmt e) {
        this.exp = exp;
        this.thenS = t;
        this.elseS = e;
    }

    public String toString() {
        return "(IF (" + exp.toString() + ") THEN (" + thenS.toString() + ") ELSE (" + elseS.toString() + "))";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        Value value = exp.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new BoolType())) {
            BoolValue cond = (BoolValue) value;
            if (cond.getVal())
                state.getStk().push(thenS);
            else
                state.getStk().push(elseS);
        }
        else
            throw new StmtExecException("Conditional expression is not boolean");
        return null;
    }

    @Override
    public Object clone() {
        try {
            return (IfStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new IfStmt(exp, thenS, elseS);
        }
    }
}
