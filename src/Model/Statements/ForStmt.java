package Model.Statements;

import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.Expressions.Exp;
import Model.Expressions.RelationalExp;
import Model.Expressions.VarExp;
import Model.ProgramState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;

public class ForStmt implements IStmt {

    private String varName;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt inForStmt;

    public ForStmt(String varName, Exp exp1, Exp exp2, Exp exp3, IStmt inForStmt) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.inForStmt = inForStmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStmt newStatement = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", exp1),
                new WhileStmt(new RelationalExp(new VarExp("v"), exp2, "<"), new CompStmt(inForStmt, new AssignStmt("v", exp3)))));
        state.getStk().push(newStatement);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp1 = exp1.typeCheck(typeEnv);
        Type typeExp2 = exp2.typeCheck(typeEnv);
        Type typeExp3 = exp3.typeCheck(typeEnv);
        if (!typeExp1.equals(new IntType()) || !typeExp2.equals(new IntType()) || !typeExp3.equals(new IntType()))
            throw new TypeException("Not all three expressions are of type int");

        return typeEnv;
    }

    @Override
    public Object clone() {
        try {
            return (ForStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new ForStmt(varName, exp1, exp2, exp3, inForStmt);
        }
    }

    @Override
    public String toString() {
        return String.format("(for (%s=%s; %s<%s; %s=%s) {%s}", varName, exp1.toString(), varName, exp2.toString(), varName, exp3.toString(), inForStmt.toString());
    }

}
