package builtin.operator;

import builtin.BuiltinSub;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class Div extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if ((arg0.type == Constants.BIG_INT_TYPE &&
            arg1.type == Constants.BIG_INT_TYPE) ||
            (arg0.type == Constants.PRECISE_REAL_TYPE &&
             arg1.type == Constants.PRECISE_REAL_TYPE)) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            BigDecimal bd1 = PointerTools.dereferencePreciseReal(arg1);
            return Heap.allocatePreciseReal(bd0.divide(bd1, 200,
                                            RoundingMode.HALF_UP));
        }
        Double operand0 = PointerTools.dereferenceReal(arg0);
        Double operand1 = PointerTools.dereferenceReal(arg1);
        if (arg0 == null || arg1 == null) {
            throw new MintException("Divide can only be applied to " + 
                                    "integers or reals.");
        }
        return Heap.allocateReal(operand0 / operand1);
    }
    
}
