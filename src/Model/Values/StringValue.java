package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value{
    private String val;

    public StringValue() {
        this.val = "";
    }

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String getVal() {
        return this.val;
    }

    @Override
    public boolean equals(Value another) {
        if (another instanceof StringValue)
            return true;
        return false;
    }
}
