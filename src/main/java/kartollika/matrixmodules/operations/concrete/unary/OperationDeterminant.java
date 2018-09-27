package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.RationalNumber;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.SwapCounter;
import kartollika.matrixmodules.operations.UnaryOperationStrategyManager;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationDeterminant extends Stepped implements UnaryStrategy {

    public OperationDeterminant() {
    }

    public OperationDeterminant(SteppingOperation steppingOperation) {
        super(steppingOperation);
    }

    @Override
    public Matrix execute(Matrix a) {
        OperationHints hints = getSteppingOperation().hints;

        Number result = RationalNumber.ONE;

        saveStep(a, hints.transformToUpperTriangle());

        UnaryOperationStrategyManager unaryOperationStrategyManager = new UnaryOperationStrategyManager();
        unaryOperationStrategyManager.setStrategy(new OperationGauss(getSteppingOperation()));
        Matrix gaussResult = unaryOperationStrategyManager.executeStrategy(a);
        SwapCounter swapCounter = SwapCounter.getInstance();

        Matrix.DiagonalIterator diagonalIterator = gaussResult.getDiagonalIterator();
        while (diagonalIterator.hasNext()) {
            Number next = diagonalIterator.next();
            result = CalcWrappeer.times(result, next);
        }

        if (swapCounter.getSwaps() % 2 == 1) {
            result = CalcWrappeer.changeSign(result);
        }
        Matrix determinant = new Matrix(new Number[][]{{result}});
        saveStep(gaussResult, hints.hintDeterminantGetResult(swapCounter.getSwaps()));
        saveStep(determinant, hints.hintDeterminantResult());
        swapCounter.deleteCounter();

        return determinant;
    }
}
