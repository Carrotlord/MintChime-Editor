package builtin.file;

import builtin.BuiltinSub;
import java.io.File;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class FileLength extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        File f = new File(fileName);
        return Heap.allocateInt((int)f.length());
    }
    
}
