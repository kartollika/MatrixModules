package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.matrix.DoubleMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationInvertedGauss extends Stepped implements UnaryStrategy {

    private boolean extendedMatrixRequired = false;

    public OperationInvertedGauss() {
        super();
    }

    public OperationInvertedGauss(SteppingOperation steppingOperation) {
        this(steppingOperation, false);
    }

    OperationInvertedGauss(SteppingOperation steppingOperation, boolean extendedMatrixRequired) {
        super(steppingOperation);
        this.extendedMatrixRequired = extendedMatrixRequired;
    }

    @Override
    public Matrix execute(Matrix a) {
        OperationHints hints = getSteppingOperation().hints;

        Matrix extended = null;
        Matrix stepResultMain = a;

        if (a instanceof DoubleMatrix && extendedMatrixRequired) {
            stepResultMain = ((DoubleMatrix) a).getMatrix1();
            extended = ((DoubleMatrix) a).getMatrix2();
        }

        if (extendedMatrixRequired && extended == null) {
            extended = new Matrix(a.getRows(), a.getColumns(), 1);
        }

        if (extended != null) {
            saveStep(new DoubleMatrix(stepResultMain, extended), hints.transformToDiagonal());
        } else {
            saveStep(stepResultMain, hints.transformToDiagonal());
        }

        Matrix.DiagonalIterator diagonalIterator = stepResultMain.getDiagonalIterator();
        while (diagonalIterator.hasNext()) {
            Number nextDiagonalElement = diagonalIterator.next();
            if (CalcWrappeer.equals(nextDiagonalElement, 0)) {
                continue;
            }

            if (CalcWrappeer.equals(nextDiagonalElement, 1)) {
                continue;
            }

            Number coefficient = CalcWrappeer.divide(1, nextDiagonalElement);
            stepResultMain = stepResultMain.multiplyLineByNumber(diagonalIterator.getRowCursor(), coefficient);
            if (extended != null) {
                extended = extended.multiplyLineByNumber(diagonalIterator.getRowCursor(), coefficient);
                saveStep(new DoubleMatrix(stepResultMain, extended), hints.hintMultiplyLineByNumber(diagonalIterator.getRowCursor(), coefficient));
            } else {
                saveStep(stepResultMain, hints.hintMultiplyLineByNumber(diagonalIterator.getRowCursor(), coefficient));
            }
        }

        Matrix.RowIterator rowIterator = stepResultMain.getRowIterator();
        for (int i = stepResultMain.getRows() - 1; i >= 1; --i) {
            diagonalIterator = stepResultMain.getDiagonalIterator();
            diagonalIterator.skipTo(i);
            Number diagonalMain = diagonalIterator.next();
            if (CalcWrappeer.equals(diagonalMain, 0)) {
                continue;
            }

            for (int j = i - 1; j >= 0; --j) {
                rowIterator.skipTo(j, i);
                Number next = rowIterator.next();
                if (!CalcWrappeer.equals(next, 0)) {
                    Number coefficient = CalcWrappeer.divide(next, diagonalMain);
                    stepResultMain = stepResultMain.subtractWithMultiply(j, i, coefficient);
                    if (extended != null) {
                        extended = extended.subtractWithMultiply(j, i, coefficient);
                        saveStep(new DoubleMatrix(stepResultMain, extended), hints.hintSubtractLineFromLineMultipliedByNumber(j, i, coefficient));
                    } else {
                        saveStep(stepResultMain, hints.hintSubtractLineFromLineMultipliedByNumber(j, i, coefficient));
                    }
                }
            }
        }
        return stepResultMain;
    }
}
