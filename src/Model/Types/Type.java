package Model.Types;

import Model.Values.Value;

public interface Type {
    boolean equals(Type another);

    Value getDefaultValue();
}

