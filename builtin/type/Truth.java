package builtin.type;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Truth extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.INT_TYPE) {
            int i = PointerTools.dereferenceInt(arg0);
            if (i == 0)
                return new Pointer(Constants.TRUTH_TYPE, 0);
            else if (i == 1)
                return new Pointer(Constants.TRUTH_TYPE, 1);
        } else if (arg0.type == Constants.REAL_TYPE) {
            double d = PointerTools.dereferenceReal(arg0);
            if (d == 0.0)
                return new Pointer(Constants.TRUTH_TYPE, 0);
            else if (d == 1.0)
                return new Pointer(Constants.TRUTH_TYPE, 1);
        } else if (arg0.type == Constants.TRUTH_TYPE) {
            return arg0;
        } else if (arg0.type == Constants.STR_TYPE) {
            String s = PointerTools.dereferenceString(arg0);
            if (s.equals("true") || s.equals("1"))
                return new Pointer(Constants.TRUTH_TYPE, 1);
            else if (s.equals("false") || s.equals("0"))
                return new Pointer(Constants.TRUTH_TYPE, 0);
        }
        throw new MintException("Cannot convert " + arg0 + " to truth value.");
    }
    
}
