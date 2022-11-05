package Model;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIList;
import Model.ADTstructures.MyIStack;
import Model.Statements.*;
import Model.Values.Value;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;

    public ProgramState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTbl, MyIList<Value> out, IStmt prg) {
        exeStack = stack;
        symTable = symTbl;
        this.out = out;
        originalProgram = (IStmt) prg.clone();
        stack.push(prg);
    }

    public MyIStack<IStmt> getStk() {
        return this.exeStack;
    }

    public MyIDictionary<String,Value> getSymTable() {
        return this.symTable;
    }

    public MyIList<Value> getOut() {
        return this.out;
    }

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    @Override
    public String toString() {
        return "--------------------\n\t- EXE STACK -\n" +  exeStack.toString() + "--------------------\n\t- SYM TABLE -\n" + symTable.toString() + "--------------------\n\t  - OUT -\n" + out.toString() + "--------------------\n";
    }
}