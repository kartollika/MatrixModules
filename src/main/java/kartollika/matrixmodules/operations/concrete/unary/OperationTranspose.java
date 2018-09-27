package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.matrix.MatrixIterator;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationTranspose implements UnaryStrategy {
    @Override
    public Matrix execute(Matrix a) {
        MatrixIterator rowIterator = a.getRowIterator();

        Number[][] resultValues = new Number[a.getColumns()][a.getRows()];

        while (rowIterator.hasNext()) {
            Number tmp = rowIterator.next();
            resultValues[rowIterator.getColumnCursor()][rowIterator.getRowCursor()] = tmp;
        }
        return new Matrix(resultValues);
    }
}
