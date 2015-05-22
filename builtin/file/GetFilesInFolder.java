/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class GetFilesInFolder extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String folderName = PointerTools.dereferenceString(args.get(0));
        File folder = new File(folderName);
        if (!folder.isDirectory()) {
            throw new MintException(folderName + " is not a folder.");
        }
        File[] fileArray = folder.listFiles();
        SmartList<Pointer> result = new SmartList<Pointer>();
        for (File f : fileArray) {
            result.add(Heap.allocateString(f.getName()));
        }
        return Heap.allocateList(result);
    }
    
}
