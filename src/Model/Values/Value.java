package Model.Values;

import Model.Types.*;

public interface Value {
    Type getType();
    boolean equals(Value another);

}
