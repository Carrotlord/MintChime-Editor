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
public class Log extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        arg0 = PointerTools.convertPreciseRealToReal(arg0);
        arg1 = PointerTools.convertPreciseRealToReal(arg1);
        Double operand0 = PointerTools.dereferenceReal(arg0);
        Double operand1 = PointerTools.dereferenceReal(arg1);
        if (operand0 == null || operand1 == null) {
            throw new MintException("Logarithms can only be applied to " + 
                                    "reals and integers.");
        }
        return Heap.allocateReal(Math.log(operand0) / Math.log(operand1));
    }
    
}
