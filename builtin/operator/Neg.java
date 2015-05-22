package builtin.operator;

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
public class Neg extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.BIG_INT_TYPE) {
            BigInteger bi0 = PointerTools.dereferenceBigInt(arg0);
            return Heap.allocateBigInt(bi0.negate());
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            return Heap.allocatePreciseReal(bd0.negate());
        }
        if (arg0.type == Constants.INT_TYPE) {
            int result = PointerTools.dereferenceInt(arg0);
            return Heap.allocateInt(-result);
        } else if (arg0.type == Constants.REAL_TYPE) {
            double result = PointerTools.dereferenceReal(arg0);
            return Heap.allocateReal(-result);
        } else {
            throw new MintException("Negation can only be applied to " + 
                                    "integers or real values.");
        }
    }
    
}
