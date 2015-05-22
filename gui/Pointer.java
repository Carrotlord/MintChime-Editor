package gui;

/**
 *
 * @author Oliver Chu
 */
public class Pointer {
    public byte type;
    public int value;
    
    public Pointer(byte type, int value) {
        this.type = type;
        this.value = value;
    }
    
    @Override
    public String toString() {
        if (type == Constants.IMG_TYPE) {
            return "<image>";
        }
        if (type == Constants.RAT_TYPE) {
            String res = PointerTools.derefRat(this).toBigDecimal().toString();
            while (res.endsWith("0") && !res.endsWith(".0") &&
                res.length() > 3) {
                res = StrTools2.slice(res, 0, -1);
            }
            return res;
        }
        if (type == Constants.MATCHER_TYPE) {
            return "<matcher #" + value + ">";
        }
        try {
            String self = PointerTools.dereferenceAsString(this);
            if (type == Constants.REAL_TYPE) {
                String lowered = self.toLowerCase();
                if (lowered.equals("nan")) {
                    return "undefined";
                } else if (lowered.equals("infinity")) {
                    return "infinity";
                } else if (lowered.contains("infinity") &&
                           lowered.contains("-")) {
                    return "-infinity";
                } else if (lowered.endsWith(".0")) {
                    /** Change myself to an integer. */
                    int v = ((int) (double)
                            PointerTools.dereferenceReal(this));
                    byte t = Constants.INT_TYPE;
                    value = v;
                    type = t;
                    return "" + v;
                }
            }
            return self;
        } catch (MintException ex) {
            return "<error: stale pointer> - " + ex;
        }
    }
    
    public String toString2() {
        return "(" + type + ", " + value + ")";
    }
    
    boolean equals(Pointer p) {
        return type == p.type && value == p.value;
    }
}
