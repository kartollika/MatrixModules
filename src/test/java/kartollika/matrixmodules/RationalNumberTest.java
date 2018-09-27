package kartollika.matrixmodules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class RationalNumberTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testCreate() {
        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("Denominator is 0");
        RationalNumber rationalNumber = new RationalNumber(-1, 0);
    }

    @Test
    public void testParseRationnal() {
        String src;
        RationalNumber parsed;
        RationalNumber expected;

        src = "-1/3";
        parsed = RationalNumber.parseRational(src);
        expected = new RationalNumber(-1, 3);
        assertEquals(expected, parsed);

        src = "2/6";
        parsed = RationalNumber.parseRational(src);
        expected = new RationalNumber(1, 3);
        assertEquals(expected, parsed);

        src = "1.5";
        parsed = RationalNumber.parseRational(src);
        expected = new RationalNumber(3, 2);
        assertEquals(expected, parsed);
    }

    @Test
    public void testEquals() {
        RationalNumber one = new RationalNumber(-3, 9);
        RationalNumber another = new RationalNumber(-1, 3);
        boolean expected = true;
        assertEquals(expected, one.equals(another));
    }

    @Test
    public void testCompareTo() {
    }

    @Test
    public void testTimes() {
        RationalNumber one, another;
        RationalNumber expected;
        RationalNumber result;

        one = new RationalNumber(-1, 3);
        another = new RationalNumber(2, 6);
        expected = new RationalNumber(-1, 9);
        result = one.times(another);
        assertEquals(expected, result);

        one = RationalNumber.ZERO;
        another = new RationalNumber(10000, 45151615);
        expected = RationalNumber.ZERO;
        result = one.times(another);
        assertEquals(expected, result);
    }

    @Test
    public void testChangeSign() {
        RationalNumber src;
        RationalNumber expected;

        src = new RationalNumber(-1, 3);
        src = src.changeSign();
        expected = new RationalNumber(1, 3);
        assertEquals(expected, src);
    }

    @Test
    public void testGcd() {
        long a, b, expected, result;

        a = 10;
        b = 2;
        expected = 2;
        result = RationalNumber.gcd(a, b);
        assertEquals(expected, result);

        a = 111;
        b = 1;
        expected = 1;
        result = RationalNumber.gcd(a, b);
        assertEquals(expected, result);

        a = -3;
        b = -3;
        expected = 3;
        result = RationalNumber.gcd(a, b);
        assertEquals(expected, result);
    }

    @Test
    public void testMinus() {
        RationalNumber one, another;
        RationalNumber expected;
        RationalNumber result;

        one = new RationalNumber(4, 5);
        another = new RationalNumber(1, 2);
        expected = new RationalNumber(3, 10);
        result = one.minus(another);
        assertEquals(expected, result);
    }

    @Test
    public void testReverse() {
        RationalNumber rationalNumber;
        RationalNumber expected;
        RationalNumber result;

        rationalNumber = RationalNumber.ONE;
        expected = RationalNumber.ONE;
        result = rationalNumber.reverse();
        assertEquals(result, expected);

        rationalNumber = RationalNumber.ZERO;
        expectedException.expect(NumberFormatException.class);
        expectedException.expectMessage("Can't perform reverse; numerator is 0");
        rationalNumber.reverse();
    }

    @Test
    public void testSign() {
        RationalNumber rationalNumber;
        int expected;
        int result;

        rationalNumber = RationalNumber.ZERO;
        expected = 0;
        result = rationalNumber.sign();
        assertEquals(result, expected);

        rationalNumber = RationalNumber.ONE;
        expected = 1;
        result = rationalNumber.sign();
        assertEquals(expected, result);

        rationalNumber = RationalNumber.ONE.changeSign();
        expected = -1;
        result = rationalNumber.sign();
        assertEquals(expected, result);
    }
}