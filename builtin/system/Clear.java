package builtin.system;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class Clear extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        Runtime currentRuntime = Runtime.getRuntime();
        // Do this first. Ah...
        for (int i = 0; i < 64; ++i) {
            System.out.println();
        }
        // Then try to clear the screen for real.
        try {
            // Windows OS:
            currentRuntime.exec("cmd.exe /c cls");
        } catch (Throwable t) {
            try {
                // Windows, again...
                currentRuntime.exec(new String[]{"cmd.exe", "/c", "cls"});
            } catch (Throwable t2) {
                try {
                    // Unix, Linux, Mac, Solaris OSes...
                    currentRuntime.exec("clear");
                } catch (Throwable t3) { /* Aw, we failed. */ }
            }
        }
        return Constants.MINT_NULL;
    }
}
