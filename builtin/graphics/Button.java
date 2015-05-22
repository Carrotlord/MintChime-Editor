package builtin.graphics;

import builtin.BuiltinSub;
import gui.Heap;
import gui.Interpreter;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Button extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Interpreter i = PointerTools.dereferenceInterpreter(args.get(0));
        return Heap.allocateButton(new ButtonManager(i));
    }
    
}
