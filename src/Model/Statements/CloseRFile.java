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
import java.io.IOException;

public class CloseRFile implements IStmt {
    private Exp exp;

    public CloseRFile(Exp exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        Value value = exp.eval(symTable);
        if (!value.getType().equals(new StringType()))
            throw new TypeException(value + " has not type string");
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        StringValue fileName = (StringValue) value;
        if (!fileTable.isDefined(fileName.getVal()))
            throw new StmtExecException(fileName + " is not in the File Table");
        BufferedReader buffer = fileTable.lookup(fileName.getVal());
        try {
            buffer.close();
        }
        catch (IOException e) {
            throw new FileException("Error on closing file" + fileName);
        }
        fileTable.delete(fileName.getVal());

        return state;
    }

    @Override
    public Object clone() {
        try {
            return (CloseRFile) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new CloseRFile(exp);
        }
    }

    public String toString() {
        return "Close Read File(" + exp + ")";
    }

}
