package builtin.math;

import builtin.BuiltinSub;
import java.math.BigDecimal;
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
public class PreciseReal extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.INT_TYPE) {
            int x = PointerTools.dereferenceInt(arg0);
            return Heap.allocatePreciseReal(new BigDecimal(x));
        } else if (arg0.type == Constants.REAL_TYPE) {
            double x = PointerTools.dereferenceReal(arg0);
            return Heap.allocatePreciseReal(new BigDecimal(x));
        } else if (arg0.type == Constants.STR_TYPE) {
            String s = PointerTools.dereferenceString(arg0);
            return Heap.allocatePreciseReal(new BigDecimal(s));
        } else {
            throw new MintException("Cannot instantiate PreciseReal " + 
                                    "with argument " + arg0);
        }
    }
    
}
