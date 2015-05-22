package builtin.math;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * Returns infinity, always.
 * @author Oliver Chu
 */
public class Infinity extends BuiltinSub {
    public static final Pointer POS_INF =
        Heap.allocateReal(Double.POSITIVE_INFINITY);
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        return POS_INF;
    }
}
