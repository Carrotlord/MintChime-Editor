package gui;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Oliver Chu
 */
public class Operator {
    
    public static Pointer power(Pointer a, Pointer b) throws MintException {
        if (a.type == Constants.OBJECT_TYPE &&
            b.type == Constants.OBJECT_TYPE) {
            Environment e = Mint.getOverloadEnv();
            Pointer value;
            value = e.getValue("pow");
            if (value != null && value.type != Constants.NULL_TYPE) {
                Subprogram power = PointerTools.dereferenceSub(value);
                SmartList<Pointer> arguments = new SmartList<Pointer>();
                arguments.add(a);
                arguments.add(b);
                return power.execute(e, new SmartList<String>(), arguments,
                    new Interpreter());
            }
            return Constants.MINT_NULL;
        } else if (a.type == Constants.BIG_INT_TYPE &&
            b.type == Constants.BIG_INT_TYPE) {
            int operand2 = Integer.parseInt(
                           PointerTools.dereferenceBigInt(b).toString());
            BigInteger operand1 = PointerTools.dereferenceBigInt(a);
            return Heap.allocateBigInt(operand1.pow(operand2));
        } else if (a.type == Constants.PRECISE_REAL_TYPE &&
                   b.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal operand1 = PointerTools.dereferencePreciseReal(a);
            BigDecimal operand2 = PointerTools.dereferencePreciseReal(b);
            try {
                int op2 = Integer.parseInt(operand2.toString());
                return Heap.allocatePreciseReal(operand1.pow(op2));
            } catch (NumberFormatException ex) {
                double op1 = Double.parseDouble(operand1.toString());
                double op2 = Double.parseDouble(operand2.toString());
                return Heap.allocateReal(Math.pow(op1, op2));
            }
        }
        boolean isIntResult = false;
        if (a.type == Constants.INT_TYPE && b.type == Constants.INT_TYPE) {
            isIntResult = true;
        }
        Double operand1 = PointerTools.dereferenceReal(a);
        Double operand2 = PointerTools.dereferenceReal(b);
        if (operand1 == null || operand2 == null) {
            badTypeException(Constants.POWER, a, b);
        }
        double result = Math.pow(operand1, operand2);
        if (isIntResult) {
            return Heap.allocateInt((int)result);
        }
        return Heap.allocateReal(result);
    }
    
    public static Pointer multiplicationFamily(int keyword, Pointer a,
                                               Pointer b) throws MintException {
        if (a.type == Constants.KEYWORD_TYPE ||
            a.type == Constants.NAME_TYPE) {
            return b;
        } else if (b.type == Constants.KEYWORD_TYPE ||
                   b.type == Constants.NAME_TYPE) {
            return a;
        }
        if (a.type == Constants.RAT_TYPE &&
            b.type == Constants.INT_TYPE) {
            int bee = b.value;
            b = Heap.allocRat(Rational.makeRat((long) bee, 1L));
        } else if (a.type == Constants.RAT_TYPE &&
            b.type == Constants.INT_TYPE) {
            int ey = a.value;
            a = Heap.allocRat(Rational.makeRat((long) ey, 1L));
        } else if (a.type == Constants.RAT_TYPE &&
            b.type == Constants.PRECISE_REAL_TYPE) {
            a = Heap.allocatePreciseReal(
                PointerTools.derefRat(a).toBigDecimal());
        } else if (b.type == Constants.RAT_TYPE &&
            a.type == Constants.PRECISE_REAL_TYPE) {
            b = Heap.allocatePreciseReal(
                PointerTools.derefRat(b).toBigDecimal());
        } else if (a.type == Constants.RAT_TYPE &&
                   b.type != Constants.RAT_TYPE) {
            a = Heap.allocateReal(PointerTools.derefRat(a).toDouble());
        } else if (b.type == Constants.RAT_TYPE &&
                   a.type != Constants.RAT_TYPE) {
            b = Heap.allocateReal(PointerTools.derefRat(b).toDouble());
        }
        if (a.type == Constants.OBJECT_TYPE &&
            b.type == Constants.OBJECT_TYPE) {
            Environment e = Mint.getOverloadEnv();
            Pointer value;
            switch (keyword) {
                case Constants.MULTIPLY: {
                    value = e.getValue("times");
                    break;
                } case Constants.DIVIDE: {
                    value = e.getValue("divide");
                    break;
                } case Constants.MODULO: {
                    value = e.getValue("remainder");
                    break;
                } case Constants.FLOOR_DIVIDE: {
                    value = e.getValue("divideRoundDown");
                    break;
                } default: {
                    throw new MintException(
                              "Operator not in multiplication family: " +
                              Interpreter.convertKeywordToOperator(keyword));
                }
            }
            if (value != null && value.type != Constants.NULL_TYPE) {
                Subprogram multiplication = PointerTools.dereferenceSub(value);
                SmartList<Pointer> arguments = new SmartList<Pointer>();
                arguments.add(a);
                arguments.add(b);
                return multiplication.execute(e, new SmartList<String>(),
                    arguments, new Interpreter());
            }
            return Constants.MINT_NULL;
        } else if (a.type == Constants.BIG_INT_TYPE &&
            b.type == Constants.BIG_INT_TYPE) {
            BigInteger op1 = PointerTools.dereferenceBigInt(a);
            BigInteger op2 = PointerTools.dereferenceBigInt(b);
            switch (keyword) {
                case Constants.MULTIPLY: {
                    return Heap.allocateBigInt(op1.multiply(op2));
                } case Constants.DIVIDE: {
                    BigDecimal o1 = new BigDecimal(op1);
                    BigDecimal o2 = new BigDecimal(op2);
                    return Heap.allocatePreciseReal(o1.divide(o2, 200,
                                                    RoundingMode.HALF_UP));
                } case Constants.MODULO: {
                    return Heap.allocateBigInt(op1.remainder(op2));
                } case Constants.FLOOR_DIVIDE: {
                    return Heap.allocateBigInt(op1.divide(op2));
                } default: {
                    throw new MintException(
                              "Operator not in multiplication family: " +
                              Interpreter.convertKeywordToOperator(keyword));
                }
            }
        } else if (a.type == Constants.PRECISE_REAL_TYPE &&
                   b.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal op1 = PointerTools.dereferencePreciseReal(a);
            BigDecimal op2 = PointerTools.dereferencePreciseReal(b);
            switch (keyword) {
                case Constants.MULTIPLY: {
                    return Heap.allocatePreciseReal(op1.multiply(op2));
                } case Constants.DIVIDE: {
                    return Heap.allocatePreciseReal(op1.divide(op2, 200,
                                                    RoundingMode.HALF_UP));
                } case Constants.MODULO: {
                    BigInteger o1 = new BigInteger(op1.toString());
                    BigInteger o2 = new BigInteger(op2.toString());
                    return Heap.allocateBigInt(o1.remainder(o2));
                } case Constants.FLOOR_DIVIDE: {
                    BigInteger o1 = new BigInteger(op1.toString());
                    BigInteger o2 = new BigInteger(op2.toString());
                    return Heap.allocateBigInt(o1.divide(o2));
                } default: {
                    throw new MintException(
                              "Operator not in multiplication family: " +
                              Interpreter.convertKeywordToOperator(keyword));
                }
            }
        }
        if (a.type == Constants.RAT_TYPE && b.type == Constants.RAT_TYPE) {
            Rational rat1 = PointerTools.derefRat(a);
            Rational rat2 = PointerTools.derefRat(b);
            switch (keyword) {
                case Constants.MULTIPLY: {
                    Rational ans = rat1.mul(rat2);
                    return Heap.allocRat(ans);
                } case Constants.DIVIDE: {
                    Rational ans = rat1.div(rat2);
                    return Heap.allocRat(ans);
                } case Constants.FLOOR_DIVIDE: {
                    Rational ans = rat1.div(rat2);
                    return Heap.allocateInt((int) ans.toDouble());
                } case Constants.MODULO: {
                    BigDecimal upper = rat1.toBigDecimal();
                    BigDecimal lower = rat2.toBigDecimal();
                    BigDecimal modded = upper.remainder(lower,
                                        MathContext.DECIMAL128);
                    return Heap.allocatePreciseReal(modded);
                }
            }
        }
        if (a.type == Constants.REAL_TYPE || b.type == Constants.REAL_TYPE) {
            Double operand1 = PointerTools.dereferenceReal(a);
            Double operand2 = PointerTools.dereferenceReal(b);
            if (operand1 == null || operand2 == null) {
                badTypeException(keyword, a, b);
            }
            switch (keyword) {
                case Constants.MULTIPLY: {
                    double result = operand1 * operand2;
                    return Heap.allocateReal(result);
                } case Constants.DIVIDE: {
                    double result =
                           Rational.divideCorrectly(
                           (double) operand1, (double) operand2);
                    return Heap.allocateReal(result);
                } case Constants.FLOOR_DIVIDE: {
                    double op1 = operand1;
                    double op2 = operand2;
                    int result = (int) op1 / (int) op2;
                    return Heap.allocateInt(result);
                } case Constants.MODULO: {
                    double op1 = operand1;
                    double op2 = operand2;
                    double result = op1 % op2;
                    return Heap.allocateReal(result);
                } default:
                    throw new MintException(
                              "Operator not in multiplication family: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        }
        Integer operand1 = PointerTools.dereferenceInt(a);
        Integer operand2 = PointerTools.dereferenceInt(b);
        if (operand1 == null || operand2 == null) {
            badTypeException(keyword, a, b);
        }
        switch (keyword) {
            case Constants.MULTIPLY: {
                int result = operand1 * operand2;
                return Heap.allocateInt(result);
            } case Constants.DIVIDE: {
                // To divide integers, we simply create a new
                // rational number, and don't really divide anything.
                Rational result = Rational.makeRat(
                    operand1.longValue(), operand2.longValue());
                return Heap.allocRat(result);
            } case Constants.FLOOR_DIVIDE: {
                int result = operand1 / operand2;
                return Heap.allocateInt(result);
            } case Constants.MODULO: {
                int result = operand1 % operand2;
                return Heap.allocateInt(result);
            } default:
                throw new MintException(
                    "Operator not in multiplication family: " +
                    Interpreter.convertKeywordToOperator(keyword));
        }
    }
    
    public static Pointer additionFamily(int keyword, Pointer a, Pointer b)
                                         throws MintException {
        if (a.type == Constants.RAT_TYPE && b.type == Constants.RAT_TYPE) {
            Rational ey = PointerTools.derefRat(a);
            Rational bee = PointerTools.derefRat(b);
            if (keyword == Constants.PLUS) {
                return Heap.allocRat(ey.add(bee));
            } else {
                return Heap.allocRat(ey.sub(bee));
            }
        }
        if (a.type == Constants.RAT_TYPE) {
            a = Heap.allocateReal(PointerTools.derefRat(a).toDouble());
        }
        if (b.type == Constants.RAT_TYPE) {
            b = Heap.allocateReal(PointerTools.derefRat(b).toDouble());
        }
        if (a.type == Constants.OBJECT_TYPE &&
            b.type == Constants.OBJECT_TYPE) {
            Environment e = Mint.getOverloadEnv();
            Pointer value;
            if (keyword == Constants.PLUS) {
                value = e.getValue("plus");
            } else {
                value = e.getValue("minus");
            }
            if (value != null && value.type != Constants.NULL_TYPE) {
                Subprogram addition = PointerTools.dereferenceSub(value);
                SmartList<Pointer> arguments = new SmartList<Pointer>();
                arguments.add(a);
                arguments.add(b);
                return addition.execute(e, new SmartList<String>(), arguments,
                    new Interpreter());
            }
            return Constants.MINT_NULL;
        } else if (a.type == Constants.BIG_INT_TYPE &&
            b.type == Constants.BIG_INT_TYPE) {
            BigInteger op1 = PointerTools.dereferenceBigInt(a);
            BigInteger op2 = PointerTools.dereferenceBigInt(b);
            if (keyword == Constants.PLUS) {
                return Heap.allocateBigInt(op1.add(op2));
            } else {
                return Heap.allocateBigInt(op1.subtract(op2));
            }
        } else if (a.type == Constants.PRECISE_REAL_TYPE &&
                   b.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal op1 = PointerTools.dereferencePreciseReal(a);
            BigDecimal op2 = PointerTools.dereferencePreciseReal(b);
            if (keyword == Constants.PLUS) {
                return Heap.allocatePreciseReal(op1.add(op2));
            } else {
                return Heap.allocatePreciseReal(op1.subtract(op2));
            }
        }
        if (a.type == Constants.STR_TYPE && b.type == Constants.STR_TYPE) {
            String operand1 = PointerTools.dereferenceString(a);
            String operand2 = PointerTools.dereferenceString(b);
            if (operand1 == null || operand2 == null) {
                badTypeException(keyword, a, b);
            }
            if (keyword == Constants.PLUS) {
                return Heap.allocateString(operand1 + operand2);
            } else {
                return Heap.allocateString(operand1.replace(operand2, ""));
            }
        } else if (a.type == Constants.LIST_TYPE &&
                   b.type == Constants.LIST_TYPE) {
            SmartList<Pointer> list1 = PointerTools.dereferenceList(a);
            SmartList<Pointer> list2 = PointerTools.dereferenceList(b);
            if (list1 == null || list2 == null) {
                badTypeException(keyword, a, b);
            }
            if (keyword == Constants.PLUS) {
                SmartList<Pointer> finalList = new SmartList<Pointer>();
                finalList.addAll(list1);
                finalList.addAll(list2);
                return Heap.allocateList(finalList);
            } else {
                for (Pointer pTwo : list2) {
                    int i = 0;
                    for (Pointer pOne : list1) {
                        if (PointerTools.dereferenceAsString(pOne).equals(
                            PointerTools.dereferenceAsString(pTwo))) {
                            list1.remove(i);
                        }
                        ++i;
                    }
                }
                return Heap.allocateList(list1);
            }
        } else if (a.type == Constants.REAL_TYPE ||
                   b.type == Constants.REAL_TYPE) {
            Double operand1 = PointerTools.dereferenceReal(a);
            Double operand2 = PointerTools.dereferenceReal(b);
            if (operand1 == null || operand2 == null) {
                badTypeException(keyword, a, b);
            }
            if (keyword == Constants.PLUS) {
                double result = operand1 + operand2;
                return Heap.allocateReal(result);
            } else {
                double result = operand1 - operand2;
                return Heap.allocateReal(result);
            }
        }
        Integer operand1 = PointerTools.dereferenceInt(a);
        Integer operand2 = PointerTools.dereferenceInt(b);
        if (operand1 == null || operand2 == null) {
            badTypeException(keyword, a, b);
        }
        if (keyword == Constants.PLUS) {
            int result = operand1 + operand2;
            return Heap.allocateInt(result);
        } else {
            int result = operand1 - operand2;
            return Heap.allocateInt(result);
        }
    }
    
    public static Pointer negate(Pointer p) throws MintException {
        if (p.type == Constants.RAT_TYPE) {
            Rational r = PointerTools.derefRat(p);
            if (r.d >= 0) {
                return Heap.allocRat(Rational.makeRat(-r.n, r.d));
            } else {
                /** The following two lines do NOT change
                 *  the value of the rational. We know r.d < 0 is true.
                 */
                r.d = -r.d;
                r.n = -r.n;
                return Heap.allocRat(Rational.makeRat(-r.n, r.d));
            }
        }
        if (p.type == Constants.BIG_INT_TYPE) {
            return Heap.allocateBigInt(
                   PointerTools.dereferenceBigInt(p).negate());
        } else if (p.type == Constants.PRECISE_REAL_TYPE) {
            return Heap.allocatePreciseReal(
                   PointerTools.dereferencePreciseReal(p).negate());
        }
        if (p.type == Constants.INT_TYPE) {
            return Heap.allocateInt(-p.value);
        } else if (p.type == Constants.REAL_TYPE) {
            return Heap.allocateReal(-PointerTools.dereferenceReal(p));
        } else if (p.type == Constants.STR_TYPE) {
            String text = PointerTools.dereferenceString(p);
            return Heap.allocateString(
                new StringBuffer(text).reverse().toString());
        } else {
            badTypeException(Constants.MINUS, p);
        }
        //Will not be executed:
        return null;
    }
    
    public static Pointer comparisonFamily(int keyword, Pointer a, Pointer b)
                                           throws MintException {
        if (a == null || b == null) {
            switch (keyword) {
                case Constants.EQUAL:
                    return a == b ? Constants.MINT_TRUE : Constants.MINT_FALSE;
                case Constants.NOT_EQUAL:
                    return a != b ? Constants.MINT_TRUE : Constants.MINT_FALSE;
                default: {
                    return Heap.allocateString(
                        "<error: Trying to compare Java null with " +
                        "itself or with Mint objects without " +
                        "using the == or != operators.>"
                    );
                }
            }
        }
        if (a.type == Constants.RAT_TYPE) {
            a = Heap.allocateReal(PointerTools.derefRat(a).toDouble());
        }
        if (b.type == Constants.RAT_TYPE) {
            b = Heap.allocateReal(PointerTools.derefRat(b).toDouble());
        }
        if (a.type == Constants.OBJECT_TYPE &&
            b.type == Constants.OBJECT_TYPE) {
            Environment e = Mint.getOverloadEnv();
            Pointer value;
            switch (keyword) {
                case Constants.EQUAL:
                    value = e.getValue("equal");
                    break;
                case Constants.NOT_EQUAL:
                    value = e.getValue("notEqual");
                    break;
                case Constants.GTR_THAN:
                    value = e.getValue("greater");
                    break;
                case Constants.LESS_THAN:
                    value = e.getValue("lesser");
                    break;
                case Constants.GTR_OR_EQUAL:
                    value = e.getValue("greaterEqual");
                    break;
                case Constants.LESS_OR_EQUAL:
                    value = e.getValue("lesserEqual");
                    break;
                default:
                    throw new MintException("Unknown comparison operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
            if (value != null && value.type != Constants.NULL_TYPE) {
                Subprogram power = PointerTools.dereferenceSub(value);
                SmartList<Pointer> arguments = new SmartList<Pointer>();
                arguments.add(a);
                arguments.add(b);
                return power.execute(e, new SmartList<String>(), arguments,
                    new Interpreter());
            }
            return Constants.MINT_NULL;
        } else if (a.type == Constants.BIG_INT_TYPE &&
            b.type == Constants.BIG_INT_TYPE) {
            BigInteger op1 = PointerTools.dereferenceBigInt(a);
            BigInteger op2 = PointerTools.dereferenceBigInt(b);
            switch (keyword) {
                case Constants.EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) == 0);
                case Constants.NOT_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) != 0);
                case Constants.GTR_THAN:
                    return Heap.allocateTruth(op1.compareTo(op2) > 0);
                case Constants.LESS_THAN:
                    return Heap.allocateTruth(op1.compareTo(op2) < 0);
                case Constants.GTR_OR_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) >= 0);
                case Constants.LESS_OR_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) <= 0);
                default:
                    throw new MintException("Unknown comparison operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        } else if (a.type == Constants.PRECISE_REAL_TYPE &&
                   b.type == Constants.PRECISE_REAL_TYPE) {
            BigDecimal op1 = PointerTools.dereferencePreciseReal(a);
            BigDecimal op2 = PointerTools.dereferencePreciseReal(b);
            switch (keyword) {
                case Constants.EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) == 0);
                case Constants.NOT_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) != 0);
                case Constants.GTR_THAN:
                    return Heap.allocateTruth(op1.compareTo(op2) > 0);
                case Constants.LESS_THAN:
                    return Heap.allocateTruth(op1.compareTo(op2) < 0);
                case Constants.GTR_OR_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) >= 0);
                case Constants.LESS_OR_EQUAL:
                    return Heap.allocateTruth(op1.compareTo(op2) <= 0);
                default:
                    throw new MintException("Unknown comparison operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        }
        if (a.type == Constants.STR_TYPE && b.type == Constants.STR_TYPE) {
            String oStr1 = PointerTools.dereferenceString(a);
            String oStr2 = PointerTools.dereferenceString(b);
            switch (keyword) {
                case Constants.EQUAL:
                    return Heap.allocateTruth(oStr1.equals(oStr2));
                case Constants.NOT_EQUAL:
                    return Heap.allocateTruth(!oStr1.equals(oStr2));
                case Constants.GTR_THAN:
                    return Heap.allocateTruth(oStr1.length() > oStr2.length());
                case Constants.LESS_THAN:
                    return Heap.allocateTruth(oStr1.length() < oStr2.length());
                case Constants.GTR_OR_EQUAL:
                    return Heap.allocateTruth(oStr1.length() >= oStr2.length());
                case Constants.LESS_OR_EQUAL:
                    return Heap.allocateTruth(oStr1.length() <= oStr2.length());
                default:
                    throw new MintException("Unknown comparison operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        } else if ((a.type == Constants.INT_TYPE ||
                    a.type == Constants.REAL_TYPE) &&
                   (b.type == Constants.INT_TYPE ||
                    b.type == Constants.REAL_TYPE)) {
            Double operand1 = PointerTools.dereferenceReal(a);
            Double operand2 = PointerTools.dereferenceReal(b);
            boolean areCloseEnough = Rational.closeEnough(operand1, operand2);
            switch (keyword) {
                case Constants.EQUAL:
                    return Heap.allocateTruth(areCloseEnough);
                case Constants.NOT_EQUAL:
                    return Heap.allocateTruth(!areCloseEnough);
                case Constants.GTR_THAN:
                    return Heap.allocateTruth(operand1 > operand2);
                case Constants.LESS_THAN:
                    return Heap.allocateTruth(operand1 < operand2);
                case Constants.GTR_OR_EQUAL:
                    return Heap.allocateTruth((operand1 > operand2) ||
                                              areCloseEnough);
                case Constants.LESS_OR_EQUAL:
                    return Heap.allocateTruth((operand1 < operand2) ||
                                              areCloseEnough);
                default:
                    throw new MintException("Unknown comparison operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        } else if (a.type == Constants.NULL_TYPE &&
                   b.type == Constants.NULL_TYPE) {
            if (keyword == Constants.EQUAL) {
                return Heap.allocateTruth(true);
            } else if (keyword == Constants.NOT_EQUAL) {
                return Heap.allocateTruth(false);
            }
            badTypeException(keyword, a, b);
        } else if (a.type == Constants.TRUTH_TYPE &&
                   b.type == Constants.TRUTH_TYPE) {
            Boolean operand1 = PointerTools.dereferenceTruth(a);
            Boolean operand2 = PointerTools.dereferenceTruth(b);
            if (keyword == Constants.EQUAL) {
                return Heap.allocateTruth(operand1 == operand2);
            } else if (keyword == Constants.NOT_EQUAL) {
                return Heap.allocateTruth(operand1 != operand2);
            }
            badTypeException(keyword, a, b);
        } else {
            if (keyword == Constants.EQUAL) {
                return Heap.allocateTruth(false);
            } else if (keyword == Constants.NOT_EQUAL) {
                return Heap.allocateTruth(true);
            }
        }
        badTypeException(keyword, a, b);
        // Will not be executed:
        return null;
    }
    
    private static char shiftChr(char c) {
        if (Character.isLetter(c)) {
            // Toggle 0x20, a.k.a. 32 or the space character,
            // to turn uppercase into lowercase and vice versa.
            return (char) (c ^ ' ');
        } else if (Character.isDigit(c)) {
            c -= '0';
            if (!(c >= 0 && c <= 9)) {
                return '?';
            }
            return ")!@#$%^&*(".charAt(c);
        } else {
            switch (c) {
               case '`':
                   return '~';
               case '\t':
                   return ' ';
               case '\\':
                   return '|';
               case '[':
                   return '{';
               case ']':
                   return '}';
               case ',':
                   return '<';
               case '.':
                   return '>';
               case '/':
                   return '?';
               case '-':
                   return '_';
               case '=':
                   return '+';
               default:
                   return c;
            }
        }
    }
    
    public static Pointer not(Pointer p) throws MintException {
        if (p.type == Constants.RAT_TYPE) {
            // Invert the rational number.
            Rational rat = PointerTools.derefRat(p);
            return Heap.allocRat(Rational.makeRat(rat.d, rat.n));
        }
        if (p.type == Constants.STR_TYPE) {
            String text = PointerTools.dereferenceString(p);
            String nText = "";
            for (char c : text.toCharArray()) {
                nText += shiftChr(c);
            }
            return Heap.allocateString(nText);
        }
        Double really = PointerTools.dereferenceReal(p);
        if (p.type == Constants.REAL_TYPE) {
            return Heap.allocateReal(1.0 - really);
        }
        if (p.type != Constants.TRUTH_TYPE) {
            badTypeException(Constants.NOT, p);
        }
        return Heap.allocateTruth(!PointerTools.dereferenceTruth(p));
    }
    
    public static Pointer increment(Pointer p) throws MintException {
        if (p.type == Constants.BIG_INT_TYPE) {
            return Heap.allocateBigInt(
                   PointerTools.dereferenceBigInt(p).add(BigInteger.ONE));
        } else if (p.type == Constants.PRECISE_REAL_TYPE) {
            return Heap.allocatePreciseReal(
                   PointerTools.dereferencePreciseReal(p).add(BigDecimal.ONE));
        }
        switch (p.type) {
            case Constants.INT_TYPE: {
                int x = PointerTools.dereferenceInt(p);
                return Heap.allocateInt(x + 1);
            } case Constants.REAL_TYPE: {
                double x = PointerTools.dereferenceReal(p);
                return Heap.allocateReal(x + 1);
            } case Constants.STR_TYPE: {
                return Heap.allocateString(
                       PointerTools.dereferenceString(p) + ' ');
            } default: {
                badTypeException(Constants.INCREMENT, p);
            }
        }
        return null;
    }
    
    public static Pointer decrement(Pointer p) throws MintException {
        if (p.type == Constants.BIG_INT_TYPE) {
            return Heap.allocateBigInt(
                   PointerTools.dereferenceBigInt(p).subtract(BigInteger.ONE));
        } else if (p.type == Constants.PRECISE_REAL_TYPE) {
            return Heap.allocatePreciseReal(
                   PointerTools.dereferencePreciseReal(p).subtract(
                                                          BigDecimal.ONE));
        }
        switch (p.type) {
            case Constants.STR_TYPE: {
                String text = PointerTools.dereferenceString(p);
                if (text.isEmpty()) {
                    return Heap.allocateString("");
                }
                return Heap.allocateString(text.substring(0,
                    text.length() - 1));
            } case Constants.INT_TYPE: {
                int x = PointerTools.dereferenceInt(p);
                return Heap.allocateInt(x - 1);
            } case Constants.REAL_TYPE: {
                double x = PointerTools.dereferenceReal(p);
                return Heap.allocateReal(x - 1);
            } default: {
                badTypeException(Constants.DECREMENT, p);
            }
        }
        return null;
    }
    
    public static Pointer logicFamily(int keyword, Pointer a, Pointer b) throws
                                                                 MintException {
        if (a.type == Constants.STR_TYPE && b.type == Constants.STR_TYPE) {
            String txtA = PointerTools.dereferenceString(a);
            String txtB = PointerTools.dereferenceString(b);
            // Fuzzy logic:
            switch (keyword) {
                case Constants.AND:
                    // textA and textB
                    // First part holds characters a (for each a of A)
                    // that are contained in B.
                    // Second part holds characters a (for each a of A)
                    // that are not contained in B.
                    String chrAinB = "";
                    String chrAnotInB = "";
                    for (char c : txtA.toCharArray()) {
                        if (txtB.contains("" + c)) {
                            chrAinB += c;
                        } else {
                            chrAnotInB += c;
                        }
                    }
                    SmartList<Pointer> ptrList = new SmartList<Pointer>();
                    ptrList.add(Heap.allocateString(chrAinB));
                    ptrList.add(Heap.allocateString(chrAnotInB));
                    return Heap.allocateList(ptrList);
                case Constants.OR:
                    // textA or textB returns textA if and only if
                    // textA is alphabetically before textB, such as
                    // ABCD coming before CATGIRL.
                    if (txtA.compareTo(txtB) < 0) {
                        // Don't reallocate pointers!
                        return a;
                    } else {
                        return b;
                    }
                case Constants.XOR:
                    // textA xor textB
                    // First value is a string that contains
                    // all chars a is b, with (for a in A) and (for b in B)
                    // running simultaneously.
                    // Second value is a list that contains
                    // pairs "ab" such that a != b with (for a in A) and
                    // (for b in B)
                    // running simultaneously.
                    // Ignores leftover characters.
                    // Run for a in A and for b in B at the same time:
                    int limit = Math.min(txtA.length(), txtB.length());
                    String indexMatches = "";
                    SmartList<Pointer> failedMatches = new SmartList<Pointer>();
                    for (int i = 0; i < limit; ++i) {
                        char alpha = txtA.charAt(i);
                        char beta = txtB.charAt(i);
                        if (alpha == beta) {
                            indexMatches += alpha;
                        } else {
                            failedMatches.add(
                                Heap.allocateString(alpha + "" + beta));
                        }
                    }
                    SmartList<Pointer> listOfSequences =
                        new SmartList<Pointer>();
                    listOfSequences.add(Heap.allocateString(indexMatches));
                    listOfSequences.add(Heap.allocateList(failedMatches));
                    return Heap.allocateList(listOfSequences);
                default:
                    throw new MintException("Unknown logical operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
            // textA or textB
            // extracts only the characters that do not match.
            

        }
        Double aDbl = PointerTools.dereferenceReal(a);
        Double bDbl = PointerTools.dereferenceReal(b);
        if (aDbl != null && bDbl != null) {
            // Fuzzy logic:
            switch (keyword) {
                case Constants.AND:
                    return Heap.allocateReal(Math.max(aDbl, bDbl));
                case Constants.OR:
                    return Heap.allocateReal(Math.min(aDbl, bDbl));
                case Constants.XOR:
                    double result = Math.min(Math.max(aDbl, 1 - bDbl),
                                    Math.max(1 - aDbl, bDbl));
                    return Heap.allocateReal(result);
                default:
                    throw new MintException("Unknown logical operator: " +
                              Interpreter.convertKeywordToOperator(keyword));
            }
        }
        if (a.type != Constants.TRUTH_TYPE || b.type != Constants.TRUTH_TYPE) {
            System.err.println(
                "The correct way to write a condition such as...\n\n" +
                // <br /><br />
                "x == 3 or 4 or 5\n\n" +
                // <br /><br />
                "is: (x == 3) or (x == 4) or (x == 5).\n" +
                "You may also use (x in [3, 4, 5]), which is equivalent."
            );
            badTypeException(keyword, a, b);
        }
        Boolean operand1 = PointerTools.dereferenceTruth(a);
        Boolean operand2 = PointerTools.dereferenceTruth(b);
        switch (keyword) {
            case Constants.AND:
                return Heap.allocateTruth(operand1 && operand2);
            case Constants.OR:
                return Heap.allocateTruth(operand1 || operand2);
            case Constants.XOR:
                boolean result = (operand1 && !operand2) ||
                                 (!operand1 && operand2);
                return Heap.allocateTruth(result);
            default:
                throw new MintException("Unknown comparison operator: " +
                                 Interpreter.convertKeywordToOperator(keyword));
        }
    }
    
    private static void badTypeException(int keyword, Pointer a, Pointer b)
                                         throws MintException {
        throw new MintException("Operator " +
                                Interpreter.convertKeywordToOperator(keyword) +
                                " cannot be applied to " + a + " and " +
                                b + ".");
    }
    
    private static void badTypeException(int keyword, Pointer p) throws
                                         MintException {
        throw new MintException("Operator " +
                                Interpreter.convertKeywordToOperator(keyword) +
                                " cannot be applied to " + p + ".");
    }
}
