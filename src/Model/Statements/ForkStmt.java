package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIHeap;
import Model.ADTstructures.MyIStack;
import Model.ADTstructures.MyStack;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Values.RefValue;
import Model.Values.Value;

public class ForkStmt implements IStmt {

    IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStmt> newExeStack = new MyStack<>();
        ProgramState newPrgState = new ProgramState(newExeStack, state.getSymTable().cloneMap(), state.getOut(), state.getFileTable(), state.getHeap(), statement);
        newPrgState.setPrgStateId();
        return newPrgState;
    }
    public String toString() {
        return " Fork(" + statement.toString() + ")";
    }

    @Override
    public Object clone() {
        try {
            return (ForkStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new ForkStmt(statement);
        }
    }
}
