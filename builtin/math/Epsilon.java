package builtin.math;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * Returns x times a very small number.
 * @author Oliver Chu
 */
public class Epsilon extends BuiltinSub {
    public static final double EPSILON = 1e-320;
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Double d = PointerTools.dereferenceReal(args.get(0));
        if (d == null) {
            return Heap.allocateReal(Double.NaN);
        }
        return Heap.allocateReal(d * EPSILON);
    }
}
