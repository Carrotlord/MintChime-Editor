package builtin.math;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Environment;
import gui.Heap;
import gui.Interpreter;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;
import gui.Subprogram;

/**
 * @author Oliver Chu
 */
public class FindRoot extends BuiltinSub {
    public double runFunction(Subprogram functionalSub, double x) throws
        MintException {
        SmartList<Pointer> sListOfPtr = new SmartList<Pointer>();
        sListOfPtr.add(Heap.allocateReal(x));
        Pointer answer = functionalSub.execute(new Environment(),
            new SmartList<String>(), sListOfPtr, new Interpreter());
        return PointerTools.dereferenceReal(answer);
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer a0 = args.get(0);
        Pointer pGuess = args.get(1);
        if (a0.type != Constants.SUBPROGRAM_TYPE) {
            throw new MintException("Cannot find the roots of " +
                "a non-subprogram object.");
        }
        Subprogram ess = PointerTools.dereferenceSub(a0);
        double ex;
        if (pGuess.type == Constants.INT_TYPE) {
            ex = (double) PointerTools.dereferenceInt(pGuess);
        } else if (pGuess.type == Constants.REAL_TYPE) {
            ex = PointerTools.dereferenceReal(pGuess);
        } else {
            return Constants.MINT_NULL;
        }
        double goingUp = ex;
        double goingDown = ex;
        int j = 0;
        while (j < 1000000) {
            if (Math.abs(runFunction(ess, goingUp)) <= 0.000075) {
                return Heap.allocateReal(goingUp);
            }
            if (Math.abs(runFunction(ess, goingDown)) <= 0.000075) {
                return Heap.allocateReal(goingDown);
            }
            goingUp += 0.000025;
            goingDown += 0.000025;
            ++j;
        }
        return Constants.MINT_NULL;
    }
    
}
