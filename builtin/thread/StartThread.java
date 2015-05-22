package builtin.thread;

import builtin.BuiltinSub;
import gui.Constants;
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
public class StartThread extends BuiltinSub {
    

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Subprogram s = PointerTools.dereferenceSub(args.get(0));
        Interpreter i = PointerTools.dereferenceInterpreter(args.get(1));
        Threadable th = new Threadable(s, i);
        Thread t = new Thread(th);
        t.start();
        Heap.keepThread(t);
        return Constants.MINT_NULL;
    }
    
    private class Threadable implements Runnable {
        private Subprogram sub;
        private Interpreter interp;
        
        public Threadable(Subprogram s, Interpreter i) {
            sub = s;
            interp = i;
        }
        
        @Override
        public void run() {
            try {
                sub.execute(interp.getEnv(), interp.getImports(),
                            new SmartList<Pointer>(), interp);
            } catch (MintException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
