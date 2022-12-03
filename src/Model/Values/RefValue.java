package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int val, Type inner) {
        this.address = val;
        this.locationType = inner;
    }

    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public String toString() {
        return address + " -> " + locationType.toString();
    }

    @Override
    public boolean equals(Value another) {
        if (another instanceof RefValue)
            return true;
        return false;

    }
}
