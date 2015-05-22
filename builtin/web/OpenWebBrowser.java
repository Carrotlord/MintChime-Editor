package builtin.web;

import builtin.BuiltinSub;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class OpenWebBrowser extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        URI uri = null;
        String u = "";
        try {
            u = PointerTools.dereferenceString(args.get(0));
            uri = new URI(u);
        } catch (URISyntaxException ex) {
            throw new MintException("Bad URI syntax: " + u);
        }
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException ex) {
            throw new MintException("IO Exception while opening URI: " + u);
        }
        return Constants.MINT_NULL;
    }
    
}
