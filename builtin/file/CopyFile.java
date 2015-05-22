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
public class CopyFile extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        String fileName2 = PointerTools.dereferenceString(args.get(1));
        FileIO.copyFile(fileName, fileName2);
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
