package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * @author Oliver Chu
 */
public class ChangeString extends BuiltinSub {
    public static final Pointer ERROR = Heap.allocateString("<error>");
    
    public Pointer toMintNull(String s) {
        if (s == null) {
            return Constants.MINT_NULL;
        }
        return Heap.allocateString(s);
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer ptr = args.get(0);
        Pointer str = args.get(1);
        String asString = "<none>";
        if (str.type != Constants.STR_TYPE) {
            asString = str.toString();
        } else {
            asString = PointerTools.dereferenceString(str);
        }
        Heap.setString(ptr.value, asString);
        return Heap.allocateString(asString);
    }
    
}
