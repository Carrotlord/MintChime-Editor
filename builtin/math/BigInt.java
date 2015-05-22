package builtin.math;

import builtin.BuiltinSub;
import java.math.BigInteger;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class BigInt extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        if (arg0.type == Constants.INT_TYPE) {
            Integer x = PointerTools.dereferenceInt(arg0);
            return Heap.allocateBigInt(new BigInteger(x.toString()));
        } else if (arg0.type == Constants.REAL_TYPE) {
            Integer x = (int)(double)PointerTools.dereferenceReal(arg0);
            return Heap.allocateBigInt(new BigInteger(x.toString()));
        } else if (arg0.type == Constants.STR_TYPE) {
            String s = PointerTools.dereferenceString(arg0);
            return Heap.allocateBigInt(new BigInteger(s));
        } else {
            throw new MintException("Cannot instantiate BigInt with argument " +
                                    arg0);
        }
    }
    
}
