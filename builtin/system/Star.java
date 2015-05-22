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
public class Star extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args.size() < 2) {
            return Constants.MINT_NULL;
        }
        return new Pointer((byte) args.get(0).value, args.get(1).value);
    }
}
