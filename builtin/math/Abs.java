package builtin.math;

import builtin.BuiltinSub;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class Abs extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.BIG_INT_TYPE) {
            BigInteger bi = PointerTools.dereferenceBigInt(arg0);
            return Heap.allocateBigInt(bi.abs());
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd = PointerTools.dereferencePreciseReal(arg0);
            return Heap.allocatePreciseReal(bd.abs());
        } else if (arg0.type == Constants.INT_TYPE) {
            return Heap.allocateInt(Math.abs(
                   PointerTools.dereferenceInt(arg0)));
        } else if (arg0.type == Constants.REAL_TYPE) {
            return Heap.allocateReal(Math.abs(
                   PointerTools.dereferenceReal(arg0)));
        } else {
            throw new MintException("Absolute value can only be applied to " + 
                                    "integers and reals.");
        }
    }
    
}
