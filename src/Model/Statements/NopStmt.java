package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.Type;

public class NopStmt implements IStmt {

    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public Object clone() {
        try {
            return (NopStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new NopStmt();
        }
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
