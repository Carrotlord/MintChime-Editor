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

/**
 *
 * @author Oliver Chu
 */
public class ExecuteJava extends BuiltinSub {
    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        String programName = PointerTools.dereferenceString(args.get(0));
        programName = programName.trim();
        String className = programName.replace(".java", "");
        if (!programName.endsWith(".java")) {
            programName += ".java";
        }
        try {  
            Process jv = Runtime.getRuntime().exec(
                "javac " + programName);
            Process jv2 = Runtime.getRuntime().exec(
                "java " + className);
            BufferedReader in = new BufferedReader(  
                new InputStreamReader(jv2.getInputStream()));  
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
