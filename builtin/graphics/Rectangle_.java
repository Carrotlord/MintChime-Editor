package builtin.graphics;

import builtin.BuiltinSub;
import java.util.ArrayList;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Rectangle_ extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        int x = PointerTools.dereferenceInt(args.get(0));
        int y = PointerTools.dereferenceInt(args.get(1));
        int width = PointerTools.dereferenceInt(args.get(2));
        int height = PointerTools.dereferenceInt(args.get(3));
        ArrayList<Integer> dim = new ArrayList<Integer>();
        dim.add(x);
        dim.add(y);
        dim.add(width);
        dim.add(height);
        return Heap.allocateShape(new Shape(Shape.RECTANGLE, dim));
    }
    
}
