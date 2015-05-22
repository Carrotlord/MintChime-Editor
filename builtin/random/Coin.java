package builtin.random;

import builtin.BuiltinSub;
import java.util.Random;
import gui.Constants;
import gui.Heap;
import gui.Mint;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * A coin is a random number generator that will
 * be weighted in a certain direction.
 * A coin will return heads or tails, with the Double
 * passed to the coin determining how often you get heads.
 * 
 * 0.5 = fair coin
 * 0.0 = always tails
 * 1.0 = always heads
 * 0.9 = 90% heads
 * 0.2 = 80% tails
 * @author Oliver Chu
 */
public class Coin extends BuiltinSub {
    private static final Pointer HEADS = Heap.allocateString("heads");
    private static final Pointer TAILS = Heap.allocateString("tails");
    
    private double normalize(double d) {
        if (d > 0.99) {
            return 0.99;
        } else if (d < 0.01) {
            return 0.01;
        }
        return d;
    }
    
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        try {
            Double d = null;
            Pointer p = args.get(0);
            if (args != null && args.size() != 0) {
                if (p.type == Constants.REAL_TYPE) {
                    d = Heap.dereferenceReal(p.value);
                } else {
                    d = PointerTools.dereferenceReal(p);
                }
            }
            if (d == null) {
                System.out.println("Argument " + args.get(0).toString() +
                    " is not a decimal-point number.");
                d = 0.5;
            }
            Random rng = new Random();
            rng.setSeed(System.nanoTime());
            if (d > 0.985) {
                return HEADS;
            } else if (d < 0.015) {
                return TAILS;
            }
            boolean b = normalize(rng.nextDouble()) < d;
            return b ? HEADS : TAILS;
        } catch (Throwable t) {
            System.err.println(t);
            Mint.printStackTrace(t.getStackTrace());
            return TAILS;
        }
    }
}
