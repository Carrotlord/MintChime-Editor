package builtin.list;

import builtin.BuiltinSub;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Remove extends BuiltinSub {
    private SmartList<Pointer> list;
    
    public Remove(SmartList<Pointer> list) {
        this.list = list;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) {
        Integer i = PointerTools.dereferenceInt(args.get(0));
        return list.remove((int)i);
    }
    
}
