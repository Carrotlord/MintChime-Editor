package builtin.math;

import builtin.BuiltinSub;
import java.math.BigInteger;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Factorial extends BuiltinSub {
    private static final int[] preComputed = {1, 1, 2, 6, 24, 120, 720, 5040,
        40320, 362880, 3628800, 39916800, 479001600};

    public int factorial(int x) {
        if (x < 0) {
            return 1;
        } else if (x <= 12) {
            return preComputed[x];
        }
        return x * factorial(x - 1);
    }
    
    private BigInteger factorial(BigInteger x) {
        BigInteger one = new BigInteger("1");
        int cmp = x.compareTo(one);
        if (cmp <= 0) {
            return one;
        }
        return x.multiply(factorial(x.subtract(one)));
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Integer operand0 = PointerTools.dereferenceInt(args.get(0));
        if (operand0 == null) {
            BigInteger op0 = PointerTools.dereferenceBigInt(args.get(0));
            if (op0 == null) {
                throw new MintException("Factorial can only be applied to " + 
                                        "integers.");
            }
            return Heap.allocateBigInt(factorial(op0));
        }
        return Heap.allocateInt(factorial(operand0));
    }
    
}
