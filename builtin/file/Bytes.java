/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package builtin.file;

import builtin.BuiltinSub;
import gui.Constants;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Bytes extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args.isEmpty()) {
            return Heap.allocateBytes(new SmartList<Byte>());
        } else if (args.get(0).type == Constants.LIST_TYPE) {
            SmartList<Pointer> pList = 
                               PointerTools.dereferenceList(args.get(0));
            SmartList<Byte> bytes = new SmartList<Byte>();
            for (Pointer p : pList) {
                bytes.add((byte)(int)PointerTools.dereferenceInt(p));
            }
            return Heap.allocateBytes(bytes);
        } else if (args.get(0).type == Constants.STR_TYPE) {
            String s = PointerTools.dereferenceString(args.get(0));
            SmartList<Byte> bytes = new SmartList<Byte>();
            for (int i = 0; i < s.length(); i++) {
                bytes.add((byte)s.charAt(i));
            }
            return Heap.allocateBytes(bytes);
        } else {
            throw new MintException("Cannot convert " + args.get(0) +
                                    " to bytes.");
        }
    }
    
}
