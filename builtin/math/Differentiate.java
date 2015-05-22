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
 *
 * @author Oliver Chu
 */
public class Differentiate extends BuiltinSub {
    private class Fprime extends BuiltinSub {
        private Subprogram function;
        
        public void setSub(Subprogram f) {
            function = f;
        }
        
        private double evaluate(double x) throws MintException {
            double dx = 0.000001;
            SmartList<Pointer> funcArgs = new SmartList<Pointer>();
            funcArgs.add(Heap.allocateReal(x + dx));
            SmartList<Pointer> funcArgs2 = new SmartList<Pointer>();
            funcArgs2.add(Heap.allocateReal(x));
            double df = PointerTools.dereferenceReal(
                function.execute(new Environment(),
                new SmartList<String>(), funcArgs, new Interpreter())) -
                PointerTools.dereferenceReal(
                function.execute(new Environment(),
                new SmartList<String>(), funcArgs2, new Interpreter()));
            return df / dx;
        }

        @Override
        public Pointer apply(SmartList<Pointer> args) throws MintException {
            Pointer a0 = args.get(0);
            double ex;
            if (a0.type == Constants.INT_TYPE) {
                ex = (double) PointerTools.dereferenceInt(a0);
            } else {
                ex = PointerTools.dereferenceReal(a0);
            }
            return Heap.allocateReal(evaluate(ex));
        }
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type != Constants.SUBPROGRAM_TYPE) {
            throw new MintException("Must differentiate a subprogram that " +
                "represents a one-argument mathematical function.");
        }
        Fprime fPrime = new Fprime();
        fPrime.setSub(PointerTools.dereferenceSub(arg0));
        SmartList<Pointer> sOfPtrs = new SmartList<Pointer>();
        sOfPtrs.add(Heap.allocateName("x"));
        Subprogram diffedSub = new Subprogram("fPrime", sOfPtrs, fPrime);
        return Heap.allocateSub(diffedSub);
    }
}
