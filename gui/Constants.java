package gui;

import java.util.ArrayList;

/**
 *
 * @author Oliver Chu
 */
public class Constants {
    public static final Pointer ONE = Heap.allocateInt(1);
    public static final Pointer ZERO = Heap.allocateInt(0);
    public static final Pointer NEG_ONE = Heap.allocateInt(-1);
    
    public static final byte INT_TYPE = 0;
    public static final byte REAL_TYPE = 1;
    public static final byte STR_TYPE = 2;
    public static final byte TRUTH_TYPE = 3;
    public static final byte NULL_TYPE = 4;
    public static final byte NAME_TYPE = 5;
    public static final byte KEYWORD_TYPE = 6;
    public static final byte MESSAGE_TYPE = 7;
    public static final byte OBJECT_TYPE = 8;
    public static final byte LIST_TYPE = 9;
    public static final byte SUBPROGRAM_TYPE = 10;
    public static final byte BLOCK_TYPE = 11;
    public static final byte BIG_INT_TYPE = 12;
    public static final byte PRECISE_REAL_TYPE = 13;
    public static final byte BYTES_TYPE = 14;
    public static final byte WINDOW_TYPE = 15;
    public static final byte BUTTON_TYPE = 16;
    public static final byte INTERPRETER_TYPE = 17;
    public static final byte SHAPE_TYPE = 18;
    public static final byte TABLE_TYPE = 19;
    public static final byte RAT_TYPE = 20;
    public static final byte MATCHER_TYPE = 21;
    public static final byte IMG_TYPE = 22;

    // This is in case we run out of types:
    /* -- UNCOMMENT THIS BLOCK WHEN MORE TYPES ARE NEEDED.
    public static final byte DOUBLE_POINTER_TYPE = 127;
    public static final byte RESERVED_TYPE0 = -128;
    public static final byte RESERVED_TYPE1 = -127;
    public static final byte RESERVED_TYPE2 = -126;
    public static final byte RESERVED_TYPE3 = -125;
    public static final byte RESERVED_TYPE4 = -124;
    public static final byte RESERVED_TYPE5 = -123;
    public static final byte RESERVED_TYPE6 = -122;
    public static final byte RESERVED_TYPE7 = -121;
    public static final byte RESERVED_TYPE8 = -120;
    public static final byte RESERVED_TYPE9 = -119;
    public static final byte RESERVED_TYPE10 = -118;
    public static final byte RESERVED_TYPE11 = -117;
    */
    
    public static final int PLUS = 0;
    public static final int MINUS = 1;
    public static final int MULTIPLY = 2;
    public static final int DIVIDE = 3;
    public static final int FLOOR_DIVIDE = 4;
    public static final int MODULO = 5;
    public static final int POWER = 6;
    public static final int INPUT = 7;
    public static final int OPEN_PAREN = 8;
    public static final int CLOSE_PAREN = 9;
    public static final int DOT = 10;
    public static final int EQUAL = 11;
    public static final int NOT_EQUAL = 12;
    public static final int GTR_THAN = 13;
    public static final int LESS_THAN = 14;
    public static final int GTR_OR_EQUAL = 15;
    public static final int LESS_OR_EQUAL = 16;
    public static final int QUOTE = 17;
    public static final int ASSIGN = 18;
    public static final int PRINT = 19;
    public static final int AND = 20;
    public static final int OR = 21;
    public static final int XOR = 22;
    public static final int NOT = 23;
    public static final int SHOW = 24;
    public static final int IMPORT = 25;
    public static final int INTO = 26;
    public static final int IF = 27;
    public static final int END = 28;
    public static final int ELSE = 29;
    public static final int WHILE = 30;
    public static final int CONTINUE = 31;
    public static final int BREAK = 32;
    public static final int REPEAT = 33;
    public static final int RETURN = 34;
    public static final int SUB = 35;
    public static final int WHEN = 36;
    public static final int FOR = 37;
    public static final int FOREACH = 38;
    public static final int ELSEIF = 39;
    public static final int INCREMENT = 40;
    public static final int DECREMENT = 41;
    public static final int DOUBLE_DOT = 42;
    public static final int COMMA = 43;
    public static final int OPEN_BRACKET = 44;
    public static final int CLOSE_BRACKET = 45;
    public static final int RUN = 46;
    public static final int BLOCK = 47;
    public static final int LEAVE = 48;
    public static final int INHERIT = 49;
    public static final int OF = 50;
    public static final int IN = 51;
    public static final int PLUS_ASSIGN = 52;
    public static final int MINUS_ASSIGN = 53;
    public static final int MULTIPLY_ASSIGN = 54;
    public static final int DIVIDE_ASSIGN = 55;
    public static final int MODULO_ASSIGN = 56;
    public static final int POWER_ASSIGN = 57;
    public static final int LINE_COMMENT = 58;
    public static final int ERASE = 59;
    public static final int ALL = 60;
    public static final int EXCEPT = 61;
    public static final int SWITCH = 62;
    public static final int CASE = 63;
    public static final int DEFAULT = 64;
    public static final int ELLIPSIS = 65;
    public static final int OPEN_BRACE = 66;
    public static final int CLOSE_BRACE = 67;
    public static final int GIVEN = 68;
    public static final int YIELD = 69;
    public static final int OTHERWISE = 70;
    public static final int QUESTION = 71;
    public static final int COLON = 72;
    
    public static final byte ADVANCE_BY_2 = 0;
    public static final byte CONTINUE_FALSE_IF = 1;
    public static final byte PUSH_EMPTY_PTR = 2;
    public static final byte POP_PTR = 3;
    public static final byte GO_AFTER_END_AND_POP = 4;
    public static final byte CONTINUE_TRUE_ELSEIF = 5;
    public static final byte CONTINUE_FALSE_ELSEIF = 6;
    public static final byte PUSH_PTR = 7;
    public static final byte GO_AFTER_END = 8;
    public static final byte MSG_CONTINUE = 9;
    public static final byte MSG_BREAK = 10;
    public static final byte RETURN_VALUE = 11;
    public static final byte DEFINE_SUB = 12;
    public static final byte RESET_REPEAT_AND_GO_AFTER_END = 13;
    public static final byte PUSH_PTR_AND_DECREMENT = 14;
    public static final byte DEFINE_BLOCK = 15;
    public static final byte MSG_LEAVE = 16;
    public static final byte MSG_FOR = 17;
    public static final byte MSG_FOREACH = 18;
    public static final byte MSG_SWITCH = 19;
    
    public static final ArrayList<Integer> MULT_FAMILY =
                                           getMultiplicationFamily();
    public static final ArrayList<Integer> ADD_FAMILY = getAdditionFamily();
    public static final ArrayList<Integer> OP_FAMILY = getOperatorFamily();
    public static final ArrayList<Integer> TIER0_KEYWORDS = getTier0Keywords();
    public static final ArrayList<Integer> COMP_FAMILY = getComparisonFamily();
    public static final ArrayList<Integer> LOGIC_FAMILY = getLogicFamily();
    public static final ArrayList<Integer> UNARY_FAMILY = getUnaryFamily();
    public static final ArrayList<Byte> OBJECT_LIKE = getObjectFamily();
    public static final ArrayList<String> BUILTIN_METHODS = getBuiltinMethods();
    
    public static final SmartList<Pointer> BLOCK_STARTERS = getBlockStarters();

    public static final Pointer MINT_NULL = new Pointer(Constants.NULL_TYPE, 0);
    public static final Pointer MINT_TRUE =
                                          new Pointer(Constants.TRUTH_TYPE, 1);
    public static final Pointer MINT_FALSE =
                                          new Pointer(Constants.TRUTH_TYPE, 0);
    /* Unused special constants */
    public static final Pointer MINT_NIL = new Pointer(Constants.NULL_TYPE, 1);
    public static final Pointer MINT_NONE = new Pointer(Constants.NULL_TYPE, 2);
    public static final Pointer MINT_NOTHING =
                                new Pointer(Constants.NULL_TYPE, 3);
    /* Empty is a sequence. It can be sliced and .length() / .size()
     * can be called on it.
     * VERY useful for making recursive lists!
     * Appending something to an empty will turn it into a list.
     */
    public static final Pointer MINT_EMPTY =
                                new Pointer(Constants.NULL_TYPE, 4);
    // For now, just use the empty list to substitute for empty.
    
    private static ArrayList<Integer> getMultiplicationFamily() {
        ArrayList<Integer> multFamily = new ArrayList<Integer>();
        multFamily.add(MULTIPLY);
        multFamily.add(DIVIDE);
        multFamily.add(FLOOR_DIVIDE);
        multFamily.add(MODULO);
        return multFamily;
    }
    
    private static ArrayList<Integer> getAdditionFamily() {
        ArrayList<Integer> addFamily = new ArrayList<Integer>();
        addFamily.add(PLUS);
        addFamily.add(MINUS);
        return addFamily;
    }
    
    private static ArrayList<Integer> getOperatorFamily() {
        //Cheat a little by realizing that all operators are a series
        //of consecutive integers:
        ArrayList<Integer> opFamily = new ArrayList<Integer>();
        //When you add more operators, be sure to increase 45 to something else.
        for (int i = 0; i <= 45; i++) {
            opFamily.add(i);
        }
        return opFamily;
    }
    
    private static ArrayList<Integer> getTier0Keywords() {
        ArrayList<Integer> tier0 = new ArrayList<Integer>();
        tier0.add(ASSIGN);
        tier0.add(PLUS_ASSIGN);
        tier0.add(MINUS_ASSIGN);
        tier0.add(MULTIPLY_ASSIGN);
        tier0.add(DIVIDE_ASSIGN);
        tier0.add(MODULO_ASSIGN);
        tier0.add(POWER_ASSIGN);
        tier0.add(PRINT);
        tier0.add(WHEN);
        tier0.add(IF);
        tier0.add(SHOW);
        tier0.add(IMPORT);
        tier0.add(END);
        tier0.add(ELSE);
        tier0.add(WHILE);
        tier0.add(CONTINUE);
        tier0.add(BREAK);
        tier0.add(REPEAT);
        tier0.add(RETURN);
        tier0.add(SUB);
        tier0.add(RUN);
        tier0.add(BLOCK);
        tier0.add(FOR);
        tier0.add(FOREACH);
        tier0.add(ELSEIF);
        tier0.add(INHERIT);
        tier0.add(LEAVE);
        tier0.add(INTO);
        tier0.add(IN);
        tier0.add(ERASE);
        tier0.add(ALL);
        tier0.add(EXCEPT);
        tier0.add(SWITCH);
        tier0.add(CASE);
        tier0.add(DEFAULT);
        tier0.add(GIVEN);
        tier0.add(YIELD);
        tier0.add(OTHERWISE);
        return tier0;
    }
    
    private static ArrayList<Integer> getComparisonFamily() {
        ArrayList<Integer> compFamily = new ArrayList<Integer>();
        compFamily.add(EQUAL);
        compFamily.add(NOT_EQUAL);
        compFamily.add(GTR_THAN);
        compFamily.add(LESS_THAN);
        compFamily.add(GTR_OR_EQUAL);
        compFamily.add(LESS_OR_EQUAL);
        return compFamily;
    }
    
    private static ArrayList<Integer> getLogicFamily() {
        ArrayList<Integer> logicFamily = new ArrayList<Integer>();
        logicFamily.add(AND);
        logicFamily.add(OR);
        logicFamily.add(XOR);
        return logicFamily;
    }
    
    private static SmartList<Pointer> getBlockStarters() {
        SmartList<Pointer> blockStarters = new SmartList<Pointer>();
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE, Constants.BLOCK));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE, Constants.IF));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE, Constants.WHILE));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE,
                                      Constants.REPEAT));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE, Constants.FOR));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE,
                                      Constants.FOREACH));
        blockStarters.add(new Pointer(Constants.KEYWORD_TYPE, Constants.SUB));
        return blockStarters;
    }
    
    private static ArrayList<Integer> getUnaryFamily() {
        ArrayList<Integer> unary = new ArrayList<Integer>();
        unary.add(NOT);
        unary.add(INCREMENT);
        unary.add(DECREMENT);
        return unary;
    }
    
    private static ArrayList<Byte> getObjectFamily() {
        ArrayList<Byte> objs = new ArrayList<Byte>();
        objs.add(STR_TYPE);
        objs.add(OBJECT_TYPE);
        objs.add(LIST_TYPE);
        objs.add(IMG_TYPE);
        objs.add(BYTES_TYPE);
        objs.add(WINDOW_TYPE);
        objs.add(BUTTON_TYPE);
        objs.add(INT_TYPE);
        objs.add(REAL_TYPE);
        objs.add(RAT_TYPE);
        return objs;
    }
    
    private static ArrayList<String> getBuiltinMethods() {
        ArrayList<String> methods = new ArrayList<String>();
        methods.add("length");
        methods.add("size");
        methods.add("pop");
        methods.add("split");
        methods.add("remove");
        methods.add("reverse");
        methods.add("find");
        methods.add("add");
        methods.add("append");
        methods.add("display");
        methods.add("setSize");
        methods.add("charNum");
        methods.add("get");
        methods.add("set");
        methods.add("setSize");
        methods.add("display");
        methods.add("hide");
        methods.add("setTitle");
        methods.add("pack");
        methods.add("setLocation");
        methods.add("enable");
        methods.add("disable");
        methods.add("setText");
        methods.add("setSubprogram");
        methods.add("setSize");
        methods.add("setPosition");
        methods.add("slice");
        methods.add("replaceSlice");
        methods.add("startsWith");
        methods.add("endsWith");
        methods.add("upper");
        methods.add("lower");
        methods.add("replace");
        methods.add("remove");
        methods.add("setBgColor");
        methods.add("setDrawingColor");
        methods.add("drawShape");
        methods.add("clearShapes");
        return methods;
    }
}
