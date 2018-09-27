package kartollika.matrixmodules;

import kartollika.matrixmodules.matrix.AugmentedMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.BinaryOperationStrategyManager;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.UnaryOperationStrategyManager;
import kartollika.matrixmodules.operations.concrete.binary.*;
import kartollika.matrixmodules.operations.concrete.unary.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class OperationsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Matrix matrix, matrix1, matrix2;
    private Matrix expected;
    private Matrix result;
    private Number number;

    @Test
    public void testAddition() {
        matrix1 = new Matrix(new Number[][]{{1, 1}, {1, 1}});
        matrix2 = new Matrix(new Number[][]{{1, 0}, {0, 1}});
        expected = new Matrix(new Number[][]{{2, 1}, {1, 2}});
        BinaryOperationStrategyManager binaryManager = new BinaryOperationStrategyManager();
        binaryManager.setStrategy(new OperationAddition());
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());

        matrix1 = new Matrix(new Number[][]{{0, 0}, {0, 0}});
        matrix2 = new Matrix(new Number[][]{{1, 0}, {0, 0}, {0, 0}});
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage("Dimensions must be equals!");
        binaryManager.executeStrategy(matrix1, matrix2);
    }

    @Test
    public void testTranspose() {
        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        expected = new Matrix(new Number[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}});
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationTranspose());
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1}});
        expected = new Matrix(new Number[][]{{1}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        expected = new Matrix(new Number[][]{{1, 4}, {2, 5}, {3, 6}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, 4}, {2, 5}, {3, 6}});
        expected = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testSubtract() {
        BinaryOperationStrategyManager binaryManager = new BinaryOperationStrategyManager();
        binaryManager.setStrategy(new OperationSubtract());

        matrix1 = new Matrix(3, 3);
        matrix2 = new Matrix(new Number[][]{{-1, 2, 3}, {1, 2, -3}, {3, -4, 5}});
        expected = new Matrix(new Number[][]{{1, -2, -3}, {-1, -2, 3}, {-3, 4, -5}});
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());

    }

    @Test
    public void testTimesByNumber() {
        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        number = 3;
        expected = new Matrix(new Number[][]{{3, 6, 9}, {12, 15, 18}, {21, 24, 27}});
        BinaryOperationStrategyManager binaryManager = new BinaryOperationStrategyManager();
        binaryManager.setStrategy(new OperationTimesByNumber());
        result = binaryManager.executeStrategy(matrix, number);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1}});
        expected = new Matrix(new Number[][]{{3}});
        result = binaryManager.executeStrategy(matrix, number);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1}, {3}});
        expected = new Matrix(new Number[][]{{3}, {9}});
        result = binaryManager.executeStrategy(matrix, number);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testTimesMatrices() {
        BinaryOperationStrategyManager binaryManager = new BinaryOperationStrategyManager();
        binaryManager.setStrategy(new OperationTimes());

        matrix1 = new Matrix(new Number[][]{{6}, {2}, {3}});
        matrix2 = new Matrix(new Number[][]{{7, 1}});
        expected = new Matrix(new Number[][]{{42, 6}, {14, 2}, {21, 3}});
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());

        matrix1 = new Matrix(new Number[][]{{6}});
        matrix2 = new Matrix(new Number[][]{{7}});
        expected = new Matrix(new Number[][]{{42}});
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());

        matrix1 = new Matrix(new Number[][]{{6, 3}});
        matrix2 = new Matrix(new Number[][]{{7}, {2}, {3}});
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Count of columns of first matrix must be equal " +
                "to count of rows of the second matrix");
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());

        matrix1 = new Matrix(new Number[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        matrix2 = new Matrix(new Number[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        expected = new Matrix(new Number[][]{{90, 100, 110, 120}, {202, 228, 254, 280}, {314, 356, 398, 440}, {426, 484, 542, 600}});
        result = binaryManager.executeStrategy(matrix1, matrix2);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testInverse() {
        matrix = new Matrix(new Number[][]{{1, 1, 1}, {1, 0, 1}, {2, 1, 1}});
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationInverse(new SteppingOperation()));
        expected = new Matrix(new Number[][]{{-1, 0, 1}, {1, -1, 0}, {1, 1, -1}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testDeterminant() {
        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationDeterminant());
        expected = new Matrix(1, 1);
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{4, 5, 1}, {1, 5, 6}, {7.2, new RationalNumber(1, 2), 2}});
        expected = new Matrix(new Number[][]{{new RationalNumber(397, 2)}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{0, 1, 2}, {4, 4, 3}, {2, 1, 0}});
        expected = new Matrix(new Number[][]{{-2}});
        result = unaryManager.executeStrategy(matrix);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testGauss() {
        matrix = new Matrix(new Number[][]{{1, 2, 2}, {2, 3, 1}, {2, 1, 3}});
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationGauss(new SteppingOperation()));
        result = unaryManager.executeStrategy(matrix);
        expected = new Matrix(new Number[][]{{1, 2, 2}, {0, -1, -3}, {0, 0, 8}});
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        result = unaryManager.executeStrategy(matrix);
        expected = new Matrix(new Number[][]{{1, 2, 3}, {0, -3, -6}, {0, 0, 0}});
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, 1, 1}, {1, 0, 1}, {2, 1, 1}});
        unaryManager.setStrategy(new OperationGauss(new SteppingOperation()));
        result = unaryManager.executeStrategy(matrix);
        expected = new Matrix(new Number[][]{{1, 1, 1}, {0, -1, 0}, {0, 0, -1}});
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, 1, 1}, {1, 0, 1}, {2, 1, 1}});
        unaryManager.setStrategy(new OperationGauss(new SteppingOperation()));
        result = unaryManager.executeStrategy(matrix);
        expected = new Matrix(new Number[][]{{1, 1, 1}, {0, -1, 0}, {0, 0, -1}});
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testInvertedGauss() {

    }

    @Test
    public void testPower() {
        BinaryOperationStrategyManager binaryManager = new BinaryOperationStrategyManager();
        binaryManager.setStrategy(new OperationPower());
        int power;

        matrix = new Matrix(new Number[][]{{1, 2}, {4, 3}});
        power = 2;
        expected = new Matrix(new Number[][]{{9, 8}, {16, 17}});
        result = binaryManager.executeStrategy(matrix, power);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(new Number[][]{{1, new RationalNumber(1, 2), -1}, {1.5, 4, 0}, {1, 2, 2}});
        power = 3;
        expected = new Matrix(new Number[][]{
                {new RationalNumber(-3, 2), new RationalNumber(-29, 8), new RationalNumber(-27, 4)},
                {new RationalNumber(249, 8), new RationalNumber(271, 4), new RationalNumber(-21, 2)},
                {new RationalNumber(111, 4), 59, 0}});
        result = binaryManager.executeStrategy(matrix, power);
        assertEquals(expected.toString(), result.toString());

        matrix = new Matrix(1, 1, 2);
        power = 4;
        expected = new Matrix(1, 1, 16);
        result = binaryManager.executeStrategy(matrix, power);
        assertEquals(expected.toString(), result.toString());
    }

    @Test
    public void testSolveSystem() {
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));

        matrix = new AugmentedMatrix(new Number[][]{{1, 2}, {2, 3}}, new Number[]{1, 2});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0}, {0, 1}}, new Number[]{1, 0});
        assertEquals(expected.toString(), result.toString());

        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));
        matrix = new AugmentedMatrix(new Number[][]{{5, 2, 1}, {0, -1, 0}, {1, -2, 0}}, new Number[]{1, 0, 3});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}, new Number[]{3, 0, -14});
        assertEquals(expected.toString(), result.toString());

        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));
        matrix = new AugmentedMatrix(new Number[][]{{1, 2, 3}, {-1, 2, 4}, {0, 0, 0}}, new Number[]{2, 1, 0});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0, new RationalNumber(-1, 2)}, {0, 1, new RationalNumber(7, 4)}, {0, 0, 0}}, new Number[]{new RationalNumber(1, 2), new RationalNumber(3, 4), 0});
        assertEquals(expected.toString(), result.toString());

        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));
        matrix = new AugmentedMatrix(new Number[][]{{1, 2, 0}, {-1, 2, 0}, {2, 6, 0}}, new Number[]{2, 1, 0});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 0}}, new Number[]{new RationalNumber(1, 2), new RationalNumber(3, 4), new RationalNumber(-11, 2)});
        assertEquals(expected.toString(), result.toString());

        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));
        matrix = new AugmentedMatrix(new Number[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {2, 1, 1, 0}}, new Number[]{2, 1, 0});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}}, new Number[]{2, 1, -5});
        assertEquals(expected.toString(), result.toString());

        unaryManager.setStrategy(new OperationSolveSystem(new SteppingOperation()));
        matrix = new AugmentedMatrix(new Number[][]{{1, 0, 0, 1}, {2, 1, 0, 3}}, new Number[]{1, 2});
        result = unaryManager.executeStrategy(matrix);
        expected = new AugmentedMatrix(new Number[][]{{1, 0, 0, 1}, {0, 1, 0, 1}}, new Number[]{1, 0});
        assertEquals(expected.toString(), result.toString());
    }
}
