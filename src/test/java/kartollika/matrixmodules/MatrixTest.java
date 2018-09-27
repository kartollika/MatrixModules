package kartollika.matrixmodules;

import kartollika.matrixmodules.matrix.AugmentedMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.matrix.MatrixIterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class MatrixTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Matrix matrix, matrix1, matrix2;
    private Matrix expected;
    private Matrix result;

    @Test
    public void equals() {
        boolean result;

        matrix1 = new Matrix(3, 3);
        matrix2 = new Matrix(3, 3);
        result = matrix1.equals(matrix2);
        assertTrue(result);

        matrix2 = new Matrix(3, 2);
        result = matrix1.equals(matrix2);
        assertFalse(result);

        matrix1 = new Matrix(new Number[][]{{3, 2L}, {new RationalNumber(1, 4), 4.5}});
        matrix2 = new Matrix(new Number[][]{{3, 2L}, {new RationalNumber(1, 4), 4.5}});
        result = matrix1.equals(matrix2);
        assertTrue(result);

        matrix2 = new Matrix(new Number[][]{{3, 2L}, {new RationalNumber(1, 4), 5}});
        result = matrix1.equals(matrix2);
        assertFalse(result);

        Object other = 1L;
        result = matrix1.equals(other);
        assertFalse(result);

        matrix1 = new AugmentedMatrix(new Number[][]{{1, 3, 2}, {2, 3, 4}, {2, 5.5, 1L}}, new Number[]{2, 4, 2L});
        matrix2 = new Matrix(new Number[][]{{1, 3, 3}, {2, 3, 4}, {2, 5.5, 1L}});
        result = matrix1.equals(matrix2);
        assertFalse(result);

        matrix2 = new Matrix(new Number[][]{{1, 3, 2}, {2, 3, 4}, {2, 5.5, 1L}});
        result = matrix1.equals(matrix2);
        assertFalse(result);

        matrix2 = new AugmentedMatrix(new Number[][]{{1, 3, 2}, {2, 3, 4}, {2, 5.5, 1L}}, new Number[]{2, 4, 1L});
        result = matrix1.equals(matrix2);
        assertFalse(result);

        matrix2 = new AugmentedMatrix(new Number[][]{{1, 3, 2}, {2, 3, 4}, {2, 5.5, 1L}}, new Number[]{2, 4, 2L});
        result = matrix1.equals(matrix2);
        assertTrue(result);
    }

    @Test
    public void iteratorTest() {
        matrix = new Matrix(new Number[][]{{3, RationalNumber.parseRational("4/3")}, {2.3, 6L}});
        String expected = "3 4/3 2.3 6";

        StringBuilder stringBuilder = new StringBuilder();
        MatrixIterator matrixIterator = matrix.getColumnIterator();
        while (matrixIterator.hasNext()) {
            stringBuilder.append(matrixIterator.next()).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        assertEquals(expected, stringBuilder.toString());

        stringBuilder = new StringBuilder();
        matrixIterator = matrix.getRowIterator();
        expected = "3 2.3 4/3 6";
        while (matrixIterator.hasNext()) {
            stringBuilder.append(matrixIterator.next()).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        assertEquals(expected, stringBuilder.toString());

        matrix = new Matrix(new Number[][]{{3, 4}, {3.5, 2}, {5, 2}});
        matrixIterator = matrix.getColumnIterator();
        expected = "3 4 3.5 2 5 2";
        stringBuilder = new StringBuilder();
        while (matrixIterator.hasNext()) {
            stringBuilder.append(matrixIterator.next()).append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        assertEquals(expected, stringBuilder.toString());

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Dimensions must be positive");
        matrix = new Matrix(0, 0);
    }

    @Test
    public void testSwapLines() {
        matrix = new Matrix(new Number[][]{{2, 3}, {4, 5}});
        result = matrix.swapLines(0, 1);
        expected = new Matrix(new Number[][]{{4, 5}, {2, 3}});
        assertEquals(expected, result);

        matrix = new Matrix(new Number[][]{{2, 3.4}, {new RationalNumber(3, 4),
                Double.parseDouble("4.5")}, {1, 4L}});
        result = matrix.swapLines(0, 1).swapLines(0, 2);
        expected = new Matrix(new Number[][]{{1, 4L}, {2, 3.4}, {new RationalNumber(3, 4),
                Double.parseDouble("4.5")}});
        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyLineByNumber() {
        matrix = new Matrix(new Number[][]{{2, 3}, {4, 5}});
        Number number = 3;
        result = matrix.multiplyLineByNumber(0, number);
        expected = new Matrix(new Number[][]{{6, 9}, {4, 5}});
        assertEquals(expected, result);

        matrix = new Matrix(new Number[][]{{2}, {4}, {10}});
        number = RationalNumber.parseRational("4/3");
        result = matrix.multiplyLineByNumber(1, number);
        expected = new Matrix(new Number[][]{{2}, {RationalNumber.parseRational("16/3")}, {10}});
        assertEquals(expected, result);
    }

    @Test
    public void testSubtractWithMultiply() {
        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Number number = new RationalNumber(2, 1);
        result = matrix.subtractWithMultiply(1, 0, number);
        expected = new Matrix(new Number[][]{{1, 2, 3}, {2, 1, 0}, {7, 8, 9}});
        assertEquals(expected, result);

        number = RationalNumber.parseRational("1/2");
        result = matrix.subtractWithMultiply(1, 0, number);
        expected = new Matrix(new Number[][]{{1, 2, 3}, {new RationalNumber(7, 2), 4,
                new RationalNumber(9, 2)}, {7, 8, 9}});
        assertEquals(expected, result);
    }
}