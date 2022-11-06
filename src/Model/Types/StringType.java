package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Type another) {
        if (another instanceof StringType)
            return true;
        return false;
    }

    public String toString() {
        return "string";
    }

    @Override
    public Value getDefaultValue() {
        return new StringValue();
    }
}
