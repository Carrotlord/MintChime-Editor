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
public class AmPm extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        int amPm = new GregorianCalendar().get(Calendar.AM_PM);
        String _amPm;
        if (amPm == Calendar.AM)
            _amPm = "am";
        else
            _amPm = "pm";
        return Heap.allocateString(_amPm);
    }
    
}
