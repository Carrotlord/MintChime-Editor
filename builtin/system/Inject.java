package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Inject extends BuiltinSub {

    private Pointer wrap(int i) {
        return new Pointer(Constants.INT_TYPE, i);
    }
    
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer argument = args.get(0);
        if (argument.type == Constants.INT_TYPE) {
            int argVal = argument.value;
            switch (argVal) {
                case 0:
                    return Constants.MINT_NULL;
                case 1:
                    return Constants.MINT_TRUE;
                case 2:
                    return Constants.MINT_FALSE;
                case -1:
                    return Heap.allocateReal(Double.NaN);
                case -2:
                    return Heap.allocateReal(Double.POSITIVE_INFINITY);
                default:
                    return wrap(~argVal);
            }
        } else {
            SmartList<Pointer> ptr = PointerTools.dereferenceList(argument);
            if (ptr == null || ptr.size() < 2) {
                return Constants.MINT_NULL;
            }
            return new Pointer(Constants.STR_TYPE, ptr.get(1).value);
        }
    }
}
