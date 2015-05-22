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
public class Eval extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String code = PointerTools.dereferenceString(args.get(0));
        Interpreter interp = PointerTools.dereferenceInterpreter(args.get(1));
        SmartList<SmartList<Pointer>> pointerLists =
                                      interp.loadProgram(code, true);
        return interp.evalExpression(pointerLists.get(0), 0);
    }
    
}
