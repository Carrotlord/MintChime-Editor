package builtin.math;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Fib extends BuiltinSub {
    private static final double PHI = 0.5 + Math.sqrt(1.25);
    private static final double root5 = Math.sqrt(5.0);
    
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        double n = (double) PointerTools.dereferenceInt(arg0);
        double answer = Math.pow(PHI, n) / root5;
        return Heap.allocateInt((int) (answer + 0.5));
    }
}
