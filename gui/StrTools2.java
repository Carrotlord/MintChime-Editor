package gui;

import java.util.ArrayList;
/**
 * This class serves as a fast substitute for regular expression searching
 * and parsing.
 * @author Jiangcheng Oliver Chu
 */
public class StrTools2 {
    /**
     * Searches for a substring inside a mainString, where the searching begins
     * at beginIndex. Returns the offset where the substring starts.
     * @param mainString
     * @param beginIndex
     * @param subString
     * @return
     */
    public static int search(String mainString,int beginIndex,
                             String subString) {
        //Chop off the part of the string before the beginIndex, since we don't
        //need that part.
        //Equivalent to Python's mainString = mainString[beginIndex:]
        mainString = slice(mainString,beginIndex);
        int maxLen = mainString.length();
        int subLen = subString.length();
        char firstChar = subString.charAt(0);
        for (int x=0; x+subLen <= maxLen; x++) {
            //To avoid wasting time, we make sure the first character is
            //a match, and then check the whole string.
            if (mainString.charAt(x) == firstChar) {
                String testString = slice(mainString,x,x+subLen);
                if (testString.equals(subString))
                    return beginIndex + x;
            }
        }
        return -1;
    }
    
    /**
     * Searches for a substring inside a mainString, where the searching begins
     * at beginIndex. Returns the offset where the substring starts.<br />
     * Does not pay attention to case (capitalization).
     * @param mainString
     * @param beginIndex
     * @param subString
     * @return
     */
    public static int searchIgnoreCase(String mainString,int beginIndex,
                                       String subString) {
        //Remove the part of the string before the beginIndex, since we don't
        //need that part.
        //Equivalent to Python's mainString = mainString[beginIndex:]
        mainString = slice(mainString,beginIndex);
        int maxLen = mainString.length();
        int subLen = subString.length();
        String firstChar = ""+subString.charAt(0);
        for (int x=0; x+subLen <= maxLen; x++) {
            //To avoid wasting time, we make sure the first character is
            //a match, and then check the whole string.
            String checkedChar = ""+mainString.charAt(x);
            if (checkedChar.equalsIgnoreCase(firstChar)) {
                String testString = slice(mainString,x,x+subLen);
                if (testString.equalsIgnoreCase(subString))
                    return beginIndex + x;
            }
        }
        return -1;
    }
    
    /**
     * Searches for a substring inside a mainString, where the searching begins
     * at beginIndex. Returns the offset where the substring starts.<br />
     * Does not pay attention to case (capitalization).
     * @param mainString
     * @param beginIndex
     * @param subString
     * @return
     */
    public static int searchIgnoreCaseInRange(String mainString,int beginIndex,
                                              int endIndex,
                                              String subString) {
        if (beginIndex == endIndex)
            return -1;
        int subLen = subString.length();
        String firstChar = ""+subString.charAt(0);
        for (int x = beginIndex; x+subLen <= endIndex; x++) {
            //To avoid wasting time, we make sure the first character is
            //a match, and then check the whole string.
            String checkedChar = ""+mainString.charAt(x);
            if (checkedChar.equalsIgnoreCase(firstChar)) {
                String testString = slice(mainString,x,x+subLen);
                if (testString.equalsIgnoreCase(subString))
                    return x;
            }
        }
        return -1;
    }
    
    public static int countOccurrences(String text,String subString,
                                       boolean isIgnoringCase) {
        int occurrences = 0;
        if (isIgnoringCase) {
            int position = 0; 
            while (true) {
                position = searchIgnoreCase(text,position,subString);
                if (position == -1)
                    break;
                position++;
                occurrences++;
            }
        } else {
            int position = 0;
            while (true) {
                position = search(text,position,subString);
                if (position == -1)
                    break;
                position++;
                occurrences++;
            }
        }
        return occurrences;
    }
    
    public static String multiplyChars(char character,int multiplier) {
        String output = "";
        for (int i = 0; i < multiplier; i++) {
            output += character;
        }
        return output;
    }
    
    public static int searchBackwards(String mainString,String subString) {
        int maxLen = mainString.length();
        return searchBackwards(mainString,maxLen - 1,subString);
    }
    
    public static int searchBackwardsIgnoreCase(String mainString,
                                                String subString) {
        int maxLen = mainString.length();
        return searchBackwardsIgnoreCase(mainString,maxLen - 1,subString);
    }

    /**
     * Searches for a substring inside a mainString, where the searching begins
     * at beginIndex. Returns the offset where the substring starts.<br />
     * The searching is done backwards by decrementing the beginning index.
     * @param mainString
     * @param beginIndex
     * @param subString
     * @return
     */
    public static int searchBackwards(String mainString,int startPos,
                                      String subString) {
        int subLen = subString.length();
        char firstChar = subString.charAt(0);
        for (int x = startPos; x >= 0; x--) {
            //To avoid wasting time, we make sure the first character is
            //a match, and then check the whole string.
            if (mainString.charAt(x) == firstChar) {
                String testString = StrTools2.slice(mainString,x,x+subLen);
                if (testString.equals(subString))
                    return x;
            }
        }
        return -1;
    }
    
    /**
     * Searches for a substring inside a mainString, where the searching begins
     * at beginIndex. Returns the offset where the substring starts.<br />
     * The searching is done backwards by decrementing the beginning index.
     * <br />Does not care about case or capitalization.
     * @param mainString
     * @param beginIndex
     * @param subString
     * @return
     */
    public static int searchBackwardsIgnoreCase(String mainString,int startPos,
                                                String subString) {
        int subLen = subString.length();
        String firstChar = ""+subString.charAt(0);
        for (int x = startPos; x >= 0; x--) {
            //To avoid wasting time, we make sure the first character is
            //a match, and then check the whole string.
            String checkedChar = ""+mainString.charAt(x);
            /* TESTING */
//            FileIO.appendTextStrToFile("The char: "+checkedChar+"\n"+
//                    "The string: "+StrTools.slice(mainString,x,x+subLen)+"\n","finder_debug.log");
            /*END TESTING */
            if (checkedChar.equalsIgnoreCase(firstChar)) {
                String testString = StrTools2.slice(mainString,x,x+subLen);
                if (testString.equalsIgnoreCase(subString))
                    return x;
            }
        }
        return -1;
    }
    
//    public static String removeLastLine(String text) {
//        int startOfLastLine = searchBackwards(text,"\n");
//        if (startOfLastLine == -1)
//            return text;
//        return StrTools.slice(text,0,startOfLastLine);
//    }

    public static boolean containsIgnoreCase(String text,String toBeFound) {
        int result = searchIgnoreCase(text,0,toBeFound);
        if (result == -1)
            return false;
        return true;
    }
    
    /**
     * This method converts a quoted string with escape characters in it to
     * a regular string. For example it will convert:<br />
     * "C:\\Users\\Booster" to C:\Users\Booster<br /><br />
     * Escape characters are specialized for ASM defines parsing. Here they
     * are:<br />
     * \" is " (quote)<br />
     * \\ is \<br />
     * \/ is / (for using forward-slash comments inside defines)<br />
     * \semi is ; (for using asm comments inside defines)
     * @param quotedString
     * @return simple string
     */
    public static String convertQuotedEscapedTextToStr(String quotedString) {
        //Remove the quotes first.
        quotedString = slice(quotedString,1,-1);
        // Converts \" -> "
        quotedString = quotedString.replace("\\\"","\"");
        // Converts \\ -> \
        quotedString = quotedString.replace("\\\\","\\");
        // Converts \/ -> /
        quotedString = quotedString.replace("\\/","/");
        // Converts \semi -> ;
        quotedString = quotedString.replace("\\semi",";");
        return quotedString;
    }

    /**
     * Copies the contents of a string starting from the beginning offset index
     * and ending at the first occurrence of the char wantedChar after that
     * index.
     * If the char isn't found, return the rest of the string.
     * @param mainString
     * @param index
     * @param wantedChar
     * @return Copied substring (which does not include the wantedChar itself)
     */
    public static String copyFromIndexToChar(String mainString,int index,
                                             char wantedChar)
    {
        mainString = slice(mainString,index);
        String outString = "";
        int maxLen = mainString.length();
        for (int x=0; x < maxLen; x++)
        {
            char currentChar = mainString.charAt(x);
            if (currentChar == wantedChar)
                return outString;
            outString += currentChar;
        }
        //If the char couldn't be found, return the whole rest of the string:
        return outString;
    }

    public static String copyFromIndexToStr(String mainString,int index,
                                            String wantedString)
    {
        mainString = slice(mainString,index);
        String outString = "";
        int maxLen = mainString.length();
        for (int x=0; x < maxLen; x++)
        {
            if (isSubstringAt(mainString,wantedString,x))
                return outString;
            outString += mainString.charAt(x);
        }
        //If the substr couldn't be found, return the whole rest of the string:
        return outString;
    }

    /**
     * slice() is a more intelligent version of String.substring(beginIndex,
     * endIndex)
     * If endIndex is larger than the length of mainString, maxLen is used
     * instead of endIndex.
     * If beginIndex is greater than or equal to endIndex, "" will be returned.
     * (This method is intended to emulate Python's string slicing.)<br />
     * Give a negative number for either index, and the index will count
     * backwards from the end of the string. So index = -6 really means
     * index = maxlength - 6
     * @param mainString
     * @param beginIndex
     * @param endIndex
     * @return
     */
    public static String slice(String mainString,int beginIndex,int endIndex)
    {
        int maxLen = mainString.length();
        if (beginIndex < 0)
            beginIndex = maxLen + beginIndex;
        if (endIndex < 0)
            endIndex = maxLen + endIndex;
        if (endIndex > maxLen)
            endIndex = maxLen;
        if (beginIndex >= endIndex)
            return "";
        return mainString.substring(beginIndex,endIndex);
    }

    /**
     * Returns mainString.substring(beginIndex), or returns "" if the
     * beginIndex is greater than the length of the mainString.
     * (This method is intended to emulate Python's string slicing.)<br />
     * Give a negative number for beginIndex, and the slice will count
     * BACKWARDS for the starting index.
     * That is, slice(string,-8) is string[-8:]
     * @param mainString
     * @param beginIndex
     * @return
     */
    public static String slice(String mainString,int beginIndex)
    {
        int maxLen = mainString.length();
        if (beginIndex < 0)
            return slice(mainString,maxLen+beginIndex,maxLen);
        else if (beginIndex < maxLen)
            return mainString.substring(beginIndex);
        else
            return "";
    }

    /**
     * Replaces known \n newlines with system newlines (\r\n for Windows).
     * @param instring
     * @return
     */
    public static String convertNewlines(String instring)
    {
        String newline = System.getProperty("line.separator");
        String output = instring.replace("\n",newline);
        return output;
    }

    /**
     * Converts Mac newlines (\r) or Windows newlines (\r\n)
     * into Unix newlines (\n)
     * @param instring
     * @return
     */
    public static String unixifyNewlines(String instring)
    {
        String sysNewline = System.getProperty("line.separator");
        String output = instring.replace(sysNewline,"\n");
        return output;
    }
    
    private static int countCharsInString(String string,char wantedChar) {
        char[] stringAsChars = string.toCharArray();
        int count = 0;
        for (char eachChar : stringAsChars) {
            if (eachChar == wantedChar)
                count++;
        }
        return count;
    }

    /**
     * Tests if mainString has the substr subString at the index beginIndex.
     * If not, returns false. Otherwise, returns true.
     * Useful for combing through a big string and checking for matches.
     * This method would be great inside a Find/Replace command.
     * @param mainString
     * @param subString
     * @param beginIndex
     * @return True only if subString begins at the beginIndex of mainString.
     */
    public static boolean isSubstringAt(String mainString,String subString,
                                        int beginIndex)
    {
        String testedString = slice(mainString,beginIndex,
                                    beginIndex + subString.length());
        return testedString.equals(subString);
    }
    
    /**
     * Searches text backwards until one of the strings given is found.
     * @param text
     * @param givenStrings
     * @param startPos
     * @return {position,index of the given string found}
     */
    static int[] searchBackwardsForAnyGiven(String text,String[] givenStrings,
                                            int startPos) {
        int maxStrings = givenStrings.length;
        for (int x = startPos; x >= 0; x--) {
            for (int j = 0; j < maxStrings; j++) {
                if (isSubstringAt(text,givenStrings[j],x)) {
                    int[] results = {x,j};
                    return results;
                }
            }
        }
        int[] results = {-1,-1};
        return results;
    }

    /**
     * Removes all C++ style comments from a string, as well as ASM semicolon
     * comments. Does not remove linefeeds (\n).
     * @param commentedText
     * @return text with comments gone
     */
    public static String removeCommentsAndPreserveNewlines(String commentedText)
    {
        int maxLen = commentedText.length();
        int index = 0;
        String outString = "";
        while (index < maxLen) {
            if (isSubstringAt(commentedText,"//",index)) {
                index = search(commentedText,index+2,"\n");
            } else if (isSubstringAt(commentedText,"/*",index)) {
                int newIndex = 2+search(commentedText,index+2,"*/");
                int newLineCount = countCharsInString(
                                    slice(commentedText,index+2,newIndex),'\n');
                for (int i=0; i < newLineCount; i++) {
                    outString += '\n';
                }
                index = newIndex;
            } else if (isSubstringAt(commentedText,";",index)) {
                index = search(commentedText,index+1,"\n");
            }
            if (index == -1)
                return outString;
            else if (index >= maxLen)
                break;
            else {
                outString += commentedText.charAt(index);
                index++;
            }
        }
        return outString;
    }
    
    /**
     * Removes all C++ style comments from a string, as well as ASM semicolon
     * comments.<br />
     * Do not remove this method because we need it for binary table
     * compilation.
     * @param commentedText
     * @return text with comments gone
     */
    public static String removeComments(String commentedText)
    {
        int maxLen = commentedText.length();
        int index = 0;
        String outString = "";
        while (index < maxLen) {
            if (isSubstringAt(commentedText,"//",index))
                index = search(commentedText,index+2,"\n");
            else if (isSubstringAt(commentedText,"/*",index))
                index = 2+search(commentedText,index+2,"*/");
            else if (isSubstringAt(commentedText,";",index))
                index = search(commentedText,index+1,"\n");
            
            if (index == -1)
                return outString;
            else if (index >= maxLen)
                break;
            else {
                outString += commentedText.charAt(index);
                index++;
            }
        }
        return outString;
    }

    /**
     * Removes the trailing and leading whitespace (tabs, spaces) for the
     * given string.
     * @param inString
     * @return inString with leading/trailing whitespace removed.
     */
    public static String sideStrip(String inString)
    {
        int sidesOkay = 0;
        while (sidesOkay != 2)
        {
            sidesOkay = 0;
            if (inString.startsWith(" ") || inString.startsWith("\t"))
                inString = StrTools2.slice(inString,1);
            else
                sidesOkay++;
            if (inString.endsWith(" ") || inString.endsWith("\t"))
                inString = StrTools2.slice(inString,0,-1);
            else
                sidesOkay++;
        }
        return inString;
    }

    /**
     * Removes the portion of the string from offset begin to offset end.
     * @param begin
     * @param end
     * @param inString
     * @return inString without the substring that starts at begin and ends at
     * end.
     */
    public static String excise(String inString,int begin,int end)
    {
        return slice(inString,0,begin)+slice(inString,end);
    }

    /**
     * Removes the portion of the string from offset begin to offset end, but
     * keeps the newlines that were once inside the removed portion.
     * @param begin
     * @param end
     * @param inString
     * @return inString without the substring that starts at begin and ends at
     * end, but keeps all newlines.
     */
    public static String exciseAndKeepNewlines(String inString,int begin,
                                               int end)
    {
        int newLines = countNewLines(slice(inString,begin,end));
        String newLineString = "";
        for (int x=0; x < newLines; x++) {
            newLineString += '\n';
        }
        return slice(inString,0,begin)+newLineString+slice(inString,end);
    }

    /**
     * If there are lots of newlines in a string, like "\n\n\n...", then this
     * will combine all the multiple newlines into single newlines.
     * @param inString
     * @return inString with no continuous series of newlines.
     */
    public static String consolidateNewlines(String inString)
    {
        if (inString.isEmpty())
            return inString;
        if (!inString.contains("\n\n"))
            return inString;
        String outString = "";
        int maxLength = inString.length();
        int newLineCounter = 0;
        for (int i=0; i < maxLength; i++) {
            char currentChar = inString.charAt(i);
            if (currentChar == '\n') {
                newLineCounter++;
            } else {
                //We have not encountered a newline.
                if (newLineCounter > 0) {
                    //A series of newlines was recently encountered.
                    outString += "\n"+currentChar;
                    newLineCounter = 0;
                } else {
                    outString += currentChar;
                }
            }
        }
        return outString;
    }
    
    /**
     * If there are lots of newlines in a string, like "\n\n\n...", then this
     * will combine all the multiple newlines into single newlines or double
     * newlines.
     * @param inString
     * @return inString with no continuous series of newlines greater than two
     * \n characters.
     */
    public static String consolidateNewlinesToDoubleNewlines(String inString)
    {
        if (inString.isEmpty())
            return inString;
        if (!inString.contains("\n\n\n"))
            return inString;
        String outString = "";
        int maxLength = inString.length();
        int newLineCounter = 0;
        for (int i=0; i < maxLength; i++) {
            char currentChar = inString.charAt(i);
            if (currentChar == '\n') {
                newLineCounter++;
            } else {
                //We have not encountered a newline.
                if (newLineCounter >= 2) {
                    //A series of newlines was recently encountered.
                    outString += "\n\n"+currentChar;
                    newLineCounter = 0;
                } else if (newLineCounter > 0) {
                    outString += "\n"+currentChar;
                    newLineCounter = 0;
                } else {
                    outString += currentChar;
                }
            }
        }
        return outString;
    }

    /**
     * If there are lots of spaces in a string, like "   ", then this
     * will combine all the multiple spaces into single spaces.
     * @param inString
     * @return inString with no continuous series of spaces.
     */
    public static String consolidateSpaces(String inString)
    {
        if (inString.isEmpty())
            return inString;
        if (!inString.contains("  "))
            return inString;
        String outString = "";
        int maxLength = inString.length();
        int spaceCounter = 0;
        for (int i=0; i < maxLength; i++) {
            char currentChar = inString.charAt(i);
            if (currentChar == ' ') {
                spaceCounter++;
            } else {
                //We have not encountered a space.
                if (spaceCounter > 0) {
                    //A series of spaces was recently encountered.
                    outString += " "+currentChar;
                    spaceCounter = 0;
                } else {
                    outString += currentChar;
                }
            }
        }
        return outString;
    }

    /**
     * Given some text that has newlines, this method will grab all the
     * strings "in between" those newlines and return them as a list.
     * Does not ignore blank lines.
     * @param text
     * @return List containing text chunks between the newlines.
     */
    public static ArrayList<String> splitLinesAndKeepBlankLines(String text) {
        int maxLength = text.length();
        int textIndex = 0;
        ArrayList<String> lineList = new ArrayList<String>();
        while (textIndex < maxLength) {
            String currentLine = copyFromIndexToChar(text,textIndex,'\n');
            lineList.add(currentLine);
            textIndex += currentLine.length() + 1;
        }
        return lineList;
    }

    /**
     * Given some text that has newlines, this method will grab all the
     * strings "in between" those newlines and return them as a list.
     * Ignores blank lines.
     * @param text
     * @return List containing text chunks between the newlines.
     */
    public static ArrayList<String> splitLines(String text) {
        text = consolidateNewlines(text);
        int maxLength = text.length();
        int textIndex = 0;
        ArrayList<String> lineList = new ArrayList<String>();
        while (textIndex < maxLength) {
            String currentLine = copyFromIndexToChar(text,textIndex,'\n');
            lineList.add(currentLine);
            textIndex += currentLine.length() + 1;
        }
        //Remove all blank lines, if any.
        if (lineList.contains("")) {
            boolean containsBlankLines = true;
            while (containsBlankLines) {
                containsBlankLines = lineList.remove("");
            }
        }
        return lineList;
    }

    /**
     * Given some text that has a certain character, this method will grab all
     * the strings "in between" those characters and return them as a list.
     * Ignores blank lines.
     * @param text
     * @return List containing text chunks between the characters.
     */
    public static ArrayList<String> splitTextAtChars(String text,
                                                      char character) {
        int maxLength = text.length();
        int textIndex = 0;
        ArrayList<String> lineList = new ArrayList<String>();
        while (textIndex < maxLength) {
            String currentLine = copyFromIndexToChar(text,textIndex,character);
            lineList.add(currentLine);
            textIndex += currentLine.length() + 1;
        }
        //Remove all blank lines, if any.
        if (lineList.contains("")) {
            boolean containsBlankLines = true;
            while (containsBlankLines) {
                containsBlankLines = lineList.remove("");
            }
        }
        return lineList;
    }
    
    public static int getMaxStringLength(ArrayList<String> stringList) {
        int max = 0;
        for (String eachString : stringList) {
            int currentLength = eachString.length();
            if (currentLength > max)
                max = currentLength;
        }
        return max;
    }

    /**
     * Inserts the string <i>insertion</i> at the offset <i>index</i>.
     * Appends the string to the end of the text if the index is too high.
     * @param text
     * @param insertion
     * @param index
     * @return Text with insertion inserted at index.
     */
    public static String insert(String text,String insertion,int index) {
        return slice(text,0,index) + insertion + slice(text,index);
    }

    public static int getLineNumber(String text,int position) {
        int maxLength = text.length();
        if (position > maxLength) {
            return countNewLines(text)+1;
        }
        //Negative positions are valid. They just count backwards from the
        //end of the string.
        text = slice(text,0,position);
        return countNewLines(text)+1;
    }
    
    public static int getLineNumberForDisplay(String text,int position) {
        int maxLength = text.length();
        if (position > maxLength) {
            return countNewLines(text)+1;
        }
        //Negative positions are valid. They just count backwards from the
        //end of the string.
        text = slice(text,0,position+1);
        return countNewLines(text)+1;
    }
// OLD METHOD.
//    public static int getLineNumber(String text,int position) {
//        int maxLength = text.length();
//        if (position > maxLength) {
//            return countNewLines(text)+1;
//        }
//        //Negative positions are valid. They just count backwards from the
//        //end of the string.
//        text = slice(text,position);
//        return countNewLines(text)+1;
//    }

    /**
     * Given some text, this method counts how many newlines (\n) are in it.
     * @param text
     * @return Number of Counted Newlines
     */
    public static int countNewLines(String text) {
        int maxLength = text.length();
        int counter = 0;
        for (int x=0; x < maxLength; x++) {
            if (text.charAt(x) == '\n')
                counter++;
        }
        return counter;
    }

    public static int[] getLineStartAndEnd(String text,int lineNumber) {
        if (!text.contains("\n")) {
            int[] noNewlinesArray = {0,text.length()};
            return noNewlinesArray;
        }
        int maxLength = text.length();
        int currentLine = 1;
        int startPos = -1;
        int endPos = -1;
        int lastLineStart = -1;
        int[] results = {0,0};
        for (int x=0; x < maxLength; x++) {
            if (currentLine == lineNumber && startPos == -1)
                startPos = x;
            if (currentLine == lineNumber+1 && endPos == -1)
                //Use minus 1, because we do not want to include the newline.
                endPos = x - 1;
            if (endPos != -1 && startPos != -1) {
                results[0] = startPos;
                results[1] = endPos;
                return results;
            }
            if (text.charAt(x) == '\n') {
                currentLine++;
                lastLineStart = x + 1;
            }
        }
        //We did not find the end position, so we just select the last line:
        results[0] = lastLineStart;
        results[1] = maxLength;
        //If something went horribly wrong, fix it.
        if (results[0] == -1 || results[1] == -1 || results[0]>results[1]) {
            results[0] = 0;
            results[1] = 0;
        }
        return results;
    }
    
    /**
     * Checks if the particular string array contains a certain string.
     * This method will accept partial strings too. For example:<br />
     * {"doggy","kitty","snake"} does contain "dog" because "dog" is part of
     * "doggy".
     * @param array
     * @param target
     * @return true or false
     */
    static boolean doesStringArrayContain(String[] array,String target) {
        for (String eachString : array) {
            if (eachString.contains(target))
                return true;
        }
        return false;
    }
}//END MAIN CLASS
