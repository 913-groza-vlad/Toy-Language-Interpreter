package Model;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIList;
import Model.ADTstructures.MyIStack;
import Model.Statements.*;
import Model.Values.Value;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symTable;
    MyIList<Value> out;
    IStmt originalProgram;
    MyIDictionary<String, BufferedReader> fileTable;

    public ProgramState(MyIStack<IStmt> stack, MyIDictionary<String, Value> symTbl, MyIList<Value> out, MyIDictionary<String, BufferedReader> fileTable, IStmt prg) {
        exeStack = stack;
        symTable = symTbl;
        this.out = out;
        this.fileTable = fileTable;
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

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public String symTableToString() {
        StringBuilder buff = new StringBuilder();
        for (String id : symTable.keys())
            buff.append("\t   ").append(id).append(" -> ").append(symTable.lookup(id)).append("\n");
        return buff.toString();
    }
    public String fileTableToString() {
        StringBuilder buff = new StringBuilder();
        for (String id : fileTable.keys())
            buff.append("\t").append(id).append("\n");
        return buff.toString();
    }
    @Override
    public String toString() {
        return "--------------------\n\t- EXE STACK -\n" +  exeStack.toString() + "--------------------\n\t- SYM TABLE -\n" + symTableToString() + "--------------------\n\t  - OUT -\n" + out.toString() + "--------------------\n    - FILE TABLE -\n" + fileTableToString() + "--------------------\n";
    }
}
