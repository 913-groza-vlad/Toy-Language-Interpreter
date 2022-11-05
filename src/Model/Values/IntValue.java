package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    private int val;

    public IntValue() {
        this.val = 0;
    }
    public IntValue(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new IntType();
    }

    public String toString() {
        return String.valueOf(val);
    }
}
