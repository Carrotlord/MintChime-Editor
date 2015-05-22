package gui;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A Quadruple is similar to a Java Double, except that it uses
 * 16 bytes of data to internally represent a real (floating point) number.
 * 
 * A Quadruple is a pair of 8-byte (64-bit) integers.
 * (255, -2) = 255 * 10^(-2) = 2.55
 * (255, -4) = .0255
 * (31415926535, -10) = 3.1415926535
 * 
 * Quadruples can also represent infinities.
 * When a quadruple is a non-infinity, the exponent's base is 10.
 * When a quadruple is an infinity, the exponent's base is aleph-null.
 * @author Oliver Chu
 */
public class Quadruple {
    public boolean isInfinity = false;
    public long value;
    /* Exponent represents a power of 10.
     * Or, in the case of infinities, the cardinal power of infinity.
     */
    public long exponent;
    public static Quadruple NUMBER_OF_ALL_INTEGERS = null;
    public static Quadruple NEG_NUM_OF_ALL_INTS = null;
    public static Quadruple ZERO = null;
    public static BigInteger LONG_MAX = new BigInteger("" + Long.MAX_VALUE);
    public static BigInteger LONG_MIN = new BigInteger("" + Long.MIN_VALUE);
    
    public void growForever() {
        isInfinity = true;
        value = Math.abs(value);
    }
    
    public void shrinkForever() {
        isInfinity = true;
        value = -Math.abs(value);
    }
    
    public Quadruple(long v, long e) {
        if (NUMBER_OF_ALL_INTEGERS == null) {
            NUMBER_OF_ALL_INTEGERS = new Quadruple(1, 0);
            NUMBER_OF_ALL_INTEGERS.growForever();
        }
        if (NEG_NUM_OF_ALL_INTS == null) {
            NEG_NUM_OF_ALL_INTS = new Quadruple(-1, 0);
            NEG_NUM_OF_ALL_INTS.shrinkForever();
        }
        if (ZERO == null) {
            ZERO = new Quadruple(0, 0);
        }
        value = v;
        exponent = e;
    }
    
    public double toDouble() {
        BigDecimal bd = new BigDecimal("" + value);
        BigDecimal multiplier = BigDecimal.TEN;
        multiplier = multiplier.pow((int) exponent);
        bd = bd.multiply(multiplier);
        return bd.doubleValue();
    }
    
    public String sqrt() {
        if (value == 0) {
            return "0";
        }
        if (value < 0) {
            return "imaginary number";
        }
        if (exponent != 0 && ((exponent & 1) == 0)) {
            return new Quadruple(value, exponent / 2).toString();
        }
        return "" + Math.sqrt(toDouble());
    }
    
    public Quadruple neg() {
        return new Quadruple(-value, exponent);
    }
    
    public Quadruple mul(Quadruple other) {
        long newValue = value * other.value;
        long newExp = exponent + other.exponent;
        return new Quadruple(newValue, newExp);
    }
    
    public Quadruple div(Quadruple other) {
        long newValue = (long) ((double) value / (double) other.value);
        long newExp = exponent - other.exponent;
        return new Quadruple(newValue, newExp);
    }
    
    public Quadruple add(Quadruple other) {
        long val = value;
        long exp = exponent;
        if (other.exponent == exp) {
            val += other.value;
        } else if (other.exponent > exp) {
            BigInteger self = new BigInteger("" + val);
            for (long i = 0; i < other.exponent - exp; i++) {
                self.multiply(BigInteger.TEN);
                val *= 10;
                if (self.compareTo(LONG_MAX) > 0) {
                    return NUMBER_OF_ALL_INTEGERS;
                } else if (self.compareTo(LONG_MIN) < 0) {
                    return NEG_NUM_OF_ALL_INTS;
                }
            }
        } else {
            // TODO
        }
        return new Quadruple(val, exp);
    }
    
    private String makeZeroes(byte num) {
        String answer = "";
        while (num > 0) {
            answer += '0';
            --num;
        }
        return answer;
    }
    
    @Override public String toString() {
        if (isInfinity) {
            if (value == 1) {
                return "infinity";
            } else if (value == -1) {
                return "-infinity";
            } else if (exponent == 0) {
                return "infinity of magnitude " + value;
            } else {
                return "infinity to the power of " + exponent +
                       ", of magnitude " + value;
            }
        }
        if (exponent == 0) {
            return "" + value;
        } else if (exponent > 0 && exponent < 64) {
            return ("" + value) + makeZeroes((byte) exponent);
        } else if (exponent >= 64) {
            return "" + value + "e" + exponent;
        } else if (exponent < 0) {
            BigDecimal self = new BigDecimal("" + value);
            long saved = exponent;
            while (saved < 0) {
                self.divide(BigDecimal.TEN, 200, BigDecimal.ROUND_HALF_UP);
                ++saved;
            }
            return self.toString();
        } else {
            return "undefined";
        }
    }
}
