package gui;

import java.math.BigDecimal;

/**
 * @author Oliver Chu
 */
public class Rational {
    public long n;
    public long d;
    private static ForkedTable allRationals = new ForkedTable();
    
    // makeRat memoizes rational number construction.
    public static Rational makeRat(long numer, long denom) {
        long[] lngs = {numer, denom};
        Rational already = allRationals.getRat(lngs);
        if (already == null) {
            return new Rational(numer, denom);
        } else {
            return already;
        }
    }
    
    private Rational(long num, long den) {
        n = num;
        d = den;
        simplify();
        save();
    }

    private void save() {
        long[] lngs = {n, d};
        allRationals.bindLongsToRat(lngs, this);
    }
    
    public static boolean closeEnough(double a, double b) {
        // If they really are equal...
        if (a == b) {
            return true;
        }
        String alpha = "" + a;
        String beta = "" + b;
        if (alpha.endsWith(".0") && beta.endsWith(".0")) {
            return ((int) a) == ((int) b);
        } else {
            if (alpha.equals(b)) {
                return true;
            }
            if (a > b) {
                return (a - b) < 0.00000001;
            } else {
                return (b - a) < 0.00000001;
            }
        }
    }
    
    public static double divideCorrectly(long numer, long denom) {
        BigDecimal top = new BigDecimal("" + numer);
        BigDecimal bottom = new BigDecimal("" + denom);
        top = top.divide(bottom, 32, BigDecimal.ROUND_HALF_UP);
        double answer = top.doubleValue();
        String dblAsString = "" + answer;
        String reality = top.toString();
        while (!(reality.startsWith(dblAsString)) &&
               dblAsString.length() > 12) {
            dblAsString = StrTools2.slice(dblAsString, 0, -1);
        }
        return Double.parseDouble(dblAsString);
    }
    
    public static double divideCorrectlyFast(double numer, double denom) {
        double inverse = 1.0 / denom;
        return numer * inverse;
    }

    public static double divideCorrectly(double n0, double d0) {
        return divideCorrectlyFast(n0, d0);
        /* BigDecimal top = new BigDecimal(n0);
        BigDecimal bottom = new BigDecimal(d0);
        top = top.divide(bottom, 32, BigDecimal.ROUND_HALF_UP);
        double answer = top.doubleValue();
        String dblAsString = "" + answer;
        String reality = top.toString();
        while (!(reality.startsWith(dblAsString)) &&
               dblAsString.length() > 12) {
            dblAsString = StrTools2.slice(dblAsString, 0, -1);
        }
        return Double.parseDouble(dblAsString); */
    }
    
    public Integer getInteger() {
        if (d == 1) {
            return (int) n;
        }
        return null;
    }
    
    public Rational add(Rational r) {
        long newDenom = r.d * d;
        long newNumer = n * r.d + r.n * d;
        return makeRat(newNumer, newDenom);
    }
    
    public Rational sub(Rational r) {
        long newDenom = r.d * d;
        long newNumer = n * r.d - r.n * d;
        return makeRat(newNumer, newDenom);
    }
    
    public Rational mul(Rational r) {
        long newNumer = n * r.n;
        long newDenom = d * r.d;
        return makeRat(newNumer, newDenom);
    }
    
    public Rational div(Rational r) {
        long newNumer = n * r.d;
        long newDenom = d * r.n;
        return makeRat(newNumer, newDenom);
    }
    
    public String sign() {
        if ((n < 0 && d < 0) || (n > 0 && d > 0) || n == 0) {
            return "";
        }
        return "-";
    }
    
    public BigDecimal toBigDecimal() {
        BigDecimal seme = new BigDecimal("" + n);
        BigDecimal uke  = new BigDecimal("" + d);
        // We invert first, for more accuracy.
        BigDecimal pose =
            BigDecimal.ONE.divide(uke, 48, BigDecimal.ROUND_HALF_UP);
        BigDecimal answer = seme.multiply(pose);
        // We chop off the last numbers by rounding, not truncating.
        answer.setScale(32, BigDecimal.ROUND_HALF_UP);
        return answer;
    }

    public double toDouble() {
        return divideCorrectly(n, d);
    }

    public String toString() {
        String inf = getInfinity();
        if (inf != null) {
            return inf;
        }
        return "" + toDouble();
    }
    
    public String getInfinity() {
        if (d == 0 && n == 0) {
            return "undefined (infinity multiplied by 0)";
        }
        if (d != 0) {
            return null;
        }
        byte scale = (byte) Math.abs(n);
        boolean isAlephNull = scale == 1;
        return (n < 0 ? "-" : "") + "infinity" +
               (isAlephNull ? "" : " of magnitude " + Math.abs(n));
    }
    
    public long greatestCommonDivisor(long a, long b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        return greatestCommonDivisor(b % a, a);
    }
    
    public final void simplify() {
        long gcd = greatestCommonDivisor(n, d);
        long positive = Math.abs(gcd);
        if (positive != 0L && positive != 1L) {
            n /= gcd;
            d /= gcd;
        }
    }
    
    public String exact() {
        String inf = getInfinity();
        if (inf != null) {
            return inf;
        }
        return sign() + Math.abs(n) + "/" + Math.abs(d);
    }
}
