package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ADTstructures.MyIStack;
import Model.Exceptions.TypeException;
import Model.ProgramState;
import Model.Types.Type;

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
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnv));
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

    public IStmt getFirst() {
        return this.first;
    }

    public IStmt getSecond() {
        return this.second;
    }
}
