package builtin.math;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Sqrt extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        arg0 = PointerTools.convertPreciseRealToReal(arg0);
        Double operand0 = PointerTools.dereferenceReal(arg0);
        if (operand0 == null) {
            throw new MintException("Square root can only be applied to " + 
                                    "reals and integers.");
        }
        return Heap.allocateReal(Math.sqrt(operand0));
    }
    
}
