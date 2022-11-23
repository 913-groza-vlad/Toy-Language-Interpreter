package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIHeap;
import Model.Exceptions.*;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile implements IStmt {

    private Exp exp;
    private String varName;

    public ReadFile(Exp exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heapTable = state.getHeap();
        if (!symTable.isDefined(varName))
            throw new StmtExecException(varName + " is not defined yet");
        if(!symTable.lookup(varName).getType().equals(new IntType()))
            throw new TypeException(varName + " is not of type int");
        Value value = exp.eval(symTable, heapTable);
        if (!value.getType().equals(new StringType()))
            throw new TypeException(value + " is not a string");
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        StringValue fileName = (StringValue) value;
        if (!fileTable.isDefined(fileName.getVal()))
            throw new StmtExecException("No entry associated to this value in file table");
        BufferedReader buffer = fileTable.lookup(fileName.getVal());
        try {
            String stringNr = buffer.readLine();
            int val = 0;
            if (stringNr != null)
                val = Integer.parseInt(stringNr);
            symTable.update(varName, new IntValue(val));
        }
        catch (IOException e) {
            throw new FileException("Error on reading from file " + fileName);
        }
        return state;
    }

    @Override
    public Object clone() {
        try {
            return (ReadFile) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new ReadFile(exp, varName);
        }
    }

    public String toString() {
        return String.format("ReadFile(%s, %s)" ,exp, varName);
    }
}
