package gui;

/**
 *
 * @author Oliver Chu
 */
public class ListTools {
    public static boolean isTable(SmartList<Pointer> lst) {
        for (Pointer p : lst) {
            if (p.type != Constants.LIST_TYPE ||
                PointerTools.dereferenceList(p).size() != 2)
                return false;
        }
        return true;
    }
    
    public static Table toTable(SmartList<Pointer> lst) {
        Table t = new Table();
        for (Pointer p : lst) {
            t.addBinding(PointerTools.dereferenceList(p));
        }
        return t;
    }
    
    public static int findFirst(SmartList<Pointer> list, int keyword) {
        int i = 0;
        for (Pointer p : list) {
            if (p != null && p.type == Constants.KEYWORD_TYPE &&
                keyword == p.value) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public static int findFirstType(SmartList<Pointer> list, byte type) {
        int i = 0;
        for (Pointer p : list) {
            if (p != null && p.type == type) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public static int findMatchingCloseParen(SmartList<Pointer> list,
                                             int start) {
        int openParens = 0;
        for (int i = start; i < list.size(); i++) {
            Pointer p = list.get(i);
            if (p != null && p.type == Constants.KEYWORD_TYPE) {
                if (p.value == Constants.OPEN_PAREN) {
                    openParens++;
                } else if (p.value == Constants.CLOSE_PAREN) {
                    if (openParens == 0)
                        return i;
                    openParens--;
                }
            }
        }
        return -1;
    }
    
    public static int findMatchingCloseBracket(SmartList<Pointer> list,
                                               int start) {
        int openBrackets = 0;
        for (int i = start; i < list.size(); i++) {
            Pointer p = list.get(i);
            if (p != null && p.type == Constants.KEYWORD_TYPE) {
                if (p.value == Constants.OPEN_BRACKET) {
                    openBrackets++;
                } else if (p.value == Constants.CLOSE_BRACKET) {
                    if (openBrackets == 0)
                        return i;
                    openBrackets--;
                }
            }
        }
        return -1;
    }
    
    public static int findMatchingCloseBrace(SmartList<Pointer> list,
                                             int start) {
        int openBraces = 0;
        for (int i = start; i < list.size(); i++) {
            Pointer p = list.get(i);
            if (p != null && p.type == Constants.KEYWORD_TYPE) {
                if (p.value == Constants.OPEN_BRACE) {
                    openBraces++;
                } else if (p.value == Constants.CLOSE_BRACE) {
                    if (openBraces == 0)
                        return i;
                    openBraces--;
                }
            }
        }
        return -1;
    }
    
    public static boolean containsPointer(SmartList<Pointer> list, Pointer p) {
        for (Pointer pointer : list) {
            if (pointer != null && p != null && pointer.equals(p)) {
                return true;
            }
        }
        return false;
    }
    
    public static SmartList<Pointer> removePointers(SmartList<Pointer> list,
                                                    Pointer p) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(p)) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
    
    public static int countPointers(SmartList<Pointer> list,
                                    Pointer p) {
        int count = 0;
        int openParens = 0;
        int openBrackets = 0;
        for (Pointer pointer : list) {
            if (pointer.equals(new Pointer(Constants.KEYWORD_TYPE,
                               Constants.OPEN_PAREN)))
                openParens++;
            if (pointer.equals(new Pointer(Constants.KEYWORD_TYPE,
                               Constants.CLOSE_PAREN)))
                openParens--;
            if (pointer.equals(new Pointer(Constants.KEYWORD_TYPE,
                               Constants.OPEN_BRACKET)))
                openBrackets++;
            if (pointer.equals(new Pointer(Constants.KEYWORD_TYPE,
                               Constants.CLOSE_BRACKET)))
                openBrackets--;
            if (pointer != null && p != null && pointer.equals(p) &&
                openParens == 0 && openBrackets == 0) {
                count++;
            }
        }
        return count;
    }
    
    public static SmartList<SmartList<Pointer>> splitListOnCommas(
                                                SmartList<Pointer> list) {
        SmartList<SmartList<Pointer>> splitLists =
                                      new SmartList<SmartList<Pointer>>();
        Pointer openParen =
                new Pointer(Constants.KEYWORD_TYPE, Constants.OPEN_PAREN);
        Pointer closeParen =
                new Pointer(Constants.KEYWORD_TYPE, Constants.CLOSE_PAREN);
        Pointer comma =
                new Pointer(Constants.KEYWORD_TYPE, Constants.COMMA);
        Pointer openBracket =
                new Pointer(Constants.KEYWORD_TYPE, Constants.OPEN_BRACKET);
        Pointer closeBracket = 
                new Pointer(Constants.KEYWORD_TYPE, Constants.CLOSE_BRACKET);
        int i = 0;
        SmartList<Pointer> currentList = new SmartList<Pointer>();
        int openParens = 0;
        int openBrackets = 0;
        while (i < list.size()) {
            if (list.get(i).equals(openParen)) {
                openParens++;
                currentList.add(openParen);
            } else if (list.get(i).equals(closeParen)) {
                openParens--;
                currentList.add(closeParen);
            } else if (list.get(i).equals(openBracket)) {
                openBrackets++;
                currentList.add(openBracket);
            } else if (list.get(i).equals(closeBracket)) {
                openBrackets--;
                currentList.add(closeBracket);
            } else if (list.get(i).equals(comma) && openParens <= 0 &&
                       openBrackets <= 0) {
                splitLists.add(currentList);
                currentList = new SmartList<Pointer>();
            } else {
                currentList.add(list.get(i));
            }
            i++;
        }
        splitLists.add(currentList);
        return splitLists;
    }
    
    public static int findPointerValue(SmartList<Pointer> list, Pointer p) {
        int i = 0;
        for (Pointer ptr : list) {
            if (ptr == null && p == null) {
                return i;
            }
            if (ptr != null && p != null) {
                if (ptr.toString().equals(p.toString()))
                    return i;
            }
            i++;
        }
        return -1;
    }
}
