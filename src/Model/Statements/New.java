package Model.Statements;


import Model.Exceptions.MyException;
import Model.Exceptions.StmtExecException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class New implements IStmt{

    Exp exp;
    String varName;

    public New(String varName, Exp exp) {
        this.exp = exp;
        this.varName = varName;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        Value value = exp.eval(state.getSymTable());
        if (!state.getSymTable().isDefined(varName))
            throw new StmtExecException(varName + " is not defined in the SymTable");
        Value varValue = state.getSymTable().lookup(varName);
        if (!(varValue.getType() instanceof RefType))
            throw new TypeException(varName + " type is not RefType");
        Type locationType = ((RefValue)varValue).getLocationType();
        if (!locationType.equals(value.getType()))
            throw new TypeException(varName + " value has not the same type as expression value ");
        Integer freeLocation = state.getHeap().add(value);
        state.getSymTable().update(varName, new RefValue(freeLocation, locationType));

        return state;
    }

    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public Object clone() {
        try {
            return (New) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new New(varName, exp);
        }
    }

}
