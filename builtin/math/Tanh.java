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
public class Tanh extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        arg0 = PointerTools.convertPreciseRealToReal(arg0);
        Double operand0 = PointerTools.dereferenceReal(arg0);
        if (operand0 == null) {
            throw new MintException(
                      "Hyperbolic tangent can only be applied to integers" + 
                      " or reals.");
        }
        return Heap.allocateReal(Math.tanh(operand0));
    }
    
}
