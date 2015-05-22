package builtin.file;

import builtin.BuiltinSub;
import gui.FileIO;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class ReadHex extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        int begin = PointerTools.dereferenceInt(args.get(1));
        int end = PointerTools.dereferenceInt(args.get(2));
        return Heap.allocateString(FileIO.readHex(fileName, begin, end));
    }
    
}
