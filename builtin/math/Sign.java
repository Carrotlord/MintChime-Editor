package builtin.math;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * @author Oliver Chu
 */
public class Sign extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        double d = 0.0;
        if (arg0.type == Constants.INT_TYPE) {
            d = (double) PointerTools.dereferenceInt(arg0);
        } else if (arg0.type == Constants.REAL_TYPE) {
            d = PointerTools.dereferenceReal(arg0);
        }
        if (d < 0.0) {
            return Constants.NEG_ONE;
        } else if (Math.abs(d) < 0.0001) {
            return Constants.ZERO;
        } else {
            return Constants.ONE;
        }
    }
    
}
