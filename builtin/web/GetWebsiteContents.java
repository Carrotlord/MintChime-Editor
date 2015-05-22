package builtin.web;

import builtin.BuiltinSub;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import gui.Heap;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class GetWebsiteContents extends BuiltinSub {
    /** Reads up to limit bytes of a web page.
     *  If limit is null, uses 65536.
     *  If limit is negative or 0, uses the maximum possible limit.
     */
    public static String getUrlSource(String url, Integer limit) {
        try {
            URL u = new URL(url);
            URLConnection uc = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                uc.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder a = new StringBuilder();
            long lim = limit == null ? 65536 : limit;
            if (lim <= 0) {
                lim = Long.MAX_VALUE;
            }
            while (a.length() < lim) {
                inputLine = in.readLine();
                if (inputLine == null) {
                    break;
                }
                a.append(inputLine);
                a.append("\n");
            }
            in.close();
            return a.toString();
        } catch (IOException ex) {
            return "No source code is available at " +
                   "this time.\nCheck your Internet connection.\n\n" +
                   "IO error information: " + ex.toString();
        }
    }
    private String addProtocol(String url) {
        if (!(url.startsWith("http://") || url.startsWith("https://")))
            return "http://" + url;
        return url;
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String url = PointerTools.dereferenceString(args.get(0));
        url = addProtocol(url);
        URL u;
        try {
            u = new URL(url);
        } catch (MalformedURLException ex) {
            throw new MintException("Badly formatted URL: " + url);
        }
        BufferedReader website;
        try {
            website = new BufferedReader(new InputStreamReader(u.openStream()));
        } catch (IOException ex) {
            throw new MintException("IO Exception while reading from URL: " +
                                    url);
        }
        String lines = "";
        String line = "";
        while (true) {
            try {
                line = website.readLine();
            } catch (IOException ex) {
                throw new MintException("IO Exception while reading from URL: "
                                        + url);
            }
            if (line == null)
                break;
            lines += line + "\n";
        }
        try {
            website.close();
        } catch (IOException ex) {
            throw new MintException("IO Exception while closing URL: " + url);
        }
        return Heap.allocateString(lines);
    }
    
}
