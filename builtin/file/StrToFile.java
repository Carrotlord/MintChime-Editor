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
public class StrToFile extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String fileName = PointerTools.dereferenceString(args.get(0));
        String contents = PointerTools.dereferenceString(args.get(1));
        FileIO.strToFile(contents, fileName);
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
