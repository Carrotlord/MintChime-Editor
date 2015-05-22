package builtin.system;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Swap extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer firstReal = args.get(0);
        Pointer secondReal = args.get(1);
        Heap.swapReals(firstReal, secondReal);
        SmartList<Pointer> swapped = new SmartList<Pointer>();
        swapped.add(secondReal);
        swapped.add(firstReal);
        return Heap.allocateList(swapped);
    }
}
