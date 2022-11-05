package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type {

    @Override
    public boolean equals(Type another) {
        if (another instanceof BoolType)
            return true;
        return false;
    }

    public String toString() {
        return "bool";
    }

    @Override
    public Value getDefaultValue() {
        return new BoolValue();
    }
}
