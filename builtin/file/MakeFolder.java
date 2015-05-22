package builtin.file;

import builtin.BuiltinSub;
import gui.Constants;
import gui.FileIO;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class MakeFolder extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String folderName = PointerTools.dereferenceString(args.get(0));
        FileIO.makeFolder(folderName);
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
