package Model.Statements;

import Model.Exceptions.MyException;
import Model.ProgramState;

public interface IStmt {
    Object clone();
    ProgramState execute(ProgramState state) throws MyException;
}

