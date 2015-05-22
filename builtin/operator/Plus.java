package builtin.operator;

import builtin.BuiltinSub;
import java.math.BigDecimal;
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
public class Plus extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer arg0 = args.get(0);
        Pointer arg1 = args.get(1);
        if (arg0.type == Constants.BIG_INT_TYPE &&
            arg1.type == Constants.BIG_INT_TYPE) {
            BigInteger bi0 = PointerTools.dereferenceBigInt(arg0);
            BigInteger bi1 = PointerTools.dereferenceBigInt(arg1);
            return Heap.allocateBigInt(bi0.add(bi1));
        } else if (arg0.type == Constants.PRECISE_REAL_TYPE &&
                   arg1.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal bd0 = PointerTools.dereferencePreciseReal(arg0);
            BigDecimal bd1 = PointerTools.dereferencePreciseReal(arg1);
            return Heap.allocatePreciseReal(bd0.add(bd1));
        }
        if (arg0.type == Constants.INT_TYPE && arg1.type == Constants.INT_TYPE)
        {
            int operand0 = PointerTools.dereferenceInt(arg0);
            int operand1 = PointerTools.dereferenceInt(arg1);
            return Heap.allocateInt(operand0 + operand1);
        } else if (arg0.type == Constants.STR_TYPE &&
                   arg1.type == Constants.STR_TYPE) {
            String operand0 = PointerTools.dereferenceString(arg0);
            String operand1 = PointerTools.dereferenceString(arg1);
            return Heap.allocateString(operand0 + operand1);
        } else if (arg0.type == Constants.LIST_TYPE && arg1.type ==
                   Constants.LIST_TYPE) {
            SmartList<Pointer> operand0 = PointerTools.dereferenceList(arg0);
            SmartList<Pointer> operand1 = PointerTools.dereferenceList(arg1);
            SmartList<Pointer> result = new SmartList<Pointer>();
            result.addAll(operand0);
            result.addAll(operand1);
            return Heap.allocateList(result);
        } else {
            Double operand0 = PointerTools.dereferenceReal(arg0);
            Double operand1 = PointerTools.dereferenceReal(arg1);
            if (arg0 == null || arg1 == null) {
                throw new MintException("Plus can only be applied to " + 
                                        "integers, reals, strings, or lists.");
            }
            return Heap.allocateReal(operand0 + operand1);
        }
    }
    
}
