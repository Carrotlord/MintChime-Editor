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
public class ShowQuestionBox extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String title = PointerTools.dereferenceString(args.get(0));
        String question = PointerTools.dereferenceString(args.get(1));
        int n = JOptionPane.showConfirmDialog(null, question, title,
                                              JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            return Constants.MINT_TRUE;
        } else {
            return Constants.MINT_FALSE;
        }
    }
    
    public static boolean javaApply(String title, String question) {
        int n = JOptionPane.showConfirmDialog(null, question, title,
                                              JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }
}
