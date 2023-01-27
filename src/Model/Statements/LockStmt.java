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

public class LockStmt implements IStmt {
    private String varName;
    private final Lock lock = new ReentrantLock();

    public LockStmt(String varName) {
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
        if (lockTable.lookUp(foundIndex.getVal()) == -1)
            lockTable.update(foundIndex.getVal(), state.getPrgStateId());
        else
            state.getStk().push(this);

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
        return "lock(" + varName + ")";
    }

    @Override
    public Object clone() {
        try {
            return (LockStmt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            return new LockStmt(varName);
        }
    }
}
