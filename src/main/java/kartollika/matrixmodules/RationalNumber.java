package kartollika.matrixmodules;

public class RationalNumber extends Number implements Comparable<RationalNumber> {

    public static RationalNumber ZERO = new RationalNumber(0, 1);
    public static RationalNumber ONE = new RationalNumber(1, 1);

    private long numerator;
    private long denominator;

    public RationalNumber(long numerator, long denominator) throws NumberFormatException {
        if (denominator == 0) {
            throw new NumberFormatException("Denominator is 0");
        }

        if (denominator < 0) {
            denominator = -denominator;
            numerator = -numerator;
        }

        long gcd = gcd(numerator, denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public static RationalNumber parseRational(String in) throws NumberFormatException {
        if (in.length() == 0) {
            throw new NumberFormatException("Parsing impossible in case of string's length 0");
        }
        int dotIndex = in.indexOf(".");
        if (dotIndex >= 0) {
            long q = (long) Math.pow(10, in.length() - dotIndex - 1);
            long num = Long.parseLong(in.substring(0, dotIndex) + in.substring(dotIndex + 1));
            return new RationalNumber(num, q);
        }

        String[] rationalParts = in.split("/");

        if (rationalParts.length == 1) {
            return new RationalNumber(Integer.parseInt(rationalParts[0]), 1);
        }

        long numerator = Integer.parseInt(rationalParts[0]);
        long denominator = Integer.parseInt(rationalParts[1]);
        long gcd = gcd(numerator, denominator);

        return new RationalNumber(numerator / gcd, denominator / gcd);
    }

    static long gcd(long a, long b) {
        long oldB;
        while (b != 0) {
            oldB = b;
            b = a % b;
            a = oldB;
        }
        return Math.abs(a);
    }

    public static RationalNumber parseRational(Number next) {
        return parseRational(next.toString());
    }

    /**
     * Checks the sign of rational number
     *
     * @return 0 if ZERO
     * 1 if POSITIVE
     * -1 if NEGATIVE
     */
    public int sign() {
        return numerator > 0 ? 1 : (numerator < 0 ? -1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number)) {
            return false;
        }

        return RationalNumber.parseRational((Number) obj).numerator == this.numerator
                && RationalNumber.parseRational((Number) obj).denominator == this.denominator;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return String.valueOf(numerator);
        }

        return numerator + "/" + denominator;
    }

    @Override
    public int intValue() {
        return Math.toIntExact(numerator / denominator);
    }

    @Override
    public long longValue() {
        return numerator / denominator;
    }

    @Override
    public float floatValue() {
        return (float) numerator / denominator;
    }

    @Override
    public double doubleValue() {
        return (double) numerator / denominator;
    }

    @Override
    public int compareTo(RationalNumber o) {
        long numerator1 = this.numerator * o.denominator;
        long numerator2 = o.numerator * this.denominator;

        return Long.compare(numerator1, numerator2);
    }

    public RationalNumber times(RationalNumber another) {
        return new RationalNumber(this.numerator * another.numerator,
                this.denominator * another.denominator);
    }

    public RationalNumber times(Number another) {
        RationalNumber anotherRational = RationalNumber.parseRational(another);
        return times(anotherRational);
    }

    /**
     * Change sing in place
     *
     * @return Rational number with changed sign
     */

    public RationalNumber changeSign() {
        return new RationalNumber(-numerator, denominator);
    }

    public RationalNumber plus(RationalNumber another) {
        long anotherDenominator = another.denominator;
        long thisDenominator = this.denominator;

        long thisNumerator = numerator * anotherDenominator;
        long anotherNumerator = another.numerator * thisDenominator;

        return new RationalNumber(thisNumerator + anotherNumerator,
                anotherDenominator * thisDenominator);
    }

    public RationalNumber minus(RationalNumber another) {
        return this.plus(another.changeSign());
    }

    public RationalNumber reverse() {
        if (numerator == 0) {
            throw new NumberFormatException("Can't perform reverse; numerator is 0");
        }
        return new RationalNumber(denominator, numerator);
    }

    public boolean isFinite() {
        return denominator != 0;
    }

    public boolean isZero() {
        return numerator == 0;
    }
}
