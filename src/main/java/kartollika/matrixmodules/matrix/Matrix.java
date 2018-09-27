package kartollika.matrixmodules.matrix;

import kartollika.matrixmodules.CalcWrappeer;

import java.util.Arrays;

public class Matrix implements ElementaryOperations {

    private int rows;
    private int columns;
    private Number[][] mainValues;

    public Matrix(int rows, int columns, Number diagonalNumber) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Dimensions must be positive");
        }
        this.rows = rows;
        this.columns = columns;
        mainValues = new Number[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                if (i == j) {
                    mainValues[i][j] = diagonalNumber;
                } else {
                    mainValues[i][j] = 0;
                }
            }
        }
    }

    public Matrix(int rows, int columns) {
        this(rows, columns, 0);
    }

    public Matrix(Number[][] mainValues) {
        this(mainValues.length, mainValues[0].length);
        this.mainValues = mainValues;
    }

    protected Matrix() {
    }

    private Number[][] getMainValues() {
        return mainValues;
    }

    protected Number[][] copyMainValues() {
        Number[][] newMainValues = new Number[rows][columns];
        for (int i = 0; i < rows; ++i) {
            System.arraycopy(mainValues[i], 0, newMainValues[i], 0, columns);
        }
        return newMainValues;
    }

    public Number getValue(int i, int j) {
        checkIndicesInBounds(i, j);
        return mainValues[i][j];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "ROWS: " + rows + ", COLUMNS: " + columns + ", " + Arrays.deepToString(mainValues);
    }

    protected void checkIndicesInBounds(int i, int j) {
        if (i < 0 || i > getRows()) {
            throw new IndexOutOfBoundsException("Index out of bounds. Index i: " + i + " size: " + getRows());
        }
        if (j < 0 || j > getColumns()) {
            throw new IndexOutOfBoundsException("Index out of bounds. Index i: " + j + " size: " + getColumns());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix)) {
            return false;
        }

        Matrix matrix = (Matrix) obj;

        if (matrix.rows != this.rows
                || matrix.columns != this.columns) {
            return false;
        }

        ColumnIterator thisIterator = getColumnIterator();
        ColumnIterator otherIterator = matrix.getColumnIterator();

        while (thisIterator.hasNext()) {
            if (!CalcWrappeer.equals(thisIterator.next(), otherIterator.next())) {
                return false;
            }
        }
        return true;
    }

    public ColumnIterator getColumnIterator() {
        return new ColumnIterator();
    }

    public RowIterator getRowIterator() {
        return new RowIterator();
    }

    public DiagonalIterator getDiagonalIterator() {
        return new DiagonalIterator();
    }

    @Override
    public Matrix swapLines(int lineSwap1, int lineSwap2) {
        Number[][] newMainValues = copyMainValues();
        ColumnIterator columnIteratorLine1 = getColumnIterator();
        ColumnIterator columnIteratorLine2 = getColumnIterator();
        columnIteratorLine1.skipTo(lineSwap1);
        columnIteratorLine2.skipTo(lineSwap2);

        for (int i = 0; i < columns; ++i) {
            Number numLine1 = columnIteratorLine1.next();
            Number numLine2 = columnIteratorLine2.next();

            newMainValues[columnIteratorLine1.getRowCursor()][columnIteratorLine1.getColumnCursor()] = numLine2;
            newMainValues[columnIteratorLine2.getRowCursor()][columnIteratorLine2.getColumnCursor()] = numLine1;
        }

        return new Matrix(newMainValues);
    }

    @Override
    public Matrix multiplyLineByNumber(int line, Number times) {
        Number[][] newMainValues = copyMainValues();

        for (int i = 0; i < columns; ++i) {
            Number target = newMainValues[line][i];
            newMainValues[line][i] = CalcWrappeer.times(target, times);
        }
        return new Matrix(newMainValues);
    }

    @Override
    public Matrix subtractWithMultiply(int lineToSubtractFrom, int lineToSubtract, Number num) {
        Number[][] newMainValues = copyMainValues();

        for (int i = 0; i < columns; ++i) {
            newMainValues[lineToSubtractFrom][i] =
                    CalcWrappeer.minus(newMainValues[lineToSubtractFrom][i],
                            CalcWrappeer.times(newMainValues[lineToSubtract][i], num));
        }
        return new Matrix(newMainValues);
    }

    public String toString(boolean inDoubles) {
        if (!inDoubles) {
            return toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ROWS: ").append(rows).append(", COLUMNS :").append(columns).append(", ");
            stringBuilder.append("[");
            Number[][] values = getMainValues();
            for (int i = 0; i < rows; ++i) {
                stringBuilder.append("[");
                for (int j = 0; j < columns; ++j) {
                    stringBuilder.append(values[i][j].doubleValue()).append(", ");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.append("], ");
            }
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /**
     * Iterates over columns while the row is fixed
     */
    public class ColumnIterator extends MatrixIterator {

        Integer columnCursor = -1;

        private ColumnIterator() {
        }

        @Override
        public boolean hasNext() {
            if (columnCursor + 1 >= columns) {
                if (rowCursor + 1 >= rows) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Number next() {
            columnCursor = (columnCursor + 1);
            rowCursor += columnCursor / columns;
            columnCursor %= columns;

            return mainValues[rowCursor][columnCursor];
        }

        public void reset() {
            rowCursor = 0;
            columnCursor = -1;
        }

        @Override
        public int getColumnCursor() {
            return columnCursor;
        }

        public void skipTo(int destRow) {
            columnCursor = -1;
            rowCursor = destRow;
        }

        public void skipTo(int destRow, int destColumn) {
            columnCursor = destColumn - 1;
            rowCursor = destRow;
        }
    }

    /**
     * Iterates over rows while the column is fixed
     */
    public class RowIterator extends MatrixIterator {

        Integer rowCursor = -1;

        private RowIterator() {
        }

        @Override
        public boolean hasNext() {
            if (rowCursor + 1 >= rows) {
                if (columnCursor + 1 >= columns) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int getRowCursor() {
            return rowCursor;
        }

        @Override
        public Number next() {
            rowCursor = (rowCursor + 1);
            columnCursor += rowCursor / rows;
            rowCursor %= rows;

            return mainValues[rowCursor][columnCursor];
        }

        public void reset() {
            columnCursor = 0;
            rowCursor = -1;
        }

        public void skipTo(int destColumn) {
            rowCursor = -1;
            columnCursor = destColumn;
        }

        public void skipTo(int destRow, int destColumn) {
            rowCursor = destRow - 1;
            columnCursor = destColumn;
        }
    }

    /**
     * Iterates by matrix's main diagonal
     */
    public class DiagonalIterator extends MatrixIterator {

        Integer rowCursor = -1;
        Integer columnCursor = -1;

        private DiagonalIterator() {
        }

        @Override
        public boolean hasNext() {
            if (rowCursor + 1 >= Math.min(rows, columns) || columnCursor + 1 >= Math.min(rows, columns)) {
                return false;
            }
            return true;
        }

        @Override
        public int getRowCursor() {
            return rowCursor;
        }

        @Override
        public int getColumnCursor() {
            return columnCursor;
        }

        @Override
        public Number next() {
            rowCursor++;
            columnCursor++;

            return mainValues[rowCursor][columnCursor];
        }

        public void reset() {
            columnCursor = -1;
            rowCursor = -1;
        }

        public void skipTo(int destRowOrColumn) {
            rowCursor = destRowOrColumn - 1;
            columnCursor = destRowOrColumn - 1;
        }
    }
}
