package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author Oliver Chu, UC Berkeley EECS Major.
 */
public class NumberTools {
    
    public NumberTools() {
        super();
    }
    /** Will return the smallest positive number in the given array. Will
     *  return -1 if there are no positive integers in the array.*/
    static int getMinimumPositiveInteger(int[] intArray) {
        int min = -1;
        //Pick the first positive number in the array.
        for (int eachInt : intArray) {
            if (eachInt > 0) {
                min = eachInt;
                break;
            }
        }
        int maxLength = intArray.length;
        for (int i = 1; i < maxLength; i++) {
            if (intArray[i] < min && !(intArray[i] < 0))
                min = intArray[i];
        }
        return min;
    }

    static String convertIntListToString(ArrayList<Integer> intList) {
        int maxLength = intList.size();
        String output = "{";
        int i = 0;
        for (int eachInteger : intList) {
            output += eachInteger;
            //Are we at the last entry?
            if (i == (maxLength - 1))
                break;
            output += ",";
            i++;
        }
        return output+"}";
    }
    
    public static String bytesToHex(byte[] byteArray) {
        String hexString = "";
        for (byte eachByte : byteArray) {
            int byteAsInt = unsignByte(eachByte);
            String byteAsHex = Integer.toHexString(byteAsInt);
            if (byteAsHex.length() == 1)
                byteAsHex = "0"+byteAsHex;
            hexString += byteAsHex;
        }
        return hexString;
    }
    
    public static int offsetToInteger(String offsetHexString) {
        try {
            return Integer.parseInt(offsetHexString,16);
        } catch (NumberFormatException exceptionInfo) {
            //Offsets cannot be negative.
            return -1;
        }
    }

    public static byte[] hexToBytes(String hexString) {
        if (!isEven(hexString.length())) {
            gui.Dialog.errorBox(
                            "While trying to convert a hex string to bytes,\n" +
                            "the number of hex digits is not divisible by 2." +
                            "\n\n(Hex pairs are needed)",
                            true);
        }
        int elements = hexString.length() / 2;
        byte[] byteArray = new byte[elements];
        int y = 0;
        for (int x=0; x < elements; x++) {
            String hexPair = StrTools.slice(hexString,y,y+2);
            byte currentByte = (byte)Integer.parseInt(hexPair,16);
            byteArray[x] = currentByte;
            y += 2;
        }
        return byteArray;
    }

    public static int unsignByte(byte signedByte) {
        int byteAsInt = signedByte;
        while (byteAsInt < 0) {
            byteAsInt += 256;
        }
        return byteAsInt;
    }
    
    //Do we need this method or not?
//    public static long unsignInteger(int signedInteger) {
//        long intAsLong = signedInteger;
//        ??? TODO FINISH THIS CODE
//    }

    public static boolean isEven(int integer) {
        return ((integer % 2) == 0);
    }
    
    public static boolean isBinaryString(String binString) {
        int maxLength = binString.length();
        for (int i=0; i < maxLength; i++) {
            char currentChar = binString.charAt(i);
            if (currentChar != '1' && currentChar != '0')
                return false;
        }
        return true;
    }
    
    public static boolean isHexStringAndFitsIntoLong(String hexString) {
        try {
            //We need to use a long in case somebody uses a large number like
            //FFFF0000.
            Long.parseLong(hexString,16);
        } catch (NumberFormatException exceptionInfo) {
            return false;
        }
        return true;
    }
    
    public static boolean isHexStringOfAnyLength(String hexString) {
        ArrayList<Character> hexDigitList = new ArrayList<Character>();
        hexDigitList.add('0');
        hexDigitList.add('1');
        hexDigitList.add('2');
        hexDigitList.add('3');
        hexDigitList.add('4');
        hexDigitList.add('5');
        hexDigitList.add('6');
        hexDigitList.add('7');
        hexDigitList.add('8');
        hexDigitList.add('9');
        hexDigitList.add('a');
        hexDigitList.add('b');
        hexDigitList.add('c');
        hexDigitList.add('d');
        hexDigitList.add('e');
        hexDigitList.add('f');
        char[] hexStringAsChars = hexString.toCharArray();
        for (char eachChar : hexStringAsChars) {
            if (!hexDigitList.contains(eachChar))
                return false;
        }
        return true;
    }
    
    public static boolean canConvertBinStringToHexPairs(String binaryString) {
        return ((binaryString.length() % 8) == 0);
    }
    
    public static String convertBinStringToHexPairs(String binaryString) {
        int maxLength = binaryString.length();
        Map<String,Character> binToHexMapping = new HashMap<String,Character>();
        binToHexMapping.put("0000",'0');
        binToHexMapping.put("0001",'1');
        binToHexMapping.put("0010",'2');
        binToHexMapping.put("0011",'3');
        binToHexMapping.put("0100",'4');
        binToHexMapping.put("0101",'5');
        binToHexMapping.put("0110",'6');
        binToHexMapping.put("0111",'7');
        binToHexMapping.put("1000",'8');
        binToHexMapping.put("1001",'9');
        binToHexMapping.put("1010",'a');
        binToHexMapping.put("1011",'b');
        binToHexMapping.put("1100",'c');
        binToHexMapping.put("1101",'d');
        binToHexMapping.put("1110",'e');
        binToHexMapping.put("1111",'f');
        String hexOutput = "";
        for (int i=0; i < maxLength; i += 4) {
            String binaryChunk = StrTools.slice(binaryString,i,i+4);
            hexOutput += binToHexMapping.get(binaryChunk);
        }
        return hexOutput;
    }
    
    public static boolean isHexByte(String hexPair) {
        int possibleByte;
        try {
            possibleByte = Integer.parseInt(hexPair,16);
        } catch (NumberFormatException exceptionInfo) {
            return false;
        }
        if (possibleByte < 0)
            possibleByte += 256;
        if (possibleByte < 256 && possibleByte >= 0)
            return true;
        return false;
    }
    
    public static boolean isSignedHexByte(String hexPair) {
        int possibleByte;
        try {
            possibleByte = Integer.parseInt(hexPair,16);
        } catch (NumberFormatException exceptionInfo) {
            return false;
        }
        if (possibleByte >= -128 && possibleByte <= 127)
            return true;
        return false;
    }
    
    public static boolean isHexWord(String hexString) {
        int possibleWord;
        try {
            possibleWord = Integer.parseInt(hexString,16);
        } catch (NumberFormatException exceptionInfo) {
            return false;
        }
        if (possibleWord < 0)
            possibleWord += 65536;
        if (possibleWord < 65536)
            return true;
        return false;
    }
    
    public static String convertHexStringToBinString(String hexString) {
        int maxLength = hexString.length();
        Map<Character,String> hexToBinMapping = new HashMap<Character,String>();
        hexToBinMapping.put('0',"0000");
        hexToBinMapping.put('1',"0001");
        hexToBinMapping.put('2',"0010");
        hexToBinMapping.put('3',"0011");
        hexToBinMapping.put('4',"0100");
        hexToBinMapping.put('5',"0101");
        hexToBinMapping.put('6',"0110");
        hexToBinMapping.put('7',"0111");
        hexToBinMapping.put('8',"1000");
        hexToBinMapping.put('9',"1001");
        hexToBinMapping.put('a',"1010");
        hexToBinMapping.put('b',"1011");
        hexToBinMapping.put('c',"1100");
        hexToBinMapping.put('d',"1101");
        hexToBinMapping.put('e',"1110");
        hexToBinMapping.put('f',"1111");
        String binOutput = "";
        for (int i=0; i < maxLength; i++) {
            binOutput += hexToBinMapping.get(hexString.charAt(i));
        }
        return binOutput;
    }
    
    public static String littleEndianDword(int integer) {
        boolean isNegative = false;
        if (integer < 0)
            isNegative = true;
        String unsignedHexString;
        //.toHexString() automatically unsigns the integer. It will never
        //return a negative hexadecimal number.
        unsignedHexString = Integer.toHexString(integer);
        //We can't return numbers wider than a DWORD
        if (unsignedHexString.length() > 8)
            return "ERROR";
        //We do not need to append 'f' to the beginning of the unsigned
        //hex string. Java does that for us.
        if (!isNegative) {
            while (unsignedHexString.length() < 8) {
                unsignedHexString = '0'+unsignedHexString;
            }
        }
        String littleEndianResult =
                    StrTools.slice(unsignedHexString,6,8)+
                    StrTools.slice(unsignedHexString,4,6)+
                    StrTools.slice(unsignedHexString,2,4)+
                    StrTools.slice(unsignedHexString,0,2);
        return littleEndianResult;
    }
    
    public static String littleEndianDword(String immediateOperand) {
        if (immediateOperand.equals("-0"))
            return "00000000";
        boolean isNegative = false;
        if (immediateOperand.startsWith("-")) {
            isNegative = true;
        } else if (immediateOperand.startsWith("+")) {
            //If the user idiotically put an unnecessary plus sign in front of
            //the number, accept it anyways.
            immediateOperand = StrTools.slice(immediateOperand,1);
        }
        String unsignedHexString;
        if (isNegative) {
            int signedNumber = 0;
            try {
                signedNumber = Integer.parseInt(immediateOperand,16);
            } catch (NumberFormatException exceptionInfo) {
                return "ERROR";
            }
            //.toHexString() automatically unsigns the integer. It will never
            //return a negative hexadecimal number.
            unsignedHexString = Integer.toHexString(signedNumber);
            //We can't return numbers wider than a DWORD
            if (unsignedHexString.length() > 8)
                return "ERROR";
            //We do not need to append 'f' to the beginning of the unsigned
            //hex string. Java does that for us.
        } else {
            //The number we got must be positive.
            if (immediateOperand.length() > 8) {
                //This number is too long. Slice off the leading zeroes.
                while (immediateOperand.startsWith("0") &&
                       immediateOperand.length() > 8) {
                    immediateOperand = StrTools.slice(immediateOperand,1);
                }
                //Even after all that work, we see that the number is not
                //full of leading zeroes but instead just doesn't fit into a
                //dword.
                if (immediateOperand.length() > 8)
                    return "ERROR";
            }
            while (immediateOperand.length() < 8) {
                //Zero extend the positive number.
                immediateOperand = "0"+immediateOperand;
            }
            unsignedHexString = immediateOperand;
        }
        String littleEndianResult = StrTools.slice(unsignedHexString,6,8)+
                                    StrTools.slice(unsignedHexString,4,6)+
                                    StrTools.slice(unsignedHexString,2,4)+
                                    StrTools.slice(unsignedHexString,0,2);
        return littleEndianResult;
    }
    
    public static String littleEndianWord(String immediateOperand) {
        String littleEndianResult = littleEndianDword(immediateOperand);
        if (!(littleEndianResult.endsWith("0000") ||
              littleEndianResult.endsWith("ffff"))) {
            return "ERROR";
        }
        return StrTools.slice(littleEndianResult,0,4);
    }

    //A little endian byte is the same as a big endian byte. But since we
    //can't write a method with the identifier byte() [byte is a type], we
    //use littleEndianByte()
    public static String littleEndianByte(String immediateOperand) {
        String littleEndianResult = littleEndianDword(immediateOperand);
        if (!(littleEndianResult.endsWith("000000") ||
              littleEndianResult.endsWith("ffffff"))) {
            return "ERROR";
        }
        return StrTools.slice(littleEndianResult,0,2);
    }
}//END MAIN CLASS
