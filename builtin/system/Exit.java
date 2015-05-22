package builtin.system;

import builtin.BuiltinSub;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Exit extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        System.exit(0);
        return null;
    }
}
