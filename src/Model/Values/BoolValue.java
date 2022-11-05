package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    private boolean val;

    public BoolValue() {
        this.val = false;
    }
    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }
    @Override
    public Type getType() {
        return new BoolType();
    }

    public String toString() {
        return Boolean.toString(val);
    }
}
