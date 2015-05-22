package builtin.system;

import builtin.BuiltinSub;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;
import gui.Table;

/**
 *
 * @author Oliver Chu
 */
public class ViewStrings extends BuiltinSub {
    public static final Pointer EMPTY_LIST =
           Heap.allocateList(new SmartList<Pointer>());
    
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        short i = 0;
        while (i < Short.MAX_VALUE) {
            try {
                HashMap<Integer, String> strs = Heap.strings;
                if (strs == null) {
                    return EMPTY_LIST;
                }
                Table tbl = new Table();
                ArrayList<Integer> keys = new ArrayList<Integer>();
                Set<Integer> s = strs.keySet();
                for (int element : s) {
                    keys.add(element);
                }
                s = null;
                for (int key : keys) {
                    SmartList<Pointer> pair = new SmartList<Pointer>();
                    pair.add(new Pointer(Constants.INT_TYPE, key));
                    pair.add(Heap.allocateString(strs.get(key)));
                    tbl.addBinding(pair);
                }
                return Heap.allocateTable(tbl);
            } catch (Throwable t) {}
            ++i;
        }
        try {
            return Heap.allocateString(Heap.strings.toString());
        } catch (Throwable t) {
            return ChangeString.ERROR;
        }
    }
}
