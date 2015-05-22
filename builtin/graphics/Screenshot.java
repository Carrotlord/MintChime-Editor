package builtin.graphics;

import builtin.BuiltinSub;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import gui.Constants;
import gui.Heap;
import gui.Mint;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;


/**
 * @author Oliver Chu
 */
public class Screenshot extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        try {
            Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenBoundaries = new Rectangle(bounds);
            BufferedImage image =
                new Robot().createScreenCapture(screenBoundaries);
            return Heap.allocImg(image);
        } catch (Throwable t) {
            String asString = t.toString();
            System.err.println(asString);
            Mint.printStackTrace(t.getStackTrace());
            return Constants.MINT_NULL;
        }
    }
    
}
