package gui;

import builtin.graphics.ButtonManager;
import builtin.graphics.MintWindow;
import builtin.graphics.Shape;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Oliver Chu
 */
public class Heap {
    private static HashMap<Integer, String> names;
    public static HashMap<Integer, String> strings;
    private static HashMap<Integer, Double> reals;
    private static HashMap<Integer, Message> messages;
    private static HashMap<Integer, MintObject> objects;
    private static HashMap<Integer, SmartList<Pointer>> lists;
    private static HashMap<Integer, Table> tables;
    private static HashMap<Integer, Subprogram> subprograms;
    private static HashMap<Integer, Block> blocks;
    private static HashMap<Integer, BigInteger> bigInts;
    private static HashMap<Integer, BigDecimal> preciseReals;
    private static HashMap<Integer, SmartList<Byte>> bytes;
    private static HashMap<Integer, MintWindow> windows;
    private static HashMap<Integer, ButtonManager> buttons;
    private static ArrayList<Thread> threads;
    private static HashMap<Integer, Interpreter> interpreters;
    private static HashMap<Integer, Shape> shapes;
    private static HashMap<Integer, Rational> rationals;
    private static HashMap<Integer, BufferedImage> images;
    private static int nextImageAddress;
    private static int nextNameAddress;
    private static int nextStringAddress;
    private static int nextRealAddress;
    private static int nextMessageAddress;
    private static int nextObjectAddress;
    private static int nextListAddress;
    private static int nextSubprogramAddress;
    private static int nextBlockAddress;
    private static int nextSystemVar;
    private static int nextBigIntAddress;
    private static int nextPreciseRealAddress;
    private static int nextBytesAddress;
    private static int nextWindowAddress;
    private static int nextButtonAddress;
    private static int nextInterpreterAddress;
    private static int nextShapeAddress;
    private static int nextTableAddress;
    private static int nextRationalAddress;

    /** Swaps two variable values by editing pointers. */
    public static void swapReals(Pointer first, Pointer second) {
        if (first.type != Constants.REAL_TYPE ||
            second.type != Constants.REAL_TYPE) {
            return;
        }
        int addressFirst = first.value;
        int addressSecond = second.value;
        Double valueFirst = reals.remove(addressFirst);
        Double valueSecond = reals.remove(addressSecond);
        if (valueFirst == null) {
            valueFirst = 0.0;
        }
        if (valueSecond == null) {
            valueSecond = 0.0;
        }
        /** Swap the two numbers. */
        reals.put(addressFirst, valueSecond);
        reals.put(addressSecond, valueFirst);
    }
    
    Heap() {
        names = new HashMap<Integer, String>();
        strings = new HashMap<Integer, String>();
        reals = new HashMap<Integer, Double>();
        messages = new HashMap<Integer, Message>();
        objects = new HashMap<Integer, MintObject>();
        lists = new HashMap<Integer, SmartList<Pointer>>();
        subprograms = new HashMap<Integer, Subprogram>();
        blocks = new HashMap<Integer, Block>();
        bigInts = new HashMap<Integer, BigInteger>();
        preciseReals = new HashMap<Integer, BigDecimal>();
        bytes = new HashMap<Integer, SmartList<Byte>>();
        windows = new HashMap<Integer, MintWindow>();
        buttons = new HashMap<Integer, ButtonManager>();
        threads = new ArrayList<Thread>();
        interpreters = new HashMap<Integer, Interpreter>();
        shapes = new HashMap<Integer, Shape>();
        tables = new HashMap<Integer, Table>();
        rationals = new HashMap<Integer, Rational>();
        images = new HashMap<Integer, BufferedImage>();
        nextRationalAddress = 0;
        nextBigIntAddress = 0;
        nextPreciseRealAddress = 0;
        nextNameAddress = 0;
        nextStringAddress = 0;
        nextRealAddress = 0;
        nextMessageAddress = 0;
        nextObjectAddress = 0;
        nextListAddress = 0;
        nextSubprogramAddress = 0;
        nextBlockAddress = 0;
        nextSystemVar = 0;
        nextBytesAddress = 0;
        nextWindowAddress = 0;
        nextButtonAddress = 0;
        nextInterpreterAddress = 0;
        nextShapeAddress = 0;
        nextTableAddress = 0;
        nextImageAddress = 0;
    }
    
    public static void eraseAllExcept(SmartList<Pointer> values) {
        SmartList<Byte> valueTypes = new SmartList<Byte>();
        for (Pointer p : values) {
            valueTypes.add(p.type);
        }
        //names = new HashMap<Integer, String>();
        if (!valueTypes.contains(Constants.STR_TYPE))
            strings = new HashMap<Integer, String>();
        if (!valueTypes.contains(Constants.REAL_TYPE))
            reals = new HashMap<Integer, Double>();
        if (!valueTypes.contains(Constants.MESSAGE_TYPE))
            messages = new HashMap<Integer, Message>();
        if (!valueTypes.contains(Constants.OBJECT_TYPE))
            objects = new HashMap<Integer, MintObject>();
        if (!valueTypes.contains(Constants.LIST_TYPE))
            lists = new HashMap<Integer, SmartList<Pointer>>();
        if (!valueTypes.contains(Constants.SUBPROGRAM_TYPE))
            subprograms = new HashMap<Integer, Subprogram>();
        if (!valueTypes.contains(Constants.BLOCK_TYPE))
            blocks = new HashMap<Integer, Block>();
        if (!valueTypes.contains(Constants.BIG_INT_TYPE))
            bigInts = new HashMap<Integer, BigInteger>();
        if (!valueTypes.contains(Constants.PRECISE_REAL_TYPE))
            preciseReals = new HashMap<Integer, BigDecimal>();
        if (!valueTypes.contains(Constants.BYTES_TYPE))
            bytes = new HashMap<Integer, SmartList<Byte>>();
        if (!valueTypes.contains(Constants.WINDOW_TYPE))
            windows = new HashMap<Integer, MintWindow>();
        if (!valueTypes.contains(Constants.BUTTON_TYPE))
            buttons = new HashMap<Integer, ButtonManager>();
        threads = new ArrayList<Thread>();
        if (!valueTypes.contains(Constants.INTERPRETER_TYPE))
            interpreters = new HashMap<Integer, Interpreter>();
        /* nextBigIntAddress = 0;
        nextPreciseRealAddress = 0;
        nextStringAddress = 0;
        nextRealAddress = 0;
        nextMessageAddress = 0;
        nextObjectAddress = 0;
        nextListAddress = 0;
        nextSubprogramAddress = 0;
        nextBlockAddress = 0;
        nextSystemVar = 0;
        nextBytesAddress = 0;
        nextWindowAddress = 0;
        nextButtonAddress = 0;
        nextInterpreterAddress = 0; */
    }
    
    public static void keepThread(Thread t) {
        threads.add(t);
    }
    
    public static Pointer getNextSystemVarName() {
        nextSystemVar++;
        return allocateName("$" + (nextSystemVar - 1));
    }
    
    public static void setString(int addr, String value) {
        strings.put(addr, value);
        if (addr >= nextStringAddress) {
            nextStringAddress = addr + 1;
        }
    }
    
    public static Message dereferenceMessage(int address) {
        return messages.get(address);
    }
    
    public static String dereferenceString(int address) {
        if (!strings.containsKey(address)) {
            return null;
        }
        return strings.get(address);
    }
    
    /** Returns the name at the address. Note that this doesn't evaluate
     * a name. Name evaluation is handled through environments, not the heap.
     */
    public static String dereferenceName(int address) {
        return names.get(address);
    }
    
    public static Double dereferenceReal(int address) {
        return reals.get(address);
    }
    
    public static MintObject dereferenceObject(int address) {
        return objects.get(address);
    }
    
    public static Rational derefRat(int address) {
        return rationals.get(address);
    }
    
    public static SmartList<Pointer> dereferenceList(int address) {
        return lists.get(address);
    }
    
    public static Subprogram dereferenceSub(int address) {
        return subprograms.get(address);
    }
    
    public static Block dereferenceBlock(int address) {
        return blocks.get(address);
    }
    
    public static BigInteger dereferenceBigInt(int address) {
        return bigInts.get(address);
    }
    
    public static BigDecimal dereferencePreciseReal(int address) {
        return preciseReals.get(address);
    }
    
    public static SmartList<Byte> dereferenceBytes(int address) {
        return bytes.get(address);
    }
    
    public static MintWindow dereferenceWindow(int address) {
        return windows.get(address);
    }
    
    public static ButtonManager dereferenceButton(int address) {
        return buttons.get(address);
    }
    
    public static Interpreter dereferenceInterpreter(int address) {
        return interpreters.get(address);
    }
    
    public static Table dereferenceTable(int address) {
        return tables.get(address);
    }
    
    public static Shape dereferenceShape(int address) {
        return shapes.get(address);
    }
    
    public static BufferedImage derefImg(int address) {
        return images.get(address);
    }
    
    public static Pointer allocImg(BufferedImage value) {
        images.put(nextImageAddress, value);
        nextImageAddress++;
        return new Pointer(Constants.IMG_TYPE, nextImageAddress - 1);
    }
    
    public static Pointer allocateName(String name) {
        names.put(nextNameAddress, name);
        nextNameAddress++;
        return new Pointer(Constants.NAME_TYPE, nextNameAddress - 1);
    }
    
    public static Pointer allocateReal(double value) {
        reals.put(nextRealAddress, value);
        nextRealAddress++;
        return new Pointer(Constants.REAL_TYPE, nextRealAddress - 1);
    }
    
    public static Pointer allocRat(Rational value) {
        rationals.put(nextRationalAddress, value);
        nextRationalAddress++;
        return new Pointer(Constants.RAT_TYPE, nextRationalAddress - 1);
    }
    
    public static Pointer allocateTable(Table value) {
        tables.put(nextTableAddress, value);
        nextTableAddress++;
        return new Pointer(Constants.TABLE_TYPE, nextTableAddress - 1);
    }
    
    public static Pointer allocateString(String value) {
        // String allocation is memoized,
        // exactly like how Java Strings are interned.
        for (int key : strings.keySet()) {
            if (strings.get(key).equals(value)) {
                return new Pointer(Constants.STR_TYPE, key);
            }
        }
        strings.put(nextStringAddress, value);
        nextStringAddress++;
        return new Pointer(Constants.STR_TYPE, nextStringAddress - 1);
    }
    
    public static Pointer allocateInt(int value) {
        return new Pointer(Constants.INT_TYPE, value);
    }
    
    public static Pointer allocateTruth(boolean value) {
        if (value)
            return new Pointer(Constants.TRUTH_TYPE, 1);
        return new Pointer(Constants.TRUTH_TYPE, 0);
    }
    
    public static Pointer allocateMessage(Message value) {
        messages.put(nextMessageAddress, value);
        nextMessageAddress++;
        return new Pointer(Constants.MESSAGE_TYPE, nextMessageAddress - 1);
    }
    
    public static Pointer allocateObject(MintObject value) {
        objects.put(nextObjectAddress, value);
        nextObjectAddress++;
        return new Pointer(Constants.OBJECT_TYPE, nextObjectAddress - 1);
    }
    
    public static Pointer allocateList(SmartList<Pointer> value) {
        lists.put(nextListAddress, value);
        nextListAddress++;
        return new Pointer(Constants.LIST_TYPE, nextListAddress - 1);
    }
    
    public static Pointer allocateSub(Subprogram value) {
        subprograms.put(nextSubprogramAddress, value);
        nextSubprogramAddress++;
        return new Pointer(Constants.SUBPROGRAM_TYPE,
                           nextSubprogramAddress - 1);
    }
    
    public static Pointer allocateBlock(Block value) {
        blocks.put(nextBlockAddress, value);
        nextBlockAddress++;
        return new Pointer(Constants.BLOCK_TYPE, nextBlockAddress - 1);
    }
    
    public static Pointer allocateBigInt(BigInteger value) {
        bigInts.put(nextBigIntAddress, value);
        nextBigIntAddress++;
        return new Pointer(Constants.BIG_INT_TYPE, nextBigIntAddress - 1);
    }
    
    public static Pointer allocatePreciseReal(BigDecimal value) {
        preciseReals.put(nextPreciseRealAddress, value);
        nextPreciseRealAddress++;
        return new Pointer(Constants.PRECISE_REAL_TYPE,
                           nextPreciseRealAddress - 1);
    }
    
    public static Pointer allocateBytes(SmartList<Byte> value) {
        bytes.put(nextBytesAddress, value);
        nextBytesAddress++;
        return new Pointer(Constants.BYTES_TYPE, nextBytesAddress - 1);
    }
    
    public static Pointer allocateWindow(MintWindow value) {
        windows.put(nextWindowAddress, value);
        nextWindowAddress++;
        return new Pointer(Constants.WINDOW_TYPE, nextWindowAddress - 1);
    }
    
    public static Pointer allocateButton(ButtonManager value) {
        buttons.put(nextButtonAddress, value);
        nextButtonAddress++;
        return new Pointer(Constants.BUTTON_TYPE, nextButtonAddress - 1);
    }
    
    public static Pointer allocateInterpreter(Interpreter i) {
        interpreters.put(nextInterpreterAddress, i);
        nextInterpreterAddress++;
        return new Pointer(Constants.INTERPRETER_TYPE,
                           nextInterpreterAddress - 1);
    }
    
    public static Pointer allocateShape(Shape value) {
        shapes.put(nextShapeAddress, value);
        nextShapeAddress++;
        return new Pointer(Constants.SHAPE_TYPE, nextShapeAddress - 1);
    }
    
    /** Since deallocation is somewhat buggy, this implementation is commented
     * out for now.
     */
    public static void deallocate(Pointer p) {
/*        switch (p.type) {
            case Constants.NAME_TYPE:
                unallocatedNameAddresses.add(p.value);
                names.remove(p.value);
                break;
            case Constants.REAL_TYPE:
                unallocatedRealAddresses.add(p.value);
                reals.remove(p.value);
                break;
            case Constants.STR_TYPE:
                unallocatedStringAddresses.add(p.value);
                strings.remove(p.value);
                break;
            default:
                break;
        }
*/
    }
    
    public static String realsToString() {
        return reals.toString();
    }
}
