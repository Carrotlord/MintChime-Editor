package builtin.operator;

import builtin.BuiltinSub;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class LogicNot extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Boolean operand0 = PointerTools.dereferenceTruth(arg0);
        if (operand0 == null) {
            throw new MintException("Logical not can only be applied to " + 
                                    "truth values.");
        }
        return Heap.allocateTruth(!operand0);
    }
    
}
