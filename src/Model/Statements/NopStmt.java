package Model.Statements;

import Model.Exceptions.MyException;
import Model.ProgramState;

public class NopStmt implements IStmt {

    public ProgramState execute(ProgramState state) throws MyException {
        return state;
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
    public String toString() {
        return "NopStatement";
    }
}
