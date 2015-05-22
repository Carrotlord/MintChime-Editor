package builtin.operator;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Operator;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class GreaterEq extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        return Operator.comparisonFamily(Constants.GTR_OR_EQUAL, arg0, arg1);
    }
    
}
