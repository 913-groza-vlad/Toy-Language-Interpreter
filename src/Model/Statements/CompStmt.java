package Model.Statements;

import Model.Exceptions.MyException;
import Model.ADTstructures.MyIStack;
import Model.ProgramState;

public class CompStmt implements IStmt {
    IStmt first, second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        stk.push(second);
        stk.push(first);
        return state;
    }

    @Override
    public Object clone() {
        try {
            return (CompStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new CompStmt(first, second);
        }
    }
}
