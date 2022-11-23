package Model.Expressions;


import Model.ADTstructures.MyIDictionary;
import Model.ADTstructures.MyIHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.TypeException;
import Model.Types.RefType;
import Model.Values.IntValue;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadH implements Exp {
    Exp exp;

    public ReadH(Exp exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap heap) throws MyException {
        Value value = exp.eval(table, heap);
        if (!(value instanceof RefValue refValue))
            throw new TypeException("Expression is not evaluated to RefValue");
        if (!heap.isDefined(refValue.getAddress()))
            throw new MyException(String.format("Address %d is not a key in the Heap Table", refValue.getAddress()));

        return heap.lookup(refValue.getAddress());
    }

    public String toString() {
        return "ReadHeap(" + exp.toString() + ")";
    }
}
