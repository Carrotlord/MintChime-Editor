package builtin.math;

import builtin.BuiltinSub;
import java.math.BigDecimal;
import java.math.BigInteger;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Max extends BuiltinSub {
    private boolean max(double a, double b) {
        return a > b;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if (arg0.type == Constants.BIG_INT_TYPE && arg1.type ==
            Constants.BIG_INT_TYPE) {
            BigInteger bi0 = PointerTools.dereferenceBigInt(arg0);
            BigInteger bi1 = PointerTools.dereferenceBigInt(arg1);
            boolean t = bi0.compareTo(bi1) > 0;
            if (t)
                return arg0;
            else
                return arg1;
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE && arg1.type ==
                   Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            BigDecimal bd1 = PointerTools.dereferencePreciseReal(arg1);
            boolean t = bd0.compareTo(bd1) > 0;
            if (t)
                return arg0;
            else
                return arg1;
            
        }
        Double operand0 = PointerTools.dereferenceReal(arg0);
        Double operand1 = PointerTools.dereferenceReal(arg1);
        if (operand0 == null || operand1 == null) {
            throw new MintException("Maximum can only be applied to " + 
                                    "reals and integers.");
        }
        if (max(operand0, operand1))
            return args.get(0);
        return args.get(1);
    }
    
}
