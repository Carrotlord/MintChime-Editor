package builtin.graphics;

import builtin.BuiltinSub;
import java.util.ArrayList;
import gui.Heap;
import gui.MintException;
import gui.MintObject;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * @author Oliver Chu
 */
public class Polygon extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        try {
            ArrayList<Integer> dim = new ArrayList<Integer>();
            SmartList<Pointer> args2 =
                PointerTools.dereferenceList(args.get(0));
            for (Pointer p : args2) {
                dim.add(PointerTools.dereferenceInt(p));
            }
            return Heap.allocateShape(new Shape(Shape.POLYGON, dim));
        } catch (Throwable t) {
            if (args.isEmpty()) {
                return Heap.allocateObject(new MintObject());
            }
            ArrayList<Integer> dimSum = new ArrayList<Integer>();
            for (Pointer p : args) {
                dimSum.add(p.value);
            }
            return Heap.allocateShape(new Shape(Shape.POLYGON, dimSum));
        }
    }
}
