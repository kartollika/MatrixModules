package kartollika.matrixmodules.operations.concrete.binary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.RationalNumber;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.interfaces.BinaryStrategy;

public class OperationTimes implements BinaryStrategy {

    @Override
    public Matrix execute(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("Count of columns of first matrix must be equal " +
                    "to count of rows of the second matrix");
        }
        Number[][] resultValues = new Number[a.getRows()][b.getColumns()];

        Matrix.ColumnIterator colIterator = a.getColumnIterator();
        Matrix.RowIterator rowIterator = b.getRowIterator();

        for (int i = 0; i < a.getRows(); ++i) {
            for (int j = 0; j < b.getColumns(); ++j) {
                colIterator.skipTo(i);
                rowIterator.skipTo(j);
                Number element = RationalNumber.ZERO;
                for (int k = 0; k < a.getColumns(); ++k) {
                    element = CalcWrappeer.plus(element, CalcWrappeer.times(colIterator.next(), rowIterator.next()));
                }
                resultValues[i][j] = element;
            }
        }
        return new Matrix(resultValues);
    }
}
