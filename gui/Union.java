package gui;

/**
 * A union is an object that can hold any object,
 * and can automatically cast itself to the
 * correct type.
 * @author Oliver Chu
 */
public class Union {
    public Object self = Constants.MINT_NULL;

    public Union(Object obj) {
        self = obj;
    }
    
    public Integer intVal() {
        return (Integer) self;
    }
    
    public String strVal() {
        return (String) self;
    }

    public Rational ratVal() {
        return (Rational) self;
    }
    
    public Byte typeOf() {
        if (self == null) {
            return null;
        }
        if (self instanceof Pointer) {
            return Constants.NULL_TYPE;
        }
        if (self instanceof Integer) {
            return Constants.INT_TYPE;
        }
        if (self instanceof String) {
            return Constants.STR_TYPE;
        }
        if (self instanceof Rational) {
            return Constants.RAT_TYPE;
        }
        return Byte.MIN_VALUE;
    }
}
