package kartollika.matrixmodules.matrix;


import kartollika.matrixmodules.CalcWrappeer;

import java.util.Arrays;

public class AugmentedMatrix extends Matrix {

    private Number coefficients[];

    public AugmentedMatrix(int rows, int columns) {
        this(rows, columns, 0);
    }

    public AugmentedMatrix(int rows, int columns, Number diagonalNumber) {
        super(rows, columns, diagonalNumber);
        coefficients = new Number[rows];
        for (int i = 0; i < coefficients.length; ++i) {
            coefficients[i] = 0;
        }
    }

    public AugmentedMatrix(Number[][] mainValues, Number[] coefficients) {
        super(mainValues);
        this.coefficients = coefficients;
    }

    @Override
    public int getColumns() {
        return super.getColumns();
    }

    @Override
    public Number getValue(int i, int j) {
        if (i < getRows() && j < getColumns()) {
            return super.getValue(i, j);
        } else {
            return getCoefficient(i);
        }
    }

    @Override
    protected void checkIndicesInBounds(int i, int j) {
        super.checkIndicesInBounds(i, j);
    }

    private Number[] copyCoefficients() {
        Number[] newCoefficients = new Number[coefficients.length];
        System.arraycopy(coefficients, 0, newCoefficients, 0, coefficients.length);
        return newCoefficients;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equalsMainValues = super.equals(obj);
        if (!equalsMainValues) {
            return false;
        }

        if (!(obj instanceof AugmentedMatrix)) {
            return false;
        }

        AugmentedMatrix other = (AugmentedMatrix) obj;
        for (int i = 0; i < coefficients.length; ++i) {
            if (!CalcWrappeer.equals(coefficients[i], other.coefficients[i])) {
                return false;
            }
        }
        return true;
    }

    //TODO() OPTIMIZE RETURN (NOT COPING MAIN VALUES)
    @Override
    public Matrix swapLines(int lineSwap1, int lineSwap2) {
        Matrix matrixSwapped = super.swapLines(lineSwap1, lineSwap2);
        Number[] newCoefficients = copyCoefficients();
        Number tmp = newCoefficients[lineSwap1];
        newCoefficients[lineSwap1] = newCoefficients[lineSwap2];
        newCoefficients[lineSwap2] = tmp;
        return new AugmentedMatrix(matrixSwapped.copyMainValues(), newCoefficients);
    }

    //TODO() OPTIMIZE RETURN (NOT COPING MAIN VALUES)
    @Override
    public Matrix multiplyLineByNumber(int line, Number times) {
        Matrix matrixMultiplied = super.multiplyLineByNumber(line, times);
        Number[] newCoefficients = copyCoefficients();
        newCoefficients[line] = CalcWrappeer.times(coefficients[line], times);
        return new AugmentedMatrix(matrixMultiplied.copyMainValues(), newCoefficients);

    }

    //TODO() OPTIMIZE RETURN (NOT COPING MAIN VALUES)
    @Override
    public Matrix subtractWithMultiply(int lineToSubtractFrom, int lineToSubtract, Number num) {
        Matrix matrixSubtracted = super.subtractWithMultiply(lineToSubtractFrom, lineToSubtract, num);
        Number[] newCoefficients = copyCoefficients();
        newCoefficients[lineToSubtractFrom] = CalcWrappeer.minus(newCoefficients[lineToSubtractFrom],
                CalcWrappeer.times(newCoefficients[lineToSubtract], num));

        return new AugmentedMatrix(matrixSubtracted.copyMainValues(), newCoefficients);


    }

    @Override
    public String toString() {
        String matrixSuper = super.toString();
        return matrixSuper + " " + Arrays.toString(coefficients);
    }

    public Number getCoefficient(int row) {
        return coefficients[row];
    }

    @Override
    public String toString(boolean inDoubles) {
        if (!inDoubles) {
            return toString();
        } else {
            String s = super.toString(true);
            StringBuilder stringBuilderCoefficients = new StringBuilder();
            stringBuilderCoefficients.append("[");
            for (int i = 0; i < getRows(); ++i) {
                stringBuilderCoefficients.append(getCoefficient(i).doubleValue()).append(",");
            }
            stringBuilderCoefficients.deleteCharAt(stringBuilderCoefficients.length() - 1);
            stringBuilderCoefficients.append("]");
            return s + stringBuilderCoefficients.toString();
        }
    }
}
