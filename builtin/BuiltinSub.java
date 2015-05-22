package builtin;

import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public abstract class BuiltinSub {
    public abstract Pointer apply(SmartList<Pointer> args) throws MintException;
}
