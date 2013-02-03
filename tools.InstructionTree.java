package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is a tree diagram data structure designed for holding the
 * keywords of Java instructions. This class is not used for anything except
 * coloring syntax in the text editors.
 * Useful information:
 * http://sujitpal.blogspot.com/2006/05/java-data-structure-generic-tree.html
 * @author Oliver Chu, UC Berkeley EECS Major.
 */
public class InstructionTree {
    private static Map<Character, ArrayList<String>> branches =
                                     new HashMap<Character, ArrayList<String>>();
    private static boolean isTreeLoaded = false;
    
    public InstructionTree() {
        super();
    }
    
    public InstructionTree(ArrayList<String> stringList) {
        super();
        if (isTreeLoaded) {
            return;
        }
        ArrayList<Character> charList = collectBeginningCharacters(stringList);
        createLeaves(charList, stringList);
        //This tree can be loaded once and only once per program instance.
        isTreeLoaded = true;
    }
    
    static boolean getIsTreeLoaded() {
        return isTreeLoaded;
    }
    
    /**
     * @return {position of leaf, length of leaf} Will return -1 for both if
     * no leaf is found.
     */
    int[] findNextMnemonicLeafInText(String text,int begin) {
        int maxLength = text.length();
        return findNextMnemonicLeafInTextRange(text, begin, maxLength);
    }
    
    /**
     * @return {position of leaf, length of leaf}. Will return -1 for both if
     * no leaf is found.
     */
    int[] findNextMnemonicLeafInTextRange(String text, int begin, int end) {
        int[] results = {-1, -1};
        boolean checkNextCharForLeaf = false;
        for (int i = begin; i < end; i++) {
            char currentChar = text.charAt(i);
            //We check for i == begin just in case the mnemonic starts at
            //the beginning of a line or document, which is actually quite
            //likely.
            if (checkNextCharForLeaf || i == begin) {
                checkNextCharForLeaf = false;
                if (branches.containsKey(currentChar)) {
                    int leafLength =
                                   checkForLeafAtPosition(text,currentChar,i+1);
                    if (leafLength != -1) {
                        //Do NOT check for a trailing space because all the
                        //zero-operand mnemonics do not need trailing spaces.
                        results[0] = i;
                        results[1] = leafLength + 1;
                        return results;
                    }
                    continue;
                }
            }
            if (currentChar == ' ' || currentChar == '\n' ||
                currentChar == '|') {
                checkNextCharForLeaf = true;
            }
        }
        return results;
    }
    
    /**
     * @param text
     * @param key
     * @param pos
     * @return Length of the leaf, or -1 if no leaf is found.
     */
    private int checkForLeafAtPosition(String text,char key,int pos) {
        ArrayList<String> leaves = branches.get(key);
        for (String eachLeaf : leaves) {
            int leafLength = eachLeaf.length();
            String possibleLeaf = StrTools.slice(text, pos, pos + leafLength);
            if (possibleLeaf.equals(eachLeaf)) {
                return leafLength;
            }
        }
        return -1;
    }
    
    private void createLeaves(ArrayList<Character> charList,
                              ArrayList<String> stringList) {
        for (char eachChar : charList) {
            for (String eachString: stringList) {
                addLeafIfNeeded(eachChar, eachString);
            }
        }
        //We must sort the strings so that the biggest ones get checked first.
        for (char eachChar : charList) {
            ArrayList<String> leaves = branches.get(eachChar);
            leaves = StrSort.performCountingSort(leaves);
            branches.put(eachChar, leaves);
        }
    }

    private void addLeafIfNeeded(char character,String string) {
        if (string.length() > 0) {
            if (string.charAt(0) == character) {
                ArrayList<String> currentLeaves;
                if (branches.containsKey(character)) {
                    currentLeaves = (ArrayList<String>) branches.get(character);
                } else {
                    currentLeaves = new ArrayList<String>();
                }
                currentLeaves.add(StrTools.slice(string, 1));
                branches.put(character, currentLeaves);
            }
        }
    }

    private ArrayList<Character> collectBeginningCharacters(
                                                 ArrayList<String> stringList) {
        ArrayList<Character> charList = new ArrayList<Character>();
        for (String eachString : stringList) {
            if (eachString.length() > 0) {
                char currentChar = eachString.charAt(0);
                if (!charList.contains(currentChar))
                    charList.add(currentChar);
            }
        }
        return charList;
    }
    
    @Override
    public String toString() {
        if (isTreeLoaded) {
            String output = "";
            Set keys = branches.keySet();
            for (Object eachKey : keys) {
                char currentChar = (Character) eachKey;
                ArrayList<String> leaves = branches.get(currentChar);
                if (leaves.isEmpty()) {
                    output += currentChar + " - (no leaves)\n";
                } else {
                    output += currentChar + " - " + leaves.get(0) + '\n';
                    int maxSize = leaves.size();
                    for (int i = 1; i < maxSize; i++) {
                        output += "  - " + leaves.get(i) + '\n';
                    }
                }
            }
            return output;
        } else {
            return "(Empty Tree)";
        }
    }
}
