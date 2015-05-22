package builtin.web;

import builtin.BuiltinSub;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 *
 * @author Oliver Chu
 */
public class DownloadFile extends BuiltinSub {
    /* Thanks to:
     * <http://stackoverflow.com/questions/921262/
     * how-to-download-and-save-a-file-from-internet-using-java>
     * for their code.
     */
    private void saveUrl(String filename, String urlString)
                         throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String url = PointerTools.dereferenceString(args.get(0));
        String fileName = PointerTools.dereferenceString(args.get(1));
        try {
            saveUrl(fileName, url);
        } catch (MalformedURLException ex) {
            throw new MintException("Badly formatted URL: " + url);
        } catch (IOException ex) {
            throw new MintException(
                      "IO Exception while downloading file from " + url);
        }
        return new Pointer(Constants.NULL_TYPE, 0);
    }
    
}
