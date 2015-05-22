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
public class TrueRandomReal extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        RandomInt.setRandomNumberGenerator();
        int first =
            PointerTools.dereferenceInt(new TrueRandomInt().apply(null));
        int second =
            PointerTools.dereferenceInt(new TrueRandomInt().apply(null));
        double result = PointerTools.dereferenceReal(
                        new RandomReal().apply(null));
        if (Math.abs(first) > Math.abs(second)) {
            result = (double) first / second;
        } else {
            result = (double) second / first;
        }
        result += new Random().nextFloat();
        if (result < 0.0) {
            result = Math.abs(result);
        }
        if (result >= 1.0) {
            int integerPart = (int) result;
            result -= integerPart;
        }
        return Heap.allocateReal(result);
    }
    
}
