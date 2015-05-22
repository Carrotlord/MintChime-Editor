package builtin.random;

import builtin.BuiltinSub;
import java.util.Random;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class RandomInt extends BuiltinSub {
    protected static Random rng = new Random();
    protected static boolean isRngSet = false;
    
    protected static void setRandomNumberGenerator() {
        if (!isRngSet) {
            rng = new Random();
            rng.setSeed(System.nanoTime() + new Object().hashCode());
            /* long lng = rng.nextLong();
            int trulyRandom = rng.nextInt();
            int otherTrulyRandom = rng.nextInt();
            try {
                trulyRandom &=
                    PointerTools.dereferenceInt(
                    new TrueRandomInt().apply(null));
                otherTrulyRandom ^=
                    PointerTools.dereferenceInt(
                    new TrueRandomInt().apply(null));
            } catch (MintException ex) {}
            rng.setSeed(-Math.abs(trulyRandom) * Math.abs(otherTrulyRandom) +
                        lng); */
            isRngSet = true;
        }
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        setRandomNumberGenerator();
        Integer a = PointerTools.dereferenceInt(args.get(0));
        Integer b = PointerTools.dereferenceInt(args.get(1));
        return Heap.allocateInt(rng.nextInt(b - a + 1) + a);
    }
    
}
