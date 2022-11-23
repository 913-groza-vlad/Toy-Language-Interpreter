package Model.Statements;

import Model.Expressions.Exp;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Values.Value;

public class PrintStmt implements IStmt {
    Exp exp;

    public PrintStmt(Exp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "print (" + exp.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        // state.getStk().pop();
        Value val = exp.eval(state.getSymTable(), state.getHeap());
        state.getOut().add(val);
        return state;
    }

    @Override
    public Object clone() {
        try {
            return (PrintStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new PrintStmt(exp);
        }
    }
}
