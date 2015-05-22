package builtin.random;

import builtin.BuiltinSub;
import java.util.Random;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 * @author Oliver Chu
 * @deprecated This class is not necessary because when RandomInt or a
 *             similar class sets the seed for the Mint interpreter
 *             that is currently running, TrueRandomInt is never called.
 *             Therefore, setting the seed is not slow.
 */
@Deprecated
public class PrepareRandomness extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args == null || args.size() == 0) {
            args = new SmartList<Pointer>();
            for (int i = 0; i < 10; i++) {
                args.add(Heap.allocateInt(
                    Math.abs((int) System.nanoTime()) % 1000000000));
            }
        }
        System.out.println(args.toString());
        RandomInt.rng = new Random();
        RandomInt.rng.setSeed((args.hashCode() & args.toString().hashCode()) +
                              System.nanoTime() +
                              System.currentTimeMillis());
        return Constants.MINT_NULL;
    }
}
