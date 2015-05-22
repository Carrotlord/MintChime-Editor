package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class VarExists extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        try {
            String s = args.get(0).toString();
            return null;
        } catch (Throwable t) {
            String failed = "";
            failed += t;
            StackTraceElement[] traces = t.getStackTrace();
            for (StackTraceElement ste : traces) {
                failed += ste + "\n";
            }
            return Heap.allocateString(failed);
        }
    }
}
