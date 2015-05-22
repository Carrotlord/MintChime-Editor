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
public class Dec extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.BIG_INT_TYPE) {
            BigInteger result = PointerTools.dereferenceBigInt(arg0);
            return Heap.allocateBigInt(result.subtract(BigInteger.ONE));
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal result = PointerTools.dereferencePreciseReal(arg0);
            return Heap.allocatePreciseReal(result.subtract(BigDecimal.ONE));
        }
        Integer result = PointerTools.dereferenceInt(arg0);
        if (result == null) {
            Double res = PointerTools.dereferenceReal(arg0);
            if (res == null) {
                throw new MintException("Decrement can only be applied to " + 
                                        "integers and reals.");
            }
            return Heap.allocateReal(res - 1);
        }
        return Heap.allocateInt(result - 1);
    }
}
