package builtin.time;

import builtin.BuiltinSub;
import java.util.GregorianCalendar;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class TimeZone extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        return Heap.allocateString(new GregorianCalendar().getTimeZone().
                                   getDisplayName());
    }
    
}
