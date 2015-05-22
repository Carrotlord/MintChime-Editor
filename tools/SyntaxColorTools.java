package tools;

import gui.StrTools2;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * This class is designed to color polyglot syntax in a text box for easier
 * readability.
 * @author Jiangcheng Oliver Chu
 */
public class SyntaxColorTools {
    private int numOfClosingComments = 0;
    private int numOfOpeningComments = 0;
    
    public SyntaxColorTools() {
        super();
    }

    /**
     * Adds color styles to a text editor (JTextPane only) so that it can use
     * these colors for syntax highlighting. The colors used are show below:
     * <br />
     * <ul>
     * <li>Brown = #define / #enddefine</li>
     * <li>Blue = mnemonics</li>
     * <li>Light blue = system commands</li>
     * <li>Green = comments</li>
     * <li>Purple = labels</li>
     * </ul>
     * @param textPane
     */
    public static void addStylesToTextPane(JTextPane textPane) {
        Style brownStyle = textPane.addStyle("brown",null);
        StyleConstants.setForeground(brownStyle,new Color(0,128,128));
        Style blueStyle = textPane.addStyle("blue",null);
        StyleConstants.setForeground(blueStyle,new Color(49,109,183));
        Style lightBlueStyle = textPane.addStyle("light_blue",null);
        StyleConstants.setForeground(lightBlueStyle,new Color(49,109,183));
        StyleConstants.setBold(lightBlueStyle, true);
        Style greenStyle = textPane.addStyle("green",null);
        StyleConstants.setForeground(greenStyle,new Color(21,150,0));
        Style purpleStyle = textPane.addStyle("purple",null);
        StyleConstants.setBold(purpleStyle, true);
        StyleConstants.setForeground(purpleStyle,new Color(137,0,176));
        Style grayStyle = textPane.addStyle("gray",null);
        StyleConstants.setForeground(grayStyle,new Color(112,128,144));
        Style redStyle = textPane.addStyle("red",null);
        StyleConstants.setForeground(redStyle, Color.RED);
        StyleConstants.setBold(redStyle, true);
        textPane.addStyle("empty",null);
    }
    
    public void styleJavaCodeInDocument(JTextPane textPane, int beginIndex) {
        StyledDocument document = textPane.getStyledDocument();
        String text = textPane.getText();
        removeStylesFromDocument(textPane,document,beginIndex);
        Style blueStyle = textPane.getStyle("blue");
        Style lightBlueStyle = textPane.getStyle("light_blue");
        Style brownStyle = textPane.getStyle("brown");
        Style greenStyle = textPane.getStyle("green");
        Style purpleStyle = textPane.getStyle("purple");
        Style grayStyle = textPane.getStyle("gray");
        Style redStyle = textPane.getStyle("red");
        //Color instruction mnemonics blue:
        ArrayList<String> allMnemonics;
        if (!InstructionTree.getIsTreeLoaded()) {
            allMnemonics = JavaResource.getJavaKeywords();
            allMnemonics.remove("print");
            allMnemonics.addAll(JavaResource.getMnemonicSynonyms());
            allMnemonics.addAll(JavaResource.getZeroOperandKeys());
        } else {
            allMnemonics = new ArrayList<String>();
        }
        InstructionTree treeObj = new InstructionTree(allMnemonics);
        int leafFinderIndex = beginIndex;
        String lowerCasedText = text.toLowerCase();
        while (true) {
            int[] results = treeObj.findNextMnemonicLeafInText(lowerCasedText,
                                                               leafFinderIndex);
            if (results[0] == -1) {
                break;
            }
            style(document, blueStyle, results[0], results[1]);
            leafFinderIndex = results[0] + 1;
        }
        char ch;
        for (ch = '0'; ch < ('9' + 1); ++ch) {
            styleExactWording("" + ch, brownStyle,
                              document, text, beginIndex);
        }
        //Color the comments green:
        styleBlock("//", "\n", greenStyle, document, text, beginIndex);
        styleBlock("#", "\n", greenStyle, document, text, beginIndex);
        styleExactWording(";", purpleStyle, document, text, beginIndex);
        styleExactWording("$", redStyle, document, text, beginIndex);
        styleExactWording("$s0", redStyle, document, text, beginIndex);
        styleExactWording("$s1", redStyle, document, text, beginIndex);
        styleExactWording("$s2", redStyle, document, text, beginIndex);
        styleExactWording("$s3", redStyle, document, text, beginIndex);
        styleExactWording("$s4", redStyle, document, text, beginIndex);
        styleExactWording("$s5", redStyle, document, text, beginIndex);
        styleExactWording("$s6", redStyle, document, text, beginIndex);
        styleExactWording("$s7", redStyle, document, text, beginIndex);
        styleExactWording("$zero", redStyle, document, text, beginIndex);
        styleExactWording("$t0", redStyle, document, text, beginIndex);
        styleExactWording("$t1", redStyle, document, text, beginIndex);
        styleExactWording("$t2", redStyle, document, text, beginIndex);
        styleExactWording("$t3", redStyle, document, text, beginIndex);
        styleExactWording("$t4", redStyle, document, text, beginIndex);
        styleExactWording("$t5", redStyle, document, text, beginIndex);
        styleExactWording("$t6", redStyle, document, text, beginIndex);
        styleExactWording("$t7", redStyle, document, text, beginIndex);
        styleExactWording("<?php", redStyle, document, text, beginIndex);
        styleExactWording("?>", redStyle, document, text, beginIndex);
        styleBlock("/*", "*/", greenStyle, document, text, beginIndex);
        styleBlock("(\"", "\")", grayStyle, document, text, beginIndex);
        styleBlock("('", "')", grayStyle, document, text, beginIndex);
        styleTextScripts(document, text, lightBlueStyle);
    }
    
    private void style(StyledDocument doc,Style style,int begin,int length) {
        doc.setCharacterAttributes(begin,length,style,true);
    }
    
    void styleJavaCodeInLine(JTextPane textPane,int caretPos) {
        StyledDocument document = textPane.getStyledDocument();
        String text = textPane.getText();
        int lineNum = StrTools.getLineNumber(text,caretPos);
        int[] startAndEnd = StrTools.getLineStartAndEnd(text,lineNum);
        int start = startAndEnd[0];
        int end = startAndEnd[1];
        removeStylesFromLine(textPane,document,start,end);
        Style blueStyle = textPane.getStyle("blue");
        Style lightBlueStyle = textPane.getStyle("light_blue");
        Style brownStyle = textPane.getStyle("brown");
        Style greenStyle = textPane.getStyle("green");
        Style purpleStyle = textPane.getStyle("purple");
        //Color instruction mnemonics blue:
        ArrayList<String> allMnemonics;
        if (!InstructionTree.getIsTreeLoaded()) {
            allMnemonics = JavaResource.getJavaKeywords();
            allMnemonics.remove("print");
            allMnemonics.addAll(JavaResource.getMnemonicSynonyms());
            allMnemonics.addAll(JavaResource.getZeroOperandKeys());
        } else {
            allMnemonics = new ArrayList<String>();
        }
        InstructionTree treeObj = new InstructionTree(allMnemonics);
        int leafFinderIndex = start;
        String lowerCasedText = text.toLowerCase();
        while (true) {
            int[] results = treeObj.findNextMnemonicLeafInTextRange(
                                    lowerCasedText,leafFinderIndex,end);
            if (results[0] == -1)
                break;
            style(document,blueStyle,results[0],results[1]);
            leafFinderIndex = results[0] + 1;
        }
        //Color the comments green:
        styleBlockInLine("//", "\n", greenStyle, document, text, start, end);
        styleExactWordingInLine(";", purpleStyle, document, text, start, end);
        styleBlock("/*","*/",greenStyle,document,text,0);
        styleBlockCommentClose(textPane,text,start,end);
        styleBlockCommentOpen(textPane,text,start,end);
    }
    
    private void styleBlockCommentOpen(JTextPane textPane,String text,
                                       int start,int end) {
        String currentLine = StrTools.slice(text,start,end);
        int numOfOpeningsInLine =
                              StrTools.countOccurrences(currentLine,"/*",false);
        if (numOfOpeningsInLine < numOfOpeningComments)
            undoOpenComment(textPane,text,start,end);
        numOfOpeningComments = numOfOpeningsInLine;
    }
    
    private void styleBlockCommentClose(JTextPane textPane,String text,
                                        int start,int end) {
        String currentLine = StrTools.slice(text,start,end);
        int numOfClosingsInLine =
                              StrTools.countOccurrences(currentLine,"*/",false);
        if (numOfClosingsInLine > numOfClosingComments)
            closeComment(textPane,text,end);
        numOfClosingComments = numOfClosingsInLine;
    }
    
    private void undoOpenComment(JTextPane textPane,String text,int startPos,
                                 int endPos) {
        String[] givenStrings = {"/*","*/"};
        int[] results =
                  StrTools.searchBackwardsForAnyGiven(text,givenStrings,endPos);
        //We have found another opening comment /* right before this erased
        //opening comment, so don't do anything.
        if (results[1] == 0)
            return;
        int startUncommentingPos;
        if (results[0] != -1 && results[0] > startPos)
            startUncommentingPos = results[0];
        else
            startUncommentingPos = startPos;
        styleJavaCodeInDocument(textPane,startUncommentingPos);
    }
    
    private void closeComment(JTextPane textPane,String text,int endPos) {
        //We need this if statement to prevent searchBackwards()'s .charAt()
        //method from crashing the program.
        if (endPos >= text.length())
            endPos = text.length() - 1;
        int closingPos = StrTools.searchBackwards(text,endPos,"*/");
        if (closingPos <= 0)
            closingPos = 1;
        int secondClosingPos =
                             StrTools.searchBackwards(text,closingPos - 1,"*/");
        int openingPos = StrTools.searchBackwards(text,closingPos - 1,"/*");
        //If a second closing pos exists after the opening position, then
        //it is pointless to restyle the document.
        if (secondClosingPos > openingPos)
            return;
        closingPos += 2;
        styleJavaCodeInDocument(textPane,closingPos);
    }
    
    static void removeStylesFromDocument(JTextPane textPane,
                                         StyledDocument document,int begin) {
        Style emptyStyle = textPane.getStyle("empty");
        document.setCharacterAttributes(begin,document.getLength()+1-begin,
                                        emptyStyle,true);
    }
    
    private void removeStylesFromLine(JTextPane textPane,
                                      StyledDocument document,
                                      int start,int end) {
        Style emptyStyle = textPane.getStyle("empty");
        document.setCharacterAttributes(start,end - start,emptyStyle,true);
    }
    
    private static void styleExactWording(String wording,Style style,
                                          StyledDocument doc,
                                          String text,int begin) {
        int foundPos = begin - 1;
        while (true) {
            foundPos = StrTools.searchIgnoreCase(text,foundPos+1,wording);
            if (foundPos == -1)
                break;
            doc.setCharacterAttributes(foundPos,wording.length(),style,true);
        }
    }
    
    private void styleExactWordingInLine(String wording,Style style,
                                   StyledDocument doc,String text,
                                   int start,int end) {
        int foundPos = start - 1;
        while (true) {
            foundPos = StrTools.searchIgnoreCaseInRange(text,foundPos+1,end,
                                                        wording);
            if (foundPos == -1 || foundPos > end)
                break;
            doc.setCharacterAttributes(foundPos,wording.length(),style,true);
        }
    }
    
    private void styleTextScripts(StyledDocument doc, String text,
                                  Style style) {
        int curr = -1;
        while (true) {
            curr = StrTools.searchIgnoreCase(text, curr + 1, "<");
            if (curr == -1) {
                break;
            }
            String piece = StrTools2.slice(text, curr + 1, curr + 4);
            System.out.println(piece);
            boolean isFullyUppercase =
                (piece.toUpperCase()).equals(piece);
            if (isFullyUppercase) {
                doc.setCharacterAttributes(curr, 4, style, true);
            }
        }
    }
    
    private void styleBlock(String blockStart,String blockEnd,
                            Style style,StyledDocument doc,String text,
                            int begin) {
        int beginPos = begin - 1;
        boolean isDone = false;
        while (!isDone) {
            beginPos = StrTools.searchIgnoreCase(text,
                                                 beginPos+1,blockStart);
            if (beginPos == -1)
                break;
            int endPos = StrTools.searchIgnoreCase(text,
                                                   beginPos+1,blockEnd);
            if (endPos == -1) {
                endPos = doc.getLength() - 1;
                isDone = true;
            }
            int styleLength = endPos - beginPos + blockEnd.length();
            doc.setCharacterAttributes(beginPos,styleLength,style,true);
        }
    }
    
    private void styleBlockInLine(String blockStart,String blockEnd,
                                  Style style,StyledDocument doc,
                                  String text,int start,int end) {
        int beginPos = start - 1;
        boolean isDone = false;
        while (!isDone) {
            beginPos = StrTools.searchIgnoreCaseInRange(text,
                                                     beginPos+1,end,blockStart);
            if (beginPos == -1 || beginPos > end)
                break;
            int endPos = StrTools.searchIgnoreCaseInRange(text,
                                                       beginPos+1,end,blockEnd);
            if (endPos == -1) {
                endPos = end;
                isDone = true;
            }
            int styleLength = endPos - beginPos + blockEnd.length();
            doc.setCharacterAttributes(beginPos,styleLength,style,true);
        }
    }
    
    private static void styleLabels(Style style,StyledDocument doc,
                                    JTextPane currentTextPane,int begin) {
        int beginPos = begin - 1;
        boolean isDone = false;
        String text = currentTextPane.getText();
        while (!isDone) {
            beginPos = StrTools.search(text,beginPos+1,":");
            if (beginPos == -1)
                break;
            String possibleSegReg = "";
            if (beginPos >= 2)
                possibleSegReg = StrTools.slice(text,beginPos - 2,beginPos);
            if (possibleSegReg.equalsIgnoreCase("ds") ||
                possibleSegReg.equalsIgnoreCase("ss")) {
                continue;
            }
            int[] endPositions = new int[6];
            //Labels can end with any of these characters!
            endPositions[0] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1," ");
            endPositions[1] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1,"\n");
            endPositions[2] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1,"|");
            endPositions[3] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1,"+");
            endPositions[4] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1,"-");
            endPositions[5] = StrTools.search(currentTextPane.getText(),
                                              beginPos+1,"]");
            int endPos = NumberTools.getMinimumPositiveInteger(endPositions);
            if (endPos == -1) {
                endPos = doc.getLength() - 1;
                isDone = true;
            }
            int styleLength = endPos - beginPos;
            if (styleLength < 0)
                styleLength = 0;
            doc.setCharacterAttributes(beginPos,styleLength,style,true);
        }
    }
    
    private void styleLabelsInLine(Style style,StyledDocument doc,
                                  JTextPane currentTextPane,int start,int end) {
        int beginPos = start - 1;
        boolean isDone = false;
        String text = currentTextPane.getText();
        while (!isDone) {
            beginPos = StrTools.searchIgnoreCaseInRange(text,beginPos+1,end,":");
            if (beginPos == -1)
                break;
            String possibleSegReg = "";
            if (beginPos >= 2)
                possibleSegReg = StrTools.slice(text,beginPos - 2,beginPos);
            if (possibleSegReg.equalsIgnoreCase("ds") ||
                possibleSegReg.equalsIgnoreCase("ss")) {
                continue;
            }
            int[] endPositions = new int[6];
            //Labels can end with any of these characters!
            endPositions[0] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end," ");
            endPositions[1] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end,"\n");
            endPositions[2] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end,"|");
            endPositions[3] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end,"+");
            endPositions[4] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end,"-");
            endPositions[5] = StrTools.searchIgnoreCaseInRange(
                                  currentTextPane.getText(),beginPos+1,end,"]");
            int endPos = NumberTools.getMinimumPositiveInteger(endPositions);
            if (endPos == -1) {
                endPos = end;
                isDone = true;
            }
            int styleLength = endPos - beginPos;
            if (styleLength < 0)
                styleLength = 0;
            doc.setCharacterAttributes(beginPos,styleLength,style,true);
        }
    }
}
