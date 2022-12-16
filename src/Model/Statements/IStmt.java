package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ProgramState;
import Model.Types.Type;

public interface IStmt {
    Object clone();
    ProgramState execute(ProgramState state) throws MyException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}

