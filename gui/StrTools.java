package gui;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Oliver Chu
 */
public class StrTools {
    public static final String MANA = "%$?mK!sUb&#@";

    private static String[] interleaveQuotes(String[] tokens) {
        ArrayList<String> lst = new ArrayList<String>();
        boolean useQuotes = false;
        for (String token : tokens) {
            if (useQuotes)
                lst.add("\"" + token + "\"");
            else
                lst.add(token);
            useQuotes = !useQuotes;
        }
        String[] result = new String[lst.size()];
        lst.toArray(result);
        return result;
    }
    
    private static String[] combineEscapedQuotes(String[] tokens) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.endsWith("\\") && i + 1 < tokens.length) {
                list.add(token + "\\\"" + tokens[i + 1]);
                i++;
            } else {
                list.add(token);
            }
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        // Check to make sure no more backslashes exist.
        int i = 0;
        for (String token : tokens) {
            if (token.endsWith("\\") && i != tokens.length - 1) {
                return combineEscapedQuotes(result);
            }
            i++;
        }
        return result;
    }
    
    public static String unescape(String str) {
        if (str.contains("\\")) {
            str = str.replace("\\\\", "\\");
            str = str.replace("\\t", "\t");
            str = str.replace("\\b", "\b");
            str = str.replace("\\n", "\n");
            str = str.replace("\\r", "\r");
            str = str.replace("\\f", "\f");
            str = str.replace("\\q", "\"");
            str = str.replace("\\\"", "\"");
            str = str.replace("\\'", "'");
            str = str.replace("\\a", "" + (char)7);
            str = str.replace("\\v", "" + (char)11);
            str = str.replace("\\ ", "");
        }
        return str;
    }
    
    public static String[] splitOnNoQuotes(String line,
                                           SmartList<String> operators) {
        String[] tokens = combineEscapedQuotes(line.split("\""));
        tokens = interleaveQuotes(tokens);
        ArrayList<String> lst = new ArrayList<String>();
        if (line.trim().startsWith("function")) {
            String token = line;
            int i = token.indexOf("function") + 9;
            String functionBody = StrTools2.slice(token, i);
            functionBody = functionBody.replace(" of ", "yields");
            // functionBody = functionBody.replace("yields", "a8Z");
            String fxn = functionBody.replace(" ", "");
            String parts[] = fxn.split("yields");
            if (parts.length != 3) {
                return new String[]{};
            } else {
                String functionName = parts[0];
                String argumentName = parts[1];
                String funcBody = parts[2];
                for (String op : operators) {
                    funcBody = funcBody.replace(op, " " + op + " ");
                }
                return new String[]{MANA, functionName,
                    argumentName, funcBody};
            }
        }
        for (String token : tokens) {
            if (token.contains("\"")) {
                //Do not split quoted text.
                lst.add(token);
            } else {
                token = token.replace("{", "[");
                token = token.replace("}", "]");
                for (String op : operators) {
                    token = token.replace(op, " " + op + " ");
                }
                lst.addAll(Arrays.asList(token.split(" ")));
            }
        }
        String[] result = new String[lst.size()];
        lst.toArray(result);
        return result;
    }
}
