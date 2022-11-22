package Model.Types;

import Model.Values.RefValue;
import Model.Values.Value;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    @Override
    public boolean equals(Type another) {
        if (another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        return false;
    }

    public Type getInner() {
        return this.inner;
    }

    public String toString() {
        return "Ref(" + inner.toString() +")";
    }

    @Override
    public Value getDefaultValue() {
        return new RefValue(0, inner);
    }
}
