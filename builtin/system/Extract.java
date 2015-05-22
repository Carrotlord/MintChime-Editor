package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Environment;
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
public class Extract extends BuiltinSub {
    private Interpreter habitat;
    private Pointer gottenValue = null;
    private static final long ONE_SECOND = 1000000000L;
    private static final long TIME_LIMIT = ONE_SECOND * 8L;
    private Thread thread = null;

    public Extract(Interpreter i) {
        habitat = i;
    }
    
    private Environment getEnvirons() {
        return habitat.getEnv();
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer singleArg = args.get(0);
        if (singleArg.type == Constants.INT_TYPE) {
            return Heap.allocateInt(~PointerTools.dereferenceInt(singleArg));
        }
        if (singleArg.type == Constants.TRUTH_TYPE) {
            if (singleArg.value == 1) {
                return Constants.ONE;
            } else {
                return new Pointer(Constants.INT_TYPE, 2);
            }
        }
        if (singleArg.type == Constants.NULL_TYPE) {
            return Constants.ZERO;
        }
        if (singleArg.type == Constants.REAL_TYPE) {
            String realStr = PointerTools.dereferenceAsString(singleArg);
            if (realStr.contains("inf")) {
                return Heap.allocateInt(-2);
            } else if (realStr.contains("undef") || realStr.contains("nan")) {
                return Heap.allocateInt(-1);
            }
        }
        String named = PointerTools.dereferenceString(singleArg);
        if (named == null) {
            return Constants.MINT_NULL;
        }
        Environment self = getEnvirons();
        try {
            long startTime = System.nanoTime();
            thread = new Thread(new GetVariable(named, self));
            thread.start();
            while (gottenValue == null) {
                if ((System.nanoTime() - startTime) > TIME_LIMIT) {
                    throw new MintException("Time limit exceeded.");
                }
            }
        } catch (Throwable t) {
            return Heap.allocateString("<error: " + t + ">");
        }
        if (gottenValue == null ||
            (gottenValue.type == Constants.NULL_TYPE &&
             gottenValue.value == 0)) {
            return Constants.MINT_NULL;
        }
        SmartList<Pointer> listOfInts = new SmartList<Pointer>();
        listOfInts.add(Heap.allocateInt(gottenValue.type));
        listOfInts.add(Heap.allocateInt(gottenValue.value));
        thread.interrupt();
        thread = null;
        return Heap.allocateList(listOfInts);
    }
    
    private class GetVariable implements Runnable {
        private String varName = "NONE";
        private Environment environs = null;
        
        public GetVariable(String variableName, Environment e) {
            varName = variableName;
            environs = e;
        }
  
        @Override
        public void run() {
            try {
                gottenValue =
                    Environment.deepSearchGetValue(varName, environs);
            } catch (Throwable t) {
                gottenValue = Constants.MINT_NULL;
            }
        }
    }
}
