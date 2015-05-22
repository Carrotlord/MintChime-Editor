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
public class FileToStr extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        return Heap.allocateString(FileIO.fileToStr(fileName));
    }
    
}
