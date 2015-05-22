/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package builtin.file;

import builtin.BuiltinSub;
import java.io.File;
import java.io.IOException;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class GetCurrentFolder extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        File folder = new File(".");
        try {
            return Heap.allocateString(folder.getCanonicalPath());
        } catch (IOException ex) {
            throw new MintException(
                      "IO Exception while getting current folder name.");
        }
    }
    
}
