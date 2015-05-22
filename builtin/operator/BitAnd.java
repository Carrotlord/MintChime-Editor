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
public class BitAnd extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if (arg0.type == Constants.BIG_INT_TYPE &&
            arg1.type == Constants.BIG_INT_TYPE) {
            BigInteger op0 = PointerTools.dereferenceBigInt(arg0);
            BigInteger op1 = PointerTools.dereferenceBigInt(arg1);
            return Heap.allocateBigInt(op0.and(op1));
        }
        Integer operand0 = PointerTools.dereferenceInt(arg0);
        Integer operand1 = PointerTools.dereferenceInt(arg1);
        if (operand0 == null || operand1 == null) {
            throw new MintException("Bitwise and can only be applied to " + 
                                    "integers.");
        }
        return Heap.allocateInt(operand0 & operand1);
    }
    
}
