package builtin.system;

import builtin.BuiltinSub;
import java.math.BigInteger;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.Rational;
import gui.SmartList;
import gui.Subprogram;

/**
 *
 * @author Oliver Chu
 */
public class SizeInBytes extends BuiltinSub {
    private static final Pointer ONE_BIT =
        Heap.allocRat(Rational.makeRat(1, 8));
    private static final Pointer NYBBLE =
        Heap.allocRat(Rational.makeRat(1, 2));
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Pointer p = new Pointer((byte) 0, 0);
        try {
            if (args == null || args.size() == 0) {
                return Constants.ZERO;
            }
            p = args.get(0);
            switch (p.type) {
                case Constants.NULL_TYPE:
                    return Constants.ZERO;
                case Constants.INT_TYPE:
                    return Heap.allocateInt(4);
                case Constants.REAL_TYPE:
                    return Heap.allocateInt(8);
                case Constants.TRUTH_TYPE:
                    return ONE_BIT;
                case Constants.BIG_INT_TYPE:
                case Constants.PRECISE_REAL_TYPE:
                    // Big integers and precise reals store
                    // their digits as 4-bit values.
                    String asStr = PointerTools.dereferenceAsString(p);
                    float fl = asStr.length();
                    fl *= 0.5;
                    if (fl < 1.0) {
                        return NYBBLE;
                    } else {
                        return Heap.allocateReal(fl);
                    }
                case Constants.RAT_TYPE:
                    return Heap.allocateInt(16);
                case Constants.STR_TYPE: {
                    String s = PointerTools.dereferenceString(p);
                    return Heap.allocateInt(s.length() + 1);
                } case Constants.BYTES_TYPE: {
                    SmartList<Byte> b = PointerTools.dereferenceBytes(p);
                    return Heap.allocateInt(b.size() + 1);
                } case Constants.LIST_TYPE:
                  case Constants.TABLE_TYPE: {
                    SmartList<Pointer> pList = PointerTools.dereferenceList(p);
                    if (pList.isEmpty()) {
                        return Constants.ZERO;
                    }
                    Double sum = 0.0;
                    // Recursively find the number of bytes in
                    // this list and all sublists.
                    for (Pointer p2 : pList) {
                        SmartList<Pointer> arguments = new SmartList<Pointer>();
                        arguments.add(p2);
                        sum += PointerTools.dereferenceReal(
                               new SizeInBytes().apply(arguments));
                    }
                    return Heap.allocateReal(sum);
                } case Constants.SUBPROGRAM_TYPE: {
                    Subprogram sb = PointerTools.dereferenceSub(p);
                    SmartList<SmartList<Pointer>> body =
                        sb.originalBody;
                    for (SmartList<Pointer> lineOfCode : body) {
                        Pointer line = Heap.allocateList(lineOfCode);
                        SmartList<Pointer> listOfOne = new SmartList<Pointer>();
                        listOfOne.add(line);
                        Double dbl = PointerTools.dereferenceReal(
                                     new SizeInBytes().apply(listOfOne));
                        Long ell = dbl.longValue();
                        return Heap.allocateBigInt(new BigInteger(
                            ell.toString()));
                    }
                }
            }
        } catch (Throwable t) {}
        return Heap.allocateInt(PointerTools.dereferenceAsString(p).length());
    }
}
