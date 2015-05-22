package builtin.time;

import builtin.BuiltinSub;
import java.util.Date;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class _Date extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        return Heap.allocateString(new Date().toString());
    }
    
}
