/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package builtin.system;

import builtin.BuiltinSub;
import gui.Heap;
import gui.Interpreter;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class ViewHeap extends BuiltinSub {
    Interpreter habitat = null;
    
    public ViewHeap(Interpreter i) {
        habitat = i;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        try {
            if (habitat == null) {
                return ViewStrings.EMPTY_LIST;
            }
            String everyVariable = habitat.getEnv().toString();
            return Heap.allocateString(everyVariable);
        } catch (Throwable t) {
            return Heap.allocateString("<error: " + t + ">");
        }
    }
    
}
