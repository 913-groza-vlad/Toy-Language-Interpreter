package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.FileException;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class OpenRFile implements IStmt {
    private Exp exp;

    public OpenRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        Value value = exp.eval(symTable);
        if (!value.getType().equals(new StringType()))
            throw new TypeException(value + " is not a string");
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        StringValue fileName = (StringValue) value;
        if (fileTable.isDefined(fileName.getVal()))
            throw new StmtExecException(fileName + " is already in the File Table");
        BufferedReader buffer;
        try {
            buffer = new BufferedReader(new FileReader(fileName.getVal()));
        }
        catch (IOException e) {
            throw new FileException("File not found or error on opening file " + fileName);
        }
        fileTable.update(fileName.getVal(), buffer);

        return state;
    }

    @Override
    public Object clone() {
        try {
            return (OpenRFile) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new OpenRFile(exp);
        }
    }

    public String toString() {
        return "Open Read File(" + exp + ")";
    }
}
