package kartollika.matrixmodules.operations.concrete.binary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.interfaces.BinaryStrategy;

public class OperationSubtract implements BinaryStrategy {
    @Override
    public Matrix execute(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getColumns() != b.getColumns()) {
            throw new UnsupportedOperationException("Dimensions must be equals!");
        }
        Number[][] resultCoefficients = new Number[a.getRows()][a.getColumns()];


        Matrix.ColumnIterator columnIterator1 = a.getColumnIterator();
        Matrix.ColumnIterator columnIterator2 = b.getColumnIterator();

        while (columnIterator1.hasNext()) {
            Number numberFirst = columnIterator1.next();
            Number numberSecond = columnIterator2.next();
            resultCoefficients[columnIterator1.getRowCursor()][columnIterator1.getColumnCursor()] =
                    CalcWrappeer.minus(numberFirst, numberSecond);
        }

        return new Matrix(resultCoefficients);
    }
}
