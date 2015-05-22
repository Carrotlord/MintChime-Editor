package builtin.web;

import builtin.BuiltinSub;
import java.net.InetAddress;
import java.net.UnknownHostException;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class GetIPAddress extends BuiltinSub {

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        InetAddress thisIP;
        try {
            thisIP = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            throw new MintException("Unknown host.");
        }
        return Heap.allocateString(thisIP.getHostAddress());
    }
    
}
