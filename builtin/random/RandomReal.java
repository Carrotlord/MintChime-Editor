package builtin.random;

import builtin.BuiltinSub;
import java.util.Random;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class RandomReal extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        RandomInt.setRandomNumberGenerator();
        if (RandomInt.rng == null) {
            RandomInt.rng = new Random();
            RandomInt.rng.setSeed(System.currentTimeMillis());
        }
        return Heap.allocateReal(RandomInt.rng.nextDouble());
    }
    
}
