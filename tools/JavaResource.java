package tools;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * @author Jiangcheng Oliver Chu
 */
public class JavaResource {
    public JavaResource() {
        super();
    }
    
    public static ArrayList<String> getAll8BitRegisters() {
        ArrayList<String> registerList = new ArrayList<String>();
        return registerList;
    }
    
    public static ArrayList<String> getAll32BitRegisters() {
        ArrayList<String> registerList = new ArrayList<String>();
        return registerList;
    }
    
    public static ArrayList<String> getAll16BitRegisters() {
        ArrayList<String> registerList = new ArrayList<String>();
        return registerList;
    }
    
    public static ArrayList<String> getAllRegisters() {
        ArrayList<String> registerList = new ArrayList<String>();
        return registerList;
    }
    
    static ArrayList<String> getAllLoadPointerToSegRegCommands() {
        ArrayList<String> allLoadPtrToSegRegCommands = new ArrayList<String>();
        return allLoadPtrToSegRegCommands;
    }
    
    static ArrayList<String> getAllJumps() {
        ArrayList<String> allJumps = new ArrayList<String>();
        return allJumps;
    }
    
    private static ArrayList<String> getConditionalCommand(
                                                         String commandHeader) {
        String[] conditionalEndings = {"o","no","b","nb","e","ne","be","a","s",
                                       "ns","p","np","l","ge","le","g"};
        ArrayList<String> result = new ArrayList<String>();
        for (String eachEnding : conditionalEndings) {
            result.add(commandHeader + eachEnding);
        }
        return result;
    }
    
    public static String convertRegisterToBinary(String registerName) {
        Map<String,String> regToBinMapping = new HashMap<String,String>();
        if (!regToBinMapping.containsKey(registerName)) {
            return "ERROR";
        }
        return regToBinMapping.get(registerName);
    }
    
    public static int determineInstructionSize(String firstOperand,
                                               String firstOperandType) {
        return -1;
    }
    
    /**
     * Given some immediate data, this method returns the number of bits
     * it will fit in: either a byte, word, or dword.<br />
     * Note that 16-bit sized constant data sometimes is not allowed:<br />
     * <br />
     * <pre>
     * MOV AX,6000   ;6000 takes up only 16-bits.
     * ADD EAX,60    ;60 takes up 8-bits.
     * ADD EAX,6000  ;But now 6000 takes up 32-bits.</pre>
     * @param firstOperand
     * @return 8, 16, or 32
     */
    public static int determineImmediateSize(String operand) {
        return 0;
    }

    /**
     * Gets all the supported mnemonics.
     * This method does NOT contain zero-operand mnemonics.<br />
     * See getZeroOperandMapping() for that.
     * @return all supported mnemonics with at least 1 operand
     */
    public static ArrayList<String> getJavaKeywords() {
        ArrayList<String> kwdList = new ArrayList<String>();
        String kwds = "abstract continue for new switch assert default" +
                      " package synchronized boolean do if private this" +
                      " break double implements protected throw byte" +
                      " else import public throws case enum instanceof " +
                      "return transient catch extends int short try char " +
                      "final interface static void class finally long " +
                      "strictfp volatile float native super while cout " +
                      "cin printf puts gets struct class sub when repeat " +
                      "end yield otherwise given true false True False " +
                      "TRUE FALSE function of into yields erase all " +
                      "except null NULL length size reverse len freeze " +
                      "thaw unfreeze microwave slice sliceToEnd knife max " +
                      "min signed unsigned using namespace asm goto " +
                      "quadruple quadfloat in each repeat map filter reduce" +
                      " inherit self __init__ except as with def uber var " +
                      "list dict table object lambda defun define car cdr " +
                      "cons first rest butfirst butrest butlast last nth " +
                      "until nil NIL length size chr ord charNum begin rescue" +
                      " toCharArray split strip trim reverse to_s elsif" +
                      " and or xor not True False elif from as unless ensure" +
                      " elseif then where";
        String[] keywords = kwds.split(" ");
        kwdList.addAll(Arrays.asList(keywords));
        return kwdList;
    }
    
    static ArrayList<String> getMnemonicSynonyms() {
        ArrayList<String> synonyms = new ArrayList<String>();
        return synonyms;
    }

    /**
     * Use zeroOperandMapping.containsKey("key") to figure out if the "key"
     * is indeed a zero operand mnemonic.
     * @return
     */
    public static Map<String,String> getZeroOperandMapping() {
        Map<String,String> zeroOperandMapping = new HashMap<String,String>();
        return zeroOperandMapping;
    }

    public static ArrayList<String> getZeroOperandKeys() {
        Set keySet = getZeroOperandMapping().keySet();
        ArrayList<String> keyList = new ArrayList<String>();
        for (Object eachObj : keySet) {
            keyList.add( (String)eachObj );
        }
        return keyList;
    }
}
