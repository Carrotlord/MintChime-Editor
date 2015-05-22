/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package builtin;

import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Constant extends BuiltinSub {
    private Pointer constant;
    
    public Constant(Pointer p) {
        constant = p;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) {
        return constant;
    }
    
}
