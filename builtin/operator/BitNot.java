package builtin.operator;

import builtin.BuiltinSub;
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
public class BitNot extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.BIG_INT_TYPE) {
            BigInteger op0 = PointerTools.dereferenceBigInt(arg0);
            return Heap.allocateBigInt(op0.not());
        }
        Integer result = PointerTools.dereferenceInt(arg0);
        if (result == null) {
            throw new MintException("Bitwise not cannot be applied to " + 
                                    "non-integer value.");
        }
        return Heap.allocateInt(~result);
    }
}
