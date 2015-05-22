package builtin.graphics;

import builtin.BuiltinSub;
import javax.swing.JOptionPane;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class ShowErrorBox extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String title = PointerTools.dereferenceString(args.get(0));
        String msg = PointerTools.dereferenceString(args.get(1));
        JOptionPane.showMessageDialog(null, msg, title,
                                      JOptionPane.ERROR_MESSAGE);
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
