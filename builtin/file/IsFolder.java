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
public class IsFolder extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String folderName = PointerTools.dereferenceString(args.get(0));
        return Heap.allocateTruth(new File(folderName).isDirectory());
    }
    
}
