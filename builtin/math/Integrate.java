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
 * Returns F(x) such that F'(x) = f(x).
 * F(x) is equal to the integral from 0 to x of f(t).
 * @author Oliver Chu
 */
public class Integrate extends BuiltinSub {
    private class F extends BuiltinSub {
        private Subprogram function;
        
        public void setSub(Subprogram f) {
            function = f;
        }
        
        private double evaluate(double x) throws MintException {
            double dx = 0.025;
            double a = 0.0;
            double b = x;
            double sum = 0.0;
            while (a < b) {
                SmartList<Pointer> args = new SmartList<Pointer>();
                args.add(Heap.allocateReal(a));
                sum += dx * PointerTools.dereferenceReal(
                       function.execute(new Environment(),
                       new SmartList<String>(), args, new Interpreter()));
                a += dx;
            }
            return sum;
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
            throw new MintException("Must integrate a subprogram that " +
                "represents a one-argument mathematical function.");
        }
        F f = new F();
        f.setSub(PointerTools.dereferenceSub(arg0));
        SmartList<Pointer> sOfPtrs = new SmartList<Pointer>();
        sOfPtrs.add(Heap.allocateName("x"));
        Subprogram diffedSub = new Subprogram("F", sOfPtrs, f);
        return Heap.allocateSub(diffedSub);
    }
}
