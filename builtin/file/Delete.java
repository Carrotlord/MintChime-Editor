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
public class Delete extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        FileIO.delete(fileName);
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
