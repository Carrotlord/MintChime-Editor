package builtin.type;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * Type is a subprogram that returns the type of any Mint value, as a
 * string.
 * 
 * When "object" is returned, that refers to objects created using
 * return this, also known as environment frames.
 * When "value" is returned, it is ANY generic Mint object of unknown type.
 * @author Oliver Chu
 */
public class Type extends BuiltinSub {
    private static Pointer LIST_TYPE_STR = Heap.allocateString("list");
    private static Pointer ELLIPSIS_TYPE_STR = Heap.allocateString("ellipsis");
    private static Pointer TABLE_TYPE_STR = Heap.allocateString("table");

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args == null || args.isEmpty()) {
            return Heap.allocateString("Java Virtual Machine null");
        }
        Pointer p = args.get(0);
        if (p == null) {
            return Heap.allocateString("Java Virtual Machine null");
        }
        if (p.type == Constants.RAT_TYPE) {
            return Heap.allocateString("fraction");
        } else if (p.type == Constants.IMG_TYPE) {
            return Heap.allocateString("image");
        }
        String strData = PointerTools.dereferenceAsString(p);
        boolean isNotString = p.type != Constants.STR_TYPE;
        if (strData.toLowerCase().contains("infinity") && isNotString) {
            return Heap.allocateString("infinite number");
        }
        if (p.value == Constants.ELLIPSIS &&
            p.type == Constants.KEYWORD_TYPE ||
            (strData.equals("...") &&
             p.type != Constants.STR_TYPE)) {
            return ELLIPSIS_TYPE_STR;
        }
        if (p.type == Constants.TABLE_TYPE) {
            return TABLE_TYPE_STR;
        }
        if (p.type == Constants.LIST_TYPE) {
            SmartList<Pointer> slp = PointerTools.dereferenceList(p);
            if (slp.size() == 0) {
                return Heap.allocateString("empty sequence");
            }
            // Check if this list is really a table.
            for (Pointer ptr : slp) {
                if (ptr.type == Constants.LIST_TYPE) {
                    SmartList<Pointer> possiblePair =
                        PointerTools.dereferenceList(ptr);
                    if (possiblePair.size() != 2) {
                        return LIST_TYPE_STR;
                    }
                } else {
                    return LIST_TYPE_STR;
                }
            }
            return TABLE_TYPE_STR;
        }
        switch (p.type) {
            case Constants.INT_TYPE:
                return Heap.allocateString("integer number");
            case Constants.REAL_TYPE:
                return Heap.allocateString("real number");
            case Constants.RAT_TYPE:
                return Heap.allocateString("rational number");
            case Constants.STR_TYPE:
                return Heap.allocateString("string");
            case Constants.TRUTH_TYPE:
                return Heap.allocateString("truth");
            case Constants.NULL_TYPE:
                return Heap.allocateString("null");
            case Constants.MESSAGE_TYPE:
                return Heap.allocateString("message");
            case Constants.OBJECT_TYPE:
                return Heap.allocateString("object");
            case Constants.LIST_TYPE:
                return Heap.allocateString("list");
            case Constants.SUBPROGRAM_TYPE:
                return Heap.allocateString("subprogram");
            case Constants.BLOCK_TYPE:
                return Heap.allocateString("block");
            case Constants.BIG_INT_TYPE:
                return Heap.allocateString("big integer");
            case Constants.PRECISE_REAL_TYPE:
                return Heap.allocateString("precise real");
            case Constants.BYTES_TYPE:
                return Heap.allocateString("bytes");
            case Constants.WINDOW_TYPE:
                return Heap.allocateString("window");
            case Constants.BUTTON_TYPE:
                return Heap.allocateString("button");
            case Constants.INTERPRETER_TYPE:
                return Heap.allocateString("interpreter");
            case Constants.SHAPE_TYPE:
                return Heap.allocateString("shape");
            case Constants.MATCHER_TYPE:
                return Heap.allocateString("matcher");
            case Constants.KEYWORD_TYPE:
                if (PointerTools.dereferenceKeyword(p) == Constants.ELLIPSIS) {
                    return ELLIPSIS_TYPE_STR;
                } else {
                    return Heap.allocateString("keyword");
                }
            default:
                return Heap.allocateString("value");
        }
    }
    
}
