package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.RationalNumber;
import kartollika.matrixmodules.matrix.DoubleMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.SwapCounter;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationGauss extends Stepped implements UnaryStrategy {

    private boolean extendedMatrixRequired = false;

    private OperationGauss() {
        super();
    }

    public OperationGauss(SteppingOperation steppingOperation) {
        this(steppingOperation, false);
    }

    public OperationGauss(SteppingOperation steppingOperation, boolean extendedMatrixRequired) {
        super(steppingOperation);
        this.extendedMatrixRequired = extendedMatrixRequired;
    }

    /**
     * Return a upper-triangle matrix without multiplying the rows by number to make ones on main diagonal
     * TODO() WITH DIAGONAL ITERATOR
     */
    @Override
    public Matrix execute(Matrix a) {
        OperationHints hints = getSteppingOperation().hints;

        Matrix stepResult = a;
        Matrix extended = null;
        if (extendedMatrixRequired) {
            extended = new Matrix(a.getRows(), a.getColumns(), 1);
        }

        if (extendedMatrixRequired) {
            saveStep(new DoubleMatrix(a, extended), hints.transformToUpperTriangle());
        } else {
            saveStep(a, hints.transformToUpperTriangle());
        }

        SwapCounter swapCounter = SwapCounter.getInstance();

        for (int i = 0; i < Math.min(a.getRows(), a.getColumns()); ++i) {
            Matrix.RowIterator findingElementIterator = stepResult.getRowIterator();
            findingElementIterator.skipTo(i, i);
            Number nextElement;
            int targetOne = -1;
            int targetFirstNonZero = -1;
            boolean isFirstZero = false;

            for (int j = i; j < a.getRows(); ++j) {
                nextElement = findingElementIterator.next();
                if (CalcWrappeer.equals(nextElement, RationalNumber.ONE)
                        || CalcWrappeer.equals(nextElement, RationalNumber.ONE.changeSign())) {
                    targetOne = j;
                    break;
                }

                if (j == i) {
                    if (CalcWrappeer.equals(nextElement, RationalNumber.ZERO)) {
                        isFirstZero = true;
                    }
                }

                if (isFirstZero && nextElement.intValue() != 0) {
                    targetFirstNonZero = j;
                }
            }

            if (targetOne != -1) {
                if (targetOne != i) {
                    stepResult = stepResult.swapLines(i, targetOne);
                    findingElementIterator = stepResult.getRowIterator();

                    if (extended != null) {
                        extended = extended.swapLines(i, targetOne);
                        saveStep(new DoubleMatrix(stepResult, extended), hints.hintSwapLines(i, targetOne));
                    } else {
                        saveStep(stepResult, hints.hintSwapLines(i, targetOne));
                    }
                    swapCounter.incSwaps();
                }
            } else {
                if (isFirstZero) {
                    if (targetFirstNonZero != -1) {
                        stepResult = stepResult.swapLines(i, targetFirstNonZero);
                        findingElementIterator = stepResult.getRowIterator();
                        if (extended != null) {
                            extended = extended.swapLines(i, targetFirstNonZero);
                            saveStep(new DoubleMatrix(stepResult, extended), hints.hintSwapLines(i, targetFirstNonZero));
                        } else {
                            saveStep(stepResult, hints.hintSwapLines(i, targetFirstNonZero));
                        }
                        swapCounter.incSwaps();

                        findingElementIterator.skipTo(i, i);
                    }
                }
            }

            Matrix.DiagonalIterator diagonalIterator = stepResult.getDiagonalIterator();
            diagonalIterator.skipTo(i);
            Number diagonalMain = diagonalIterator.next();
            findingElementIterator.skipTo(i + 1, i);
            for (int j = i + 1; j < a.getRows(); ++j) {
                nextElement = findingElementIterator.next();
                if (!CalcWrappeer.equals(nextElement, RationalNumber.ZERO)) {
                    Number coefficient = CalcWrappeer.divide(nextElement, diagonalMain);
                    stepResult = stepResult.subtractWithMultiply(j, i, coefficient);
                    if (extended != null) {
                        extended = extended.subtractWithMultiply(j, i, coefficient);
                        saveStep(new DoubleMatrix(stepResult, extended), hints.hintSubtractLineFromLineMultipliedByNumber(j, i, coefficient));
                    } else {
                        saveStep(stepResult, hints.hintSubtractLineFromLineMultipliedByNumber(j, i, coefficient));
                    }
                }
            }
        }
        return stepResult;
    }
}
