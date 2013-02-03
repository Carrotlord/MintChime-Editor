package gui;

import builtin.graphics.ButtonManager;
import builtin.graphics.MintWindow;
import builtin.graphics.Shape;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Oliver Chu
 */
public class PointerTools {
    public static BufferedImage derefImg(Pointer p) {
        if (p.type != Constants.IMG_TYPE) {
            return null;
        }
        return Heap.derefImg(p.value);
    }
    
    public static Rational derefRat(Pointer p) {
        if (p.type != Constants.RAT_TYPE) {
            return null;
        }
        return Heap.derefRat(p.value);
    }
    
    public static Message dereferenceMessage(Pointer p) {
        if (p.type != Constants.MESSAGE_TYPE)
            return null;
        return Heap.dereferenceMessage(p.value);
    }
    
    public static String dereferenceName(Pointer p) {
        if (p.type != Constants.NAME_TYPE)
            return null;
        return Heap.dereferenceName(p.value);
    }
    
    public static Integer dereferenceKeyword(Pointer p) {
        if (p.type != Constants.KEYWORD_TYPE)
            return null;
        return p.value;
    }
    
    public static Integer dereferenceInt(Pointer p) {
        if (p.type != Constants.INT_TYPE)
            return null;
        return p.value;
    }
    
    public static Double dereferenceReal(Pointer p) {
        if (p.type == Constants.INT_TYPE) {
            return (double) p.value;
        } else if (p.type == Constants.REAL_TYPE) {
            return Heap.dereferenceReal(p.value);
        } else if (p.type == Constants.RAT_TYPE) {
            return Heap.derefRat(p.value).toDouble();
        } else if (p.type == Constants.PRECISE_REAL_TYPE) {
            return Heap.dereferencePreciseReal(p.value).doubleValue();
        } else if (p.type == Constants.BIG_INT_TYPE) {
            return Heap.dereferenceBigInt(p.value).doubleValue();
        }
        return null;
    }
    
    public static String dereferenceString(Pointer p) {
        if (p.type != Constants.STR_TYPE) {
            return null;
        }
        return Heap.dereferenceString(p.value);
    }
    
    public static Boolean dereferenceTruth(Pointer p) {
        if (p.type != Constants.TRUTH_TYPE) {
            return null;
        }
        return p.value == 1;
    }
    
    public static MintObject dereferenceObject(Pointer p) {
        if (p.type != Constants.OBJECT_TYPE) {
            return null;
        }
        return Heap.dereferenceObject(p.value);
    }
    
    public static SmartList<Pointer> dereferenceList(Pointer p) {
        if (p.type != Constants.LIST_TYPE &&
            p.type != Constants.TABLE_TYPE) {
            return null;
        }
        return Heap.dereferenceList(p.value);
    }
    
    public static Subprogram dereferenceSub(Pointer p) {
        if (p.type != Constants.SUBPROGRAM_TYPE) {
            return null;
        }
        return Heap.dereferenceSub(p.value);
    }
    
    public static Block dereferenceBlock(Pointer p) {
        if (p.type != Constants.BLOCK_TYPE) {
            return null;
        }
        return Heap.dereferenceBlock(p.value);
    }
    
    public static BigInteger dereferenceBigInt(Pointer p) {
        if (p.type != Constants.BIG_INT_TYPE) {
            return null;
        }
        return Heap.dereferenceBigInt(p.value);
    }
    
    public static BigDecimal dereferencePreciseReal(Pointer p) {
        if (p.type == Constants.BIG_INT_TYPE) {
            BigInteger bi = Heap.dereferenceBigInt(p.value);
            return new BigDecimal(bi);
        }
        if (p.type != Constants.PRECISE_REAL_TYPE) {
            return null;
        }
        return Heap.dereferencePreciseReal(p.value);
    }
    
    public static SmartList<Byte> dereferenceBytes(Pointer p) {
        if (p.type != Constants.BYTES_TYPE)
            return null;
        return Heap.dereferenceBytes(p.value);
    }
    
    public static MintWindow dereferenceWindow(Pointer p) {
        if (p.type != Constants.WINDOW_TYPE) {
            return null;
        }
        return Heap.dereferenceWindow(p.value);
    }
    
    public static Table dereferenceTable(Pointer p) {
        if (p.type != Constants.TABLE_TYPE) {
            return null;
        }
        return Heap.dereferenceTable(p.value);
    }
    
    public static ButtonManager dereferenceButton(Pointer p) {
        if (p.type != Constants.BUTTON_TYPE) {
            return null;
        }
        return Heap.dereferenceButton(p.value);
    }
    
    public static Pointer convertBigIntToInt(Pointer p) {
        if (p.type != Constants.BIG_INT_TYPE)
            return p;
        BigInteger bi = dereferenceBigInt(p);
        int i = bi.intValue();
        return Heap.allocateInt(i);
    }
    
    public static Pointer convertPreciseRealToReal(Pointer p) {
        if (p.type != Constants.PRECISE_REAL_TYPE) {
            return p;
        }
        BigDecimal bd = dereferencePreciseReal(p);
        double d = bd.doubleValue();
        return Heap.allocateReal(d);
    }
    
    public static Interpreter dereferenceInterpreter(Pointer p) {
        if (p.type != Constants.INTERPRETER_TYPE)
            return null;
        return Heap.dereferenceInterpreter(p.value);
    }
    
    public static Shape dereferenceShape(Pointer p) {
        if (p.type != Constants.SHAPE_TYPE)
            return null;
        return Heap.dereferenceShape(p.value);
    }
    
    /** Converts a pointer's value into a string, no matter the value. */
    public static String dereferenceAsString(Pointer p) throws MintException {
        if (p == null)
            return "ERROR";
        switch (p.type) {
            case Constants.INT_TYPE:
                return Integer.toString(p.value);
            case Constants.KEYWORD_TYPE:
                return Interpreter.convertKeywordToOperator(p.value);
            case Constants.NAME_TYPE:
                return dereferenceName(p);
            case Constants.NULL_TYPE:
                return "null";
            case Constants.REAL_TYPE:
                return Double.toString(dereferenceReal(p));
            case Constants.STR_TYPE:
                return "\"" + dereferenceString(p) + "\"";
            case Constants.TRUTH_TYPE:
                if (p.value == 0)
                    return "false";
                return "true";
            case Constants.MESSAGE_TYPE:
                return dereferenceMessage(p).toString();
            case Constants.OBJECT_TYPE:
                return dereferenceObject(p).toString();
            case Constants.SUBPROGRAM_TYPE:
                return "<subprogram " + dereferenceSub(p).getName() + ">";
            case Constants.LIST_TYPE:
                SmartList<Pointer> tbl = PointerTools.dereferenceList(p);
                boolean isTbl = ListTools.isTable(tbl);
                boolean isReally = isTbl && !(tbl.isRecursiveList) &&
                        !(tbl.isFrozen);
                // This is a normal, non-recursive table.
                if (isReally) {
                    return "{" + StrTools2.slice(
                        dereferenceList(p).toString(), 1, -1) + "}";
                }
                return dereferenceList(p).toString();
            case Constants.TABLE_TYPE:
                return dereferenceTable(p).toString();
            case Constants.BIG_INT_TYPE:
                return dereferenceBigInt(p).toString();
            case Constants.PRECISE_REAL_TYPE:
                return dereferencePreciseReal(p).toString();
            case Constants.BYTES_TYPE:
                String result = "";
                SmartList<Byte> bytes = dereferenceBytes(p);
                for (byte eachByte : bytes) {
                    if (eachByte < 0)
                        result += ((int)eachByte + 256) + " ";
                    else
                        result += eachByte + " ";
                }
                result = StrTools2.slice(result, 0, -1);
                return result;
            case Constants.WINDOW_TYPE:
                return "<window>";
            case Constants.BUTTON_TYPE:
                return "<button>";
            case Constants.INTERPRETER_TYPE:
                return "<interpreter>";
            case Constants.SHAPE_TYPE:
                return "<shape>";
            default:
                throw new MintException("Cannot convert pointer to string: " +
                                        p.toString2());
        }
    }
}
