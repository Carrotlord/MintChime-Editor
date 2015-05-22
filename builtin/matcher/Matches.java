/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package builtin.matcher;

import builtin.BuiltinSub;
import gui.Constants;
import gui.MintException;
import gui.Pointer;
import gui.PointerTools;
import gui.SmartList;

/**
 * @author Oliver Chu
 */
public class Matches extends BuiltinSub {
    private static final int STAY_SAME = -128;
    private static final int DECREMENT_THIS = -64;
    private static final int DOES_NOT_MATCH = Integer.MIN_VALUE + 1;
    private static final int NORMAL_MATCH = 1023;
    private static final int MOVE_FORWARD_X_BASE = 1024;
    public static final int ANY_CHARACTER = 0;
    public static final Pointer ANY_CHAR_MATCH =
        new Pointer(Constants.MATCHER_TYPE, ANY_CHARACTER);
    public static final Pointer ANY_UPPER_OR_LOWERCASE_LETTER =
        new Pointer(Constants.MATCHER_TYPE, 1);
    public static final Pointer ANY_DECIMAL_DIGIT =
        new Pointer(Constants.MATCHER_TYPE, 2);
    public static final Pointer ANY_HEXADECIMAL_DIGIT =
        new Pointer(Constants.MATCHER_TYPE, 3);
    public static final Pointer ANY_BINARY_DIGIT =
        new Pointer(Constants.MATCHER_TYPE, 4);
    public static final Pointer ANY_UPPERCASE_LETTER =
        new Pointer(Constants.MATCHER_TYPE, 5);
    public static final Pointer ANY_OPEN_BRACE_OR_BRACKET =
        new Pointer(Constants.MATCHER_TYPE, 6);
    public static final Pointer ANY_SYMBOL =
        new Pointer(Constants.MATCHER_TYPE, 7);
    public static final Pointer ANY_CLOSE_BRACE_OR_BRACKET =
        new Pointer(Constants.MATCHER_TYPE, 8);
    public static final Pointer ANY_WHITESPACE =
        new Pointer(Constants.MATCHER_TYPE, 9);
    public static final Pointer ANY_REGEX_METACHARACTER =
        new Pointer(Constants.MATCHER_TYPE, 10);
    public static final int Z_OR_MORE_OF_NXT = 11;
    public static final Pointer ZERO_OR_MORE_OF_NEXT =
        new Pointer(Constants.MATCHER_TYPE, Z_OR_MORE_OF_NXT);
    
    public int characterMatches(char c, SmartList<Pointer> needleList) {
        if (needleList.isEmpty()) {
            return DOES_NOT_MATCH;
        }
        Pointer functor = needleList.get(0);
        switch (functor.type) {
            case Constants.STR_TYPE: {
                String function = PointerTools.dereferenceString(functor);
                if (function.length() == 1) {
                    // If we are only checking 1 character,
                    // just check for that one only.
                    if (c == function.charAt(0)) {
                        return NORMAL_MATCH;
                    } else {
                        return DOES_NOT_MATCH;
                    }
                } else {
                    // Otherwise,
                    // check if our character c
                    // is equal to the first, or the second, or the
                    // third... etc. characters of the given string.
                    for (char d : function.toCharArray()) {
                        if (c == d) {
                            return NORMAL_MATCH;
                        }
                    }
                    return DOES_NOT_MATCH;
                }
            } case Constants.MATCHER_TYPE: {
                switch (functor.value) {
                    case ANY_CHARACTER:
                        // Anything works. We don't care what the character is.
                        return NORMAL_MATCH;
                    case Z_OR_MORE_OF_NXT:
                        String nextChars =
                            PointerTools.dereferenceString(needleList.get(1));
                        if (nextChars == null) {
                            // Sorry, but this meta list-value
                            // must be followed by a string, not another
                            // matcher.
                            return DOES_NOT_MATCH;
                        }
                        if (nextChars.contains("" + c)) {
                            // We didn't find all of them yet.
                            // The for loop will increment the
                            // index for the current character,
                            // but not for the needle list.
                            return STAY_SAME;
                        } else {
                            // We successfully found all of them.
                            // So now move forward past ourselves
                            // and past the character(s) we were checking.
                            return MOVE_FORWARD_X_BASE + 2;
                        }
                    default:
                        return DOES_NOT_MATCH;
                }
            } case Constants.INT_TYPE: {
                int val = functor.value;
                if (val < 1) {
                    // We are done, so move on.
                    // The reason we are done is because the
                    // needle list has told us to "check for 0 of something"
                    // which is always true.
                    return MOVE_FORWARD_X_BASE + 2;
                } else {
                    String nextChars =
                        PointerTools.dereferenceString(needleList.get(1));
                    if (nextChars == null) {
                        // The next set of character(s)
                        // is not a Mint string!
                        // We can't do any kind of checking!
                        return DOES_NOT_MATCH;
                    } else {
                        // Found it. Next time, we have to check for 1
                        // less character.
                        if (nextChars.contains("" + c)) {
                            return DECREMENT_THIS;
                        } else {
                            // This isn't the same!
                            return DOES_NOT_MATCH;
                        }
                    }
                }
            } default:
                // The only legal list-values are...
                //
                // Mint strings of length 1 or more,
                // equivalent to the regex "[abcdef]" if you
                // have the string "abcdef"
                //
                // Integers, in which [20, "a", 30, "b"]
                // is the same as the regex "a{20}b{30}"
                //
                // Meta list-values, such as zeroOrMoreOfNext
                // or anyCharacter.
                // ["ft", zeroOrMoreOfNext, "t"] is the same as the regex
                // "[ft]t*" or "ft+".
                // ["bad", anyCharacter, anyCharacter, "apple"] is the
                // same as the regex
                // "bad.{2}apple" or "bad..apple"
                //
                // Since this particular case isn't legal, we return
                // the value DOES_NOT_MATCH.
                return DOES_NOT_MATCH;
        }
    }
    
    /** Performs a Lisp (cdr someList) for 'number' number of times,
     *  and returns the result.
     *  if the 'number' is negative, performs a (cons MINT_NULL someList)
     *  'number' number of times, and then returns that.
     */
    public SmartList<Pointer> chomp(int number, SmartList<Pointer> someList) {
        if (number == 0) {
            return someList;
        }
        if (number < 0) {
            int limit = -number;
            SmartList<Pointer> nullDriver = new SmartList<Pointer>();
            for (int i = 0; i < limit; ++i) {
                nullDriver.add(Constants.MINT_NULL);
            }
            SmartList<Pointer> nList = new SmartList<Pointer>();
            nList.addAll(nullDriver);
            nList.addAll(someList);
            return nList;
        } else {
            // We can't chomp an empty list.
            if (someList.isEmpty()) {
                return someList;
            }
            SmartList<Pointer> someOtherList = new SmartList<Pointer>();
            for (int i = number; i < someList.size(); i++) {
                someOtherList.add(someList.get(i));
            }
            return someOtherList;
        }
    }

    @Override
    public Pointer apply(SmartList<Pointer> args) throws MintException {
        if (args.size() < 2) {
            return Constants.MINT_FALSE;
        }
        String haystack = PointerTools.dereferenceString(args.get(0));
        SmartList<Pointer> needleList =
            PointerTools.dereferenceList(args.get(1));
        if (haystack == null || needleList == null) {
            return Constants.MINT_FALSE;
        }
        if (needleList.isEmpty()) {
            return Constants.MINT_FALSE;
        }
        for (char c : haystack.toCharArray()) {
            int matchCode = characterMatches(c, needleList);
            if (matchCode == NORMAL_MATCH) {
                needleList = chomp(1, needleList);
            } else if (matchCode == DOES_NOT_MATCH) {
                return Constants.MINT_FALSE;
            } else {
                if (matchCode > MOVE_FORWARD_X_BASE) {
                    needleList =
                        chomp(matchCode - MOVE_FORWARD_X_BASE, needleList);
                } else if (matchCode == DECREMENT_THIS) {
                    needleList.set(0,
                        new Pointer(Constants.INT_TYPE,
                        needleList.get(0).value - 1));
                    // We chomp because we are done checking for this
                    // number of chars.
                    if (needleList.get(0).value == 0) {
                        needleList = chomp(1, needleList);
                    }
                } // else if (STAY_SAME) { do nothing } else { do nothing }
            }
        }
        return Constants.MINT_TRUE;
    }
    
}
