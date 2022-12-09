package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new StmtExecException("Variable is already declared");
        symTable.update(name, type.getDefaultValue());
        return null;
    }

    @Override
    public Object clone() {
        try {
            return (VarDeclStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new VarDeclStmt(name, type);
        }
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
