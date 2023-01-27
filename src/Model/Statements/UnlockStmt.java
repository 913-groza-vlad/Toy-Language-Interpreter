package Model.Statements;

import Model.ADTstructures.ILockTable;
import Model.ADTstructures.MyIDictionary;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnlockStmt implements IStmt {
    private String varName;
    private final Lock lock = new ReentrantLock();

    public UnlockStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        ILockTable lockTable = state.getLockTable();
        if (!symTable.isDefined(varName) || symTable.lookup(varName).getType().equals(new IntType()))
            throw new MyException(varName + " not defined in the SymbolTable or it is not of type int");
        IntValue foundIndex = (IntValue) symTable.lookup(varName);
        if (!lockTable.isDefined(foundIndex.getVal()))
            throw new MyException(foundIndex.toString() + " is not an index in the LockTable");
        if (lockTable.lookUp(foundIndex.getVal()) == state.getPrgStateId())
            lockTable.update(foundIndex.getVal(), -1);
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (!typeEnv.lookup(varName).equals(new IntType()))
            throw new TypeException(varName + "doesn't have type int!");
        return typeEnv;
    }

    public String toString() {
        return "unlock(" + varName + ")";
    }

    @Override
    public Object clone() {
        try {
            return (UnlockStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new UnlockStmt(varName);
        }
    }
}