package builtin.time;

import builtin.BuiltinSub;
import java.util.Calendar;
import java.util.GregorianCalendar;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Hour24HourFormat extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        return Heap.allocateInt(new GregorianCalendar().get(
                                Calendar.HOUR_OF_DAY));
    }
    
}
