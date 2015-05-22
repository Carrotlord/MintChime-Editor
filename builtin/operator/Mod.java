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
public class Mod extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if (arg0.type == Constants.BIG_INT_TYPE &&
            arg1.type == Constants.BIG_INT_TYPE) {
            BigInteger bi0 = PointerTools.dereferenceBigInt(arg0);
            BigInteger bi1 = PointerTools.dereferenceBigInt(arg1);
            return Heap.allocateBigInt(bi0.remainder(bi1));
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE &&
                   arg1.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            BigDecimal bd1 = PointerTools.dereferencePreciseReal(arg1);
            BigInteger bi0 = new BigInteger(bd0.toString());
            BigInteger bi1 = new BigInteger(bd1.toString());
            return Heap.allocateBigInt(bi0.remainder(bi1));
        }
        Double operand0 = PointerTools.dereferenceReal(arg0);
        Double operand1 = PointerTools.dereferenceReal(arg1);
        if (arg0 == null || arg1 == null) {
            throw new MintException("Modulo can only be applied to " + 
                                    "integers.");
        }
        double op0 = operand0;
        double op1 = operand1;
        return Heap.allocateInt((int)op0 % (int)op1);
    }
    
}
