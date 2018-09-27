package kartollika.matrixmodules.operations.concrete.binary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.RationalNumber;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.matrix.MatrixIterator;
import kartollika.matrixmodules.operations.interfaces.BinaryWithNumberStrategy;

public class OperationTimesByNumber implements BinaryWithNumberStrategy {
    @Override
    public Matrix execute(Matrix a, Number k) {
        RationalNumber byTimes = RationalNumber.parseRational(k);
        Matrix.ColumnIterator iterator = a.getColumnIterator();

        Number[][] resultValues = new Number[a.getRows()][a.getColumns()];
        while (iterator.hasNext()) {
            Number newValue = CalcWrappeer.times(iterator.next(), byTimes);
            resultValues[iterator.getRowCursor()][iterator.getColumnCursor()] = newValue;
        }
        return new Matrix(resultValues);
    }
}
