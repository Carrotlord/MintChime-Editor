package builtin.encryption;

import builtin.*;
import gui.Heap;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Jumble extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) {
        String toBeJumbled = PointerTools.dereferenceString(args.get(0));
        String jumbled = "";
        SmartList<Pointer> retnList = new SmartList<Pointer>();
        SmartList<Byte> bytes = new SmartList<Byte>();
        // TODO Finish this method.
        return Heap.allocateList(retnList);
    }
}
