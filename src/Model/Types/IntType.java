package Model.Types;

import Model.Values.IntValue;
import Model.Values.Value;

public class IntType implements Type {

    @Override
    public boolean equals(Type another) {
        if (another instanceof IntType)
            return true;
        return false;
    }

    public String toString() {
        return "int";
    }

    @Override
    public Value getDefaultValue() {
        return new IntValue();
    }
}
