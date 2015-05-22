package builtin.system;

import builtin.*;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class GetArgs extends BuiltinSub {
    private static SmartList<Pointer> sysArgs;

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        return Heap.allocateList(sysArgs);
    }
    
    public static void setArgs(SmartList<String> args) {
        sysArgs = new SmartList<Pointer>();
        for (String arg : args) {
            if (arg != null)
                sysArgs.add(Heap.allocateString(arg));
        }
    }
    
}
