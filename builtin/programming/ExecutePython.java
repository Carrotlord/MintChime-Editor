package builtin.programming;

import builtin.BuiltinSub;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;
import gui.StrTools2;

/**
 * @author Oliver Chu
 */
public class ExecutePython extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String version = "";
        String programName = PointerTools.dereferenceString(args.get(0));
        if (programName.startsWith("[version:")) {
            int i = programName.indexOf(']');
            String piece = StrTools2.slice(programName, 0, i);
            version = piece.replace("[version:", "").replace("]", "");
            programName = StrTools2.slice(programName, i + 1);
        }
        try {
            programName = programName.trim();
            if (!programName.endsWith(".py")) {
                programName += ".py";
            }
            Process py = Runtime.getRuntime().exec("python" +
                version + " " + programName);
            BufferedReader in = new BufferedReader(  
                new InputStreamReader(py.getInputStream()));  
            String line = null;
            int i = 0;
            int limit = Integer.MAX_VALUE >>> 1;
            while (i < limit) {
                line = in.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
                i++;
            }
        } catch (IOException ex) {
        }  
        return Constants.MINT_NULL;
    }
}
