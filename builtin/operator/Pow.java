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
public class Pow extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if (arg0.type == Constants.BIG_INT_TYPE &&
            arg1.type == Constants.BIG_INT_TYPE) {
            BigInteger bi0 = PointerTools.dereferenceBigInt(arg0);
            BigInteger bi1 = PointerTools.dereferenceBigInt(arg1);
            return Heap.allocateBigInt(bi0.pow(bi1.intValue()));
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE &&
                   arg1.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            BigDecimal bd1 = PointerTools.dereferencePreciseReal(arg1);
            return Heap.allocatePreciseReal(bd0.pow(bd1.intValue()));
        }
        if (arg0.type == Constants.INT_TYPE && arg1.type == Constants.INT_TYPE)
        {
            int operand0 = PointerTools.dereferenceInt(arg0);
            int operand1 = PointerTools.dereferenceInt(arg1);
            return Heap.allocateInt((int)Math.pow(operand0, operand1));
        } else {
            Double operand0 = PointerTools.dereferenceReal(arg0);
            Double operand1 = PointerTools.dereferenceReal(arg1);
            if (arg0 == null || arg1 == null) {
                throw new MintException("Power can only be applied to " + 
                                        "integers or reals.");
            }
            return Heap.allocateReal(Math.pow(operand0, operand1));
        }
    }
    
}
