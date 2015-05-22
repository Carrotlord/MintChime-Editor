package builtin.mint;

import builtin.BuiltinSub;
import gui.Interpreter;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Exec extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String code = PointerTools.dereferenceString(args.get(0));
        Interpreter i = PointerTools.dereferenceInterpreter(args.get(1));
        return i.run(code, true);
    }
    
}
