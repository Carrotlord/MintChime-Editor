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
public class Real extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.INT_TYPE) {
            return Heap.allocateReal(PointerTools.dereferenceInt(arg0));
        } else if (arg0.type == Constants.REAL_TYPE) {
            return arg0;
        } else if (arg0.type == Constants.TRUTH_TYPE) {
            return Heap.allocateReal(arg0.value);
        } else if (arg0.type == Constants.STR_TYPE) {
            return Heap.allocateReal(Double.parseDouble(
                   PointerTools.dereferenceString(arg0)));
        }
        throw new MintException("Cannot convert " + arg0 + " to real.");
    }
    
}
