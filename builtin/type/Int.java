package builtin.type;

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
public class Int extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.INT_TYPE) {
            return arg0;
        } else if (arg0.type == Constants.REAL_TYPE) {
            return Heap.allocateInt(
                   (int)(double)PointerTools.dereferenceReal(arg0));
        } else if (arg0.type == Constants.TRUTH_TYPE) {
            return Heap.allocateInt(arg0.value);
        } else if (arg0.type == Constants.STR_TYPE) {
            return Heap.allocateInt(Integer.parseInt(
                   PointerTools.dereferenceString(arg0)));
        }
        throw new MintException("Cannot convert " + arg0 + " to integer.");
    }
    
}
