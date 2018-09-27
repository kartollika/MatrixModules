package kartollika.matrixmodules.matrix;

public class DoubleMatrix extends Matrix {

    private Matrix matrix1;
    private Matrix matrix2;

    private DoubleMatrix(int rows, int columns) {
        super(rows, columns);
    }

    private DoubleMatrix(Number[][] mainValues) {
        super(mainValues);
    }

    public DoubleMatrix(Matrix matrix1, Matrix matrix2) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
    }

    @Override
    public int getRows() {
        return matrix1.getRows();
    }

    @Override
    public int getColumns() {
        return matrix1.getColumns();
    }

    @Override
    protected void checkIndicesInBounds(int i, int j) {
        if (i < 0 || i >= matrix1.getRows() + matrix2.getRows()) {
            throw new IndexOutOfBoundsException("Index out of bounds. Index i: " + i + " size: " + getRows());
        }
        if (j < 0 || j >= matrix1.getColumns() + matrix2.getColumns()) {
            throw new IndexOutOfBoundsException("Index out of bounds. Index i: " + i + " size: " + getColumns());
        }
    }

    @Override
    public String toString() {
        String stringMatrix1 = matrix1.toString();
        return stringMatrix1 + matrix2.toString();
    }

    @Override
    public Number getValue(int i, int j) {
        checkIndicesInBounds(i, j);
        if (i < matrix1.getRows() && j < matrix1.getColumns()) {
            return matrix1.getValue(i, j);
        } else {
            return matrix2.getValue(i, j);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DoubleMatrix)) {
            return false;
        }

        boolean equalsFirst = matrix1.equals(((DoubleMatrix) obj).matrix1);
        return (equalsFirst) && matrix2.equals(((DoubleMatrix) obj).matrix2);
    }

    @Override
    public ColumnIterator getColumnIterator() {
        return super.getColumnIterator();
    }

    @Override
    public RowIterator getRowIterator() {
        return super.getRowIterator();
    }

    @Override
    public DiagonalIterator getDiagonalIterator() {
        return super.getDiagonalIterator();
    }

    @Override
    public Matrix swapLines(int lineSwap1, int lineSwap2) {
        return new DoubleMatrix(matrix1.swapLines(lineSwap1, lineSwap2), matrix2.swapLines(lineSwap1, lineSwap2));
    }

    @Override
    public Matrix multiplyLineByNumber(int line, Number times) {
        return new DoubleMatrix(matrix1.multiplyLineByNumber(line, times), matrix2.multiplyLineByNumber(line, times));
    }

    @Override
    public Matrix subtractWithMultiply(int lineToSubtractFrom, int lineToSubtract, Number num) {
        return new DoubleMatrix(matrix1.subtractWithMultiply(lineToSubtractFrom, lineToSubtract, num),
                matrix2.subtractWithMultiply(lineToSubtractFrom, lineToSubtract, num));
    }

    public Matrix getMatrix1() {
        return matrix1;
    }

    public Matrix getMatrix2() {
        return matrix2;
    }
}
