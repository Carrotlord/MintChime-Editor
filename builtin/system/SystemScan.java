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
public class SystemScan extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Runtime runtime = Runtime.getRuntime();
        long freeMem = runtime.freeMemory();
        String analysis =
            (runtime.totalMemory() - freeMem) +
            " bytes in use.\n";
        analysis += freeMem + " bytes not used.\n";
        analysis += runtime.maxMemory() + " bytes maximum.";
        return Heap.allocateString(analysis);
    }
    
}
