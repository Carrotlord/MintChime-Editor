package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Address extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args.size() < 1) {
            return Constants.NEG_ONE;
        }
        return new Pointer(Constants.INT_TYPE, args.get(0).value);
    }
}
