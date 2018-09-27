package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.matrix.DoubleMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.UnaryOperationStrategyManager;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationInverse extends Stepped implements UnaryStrategy {

    private OperationInverse() {
        super();
    }

    public OperationInverse(SteppingOperation steppingOperation) {
        super(steppingOperation);
    }

    @Override
    public Matrix execute(Matrix a) {
        OperationHints hints = getSteppingOperation().hints;
        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationDeterminant());
        if (unaryManager.executeStrategy(a).equals(new Matrix(1, 1))) {
            return null;
        }

        unaryManager.setStrategy(new OperationGauss(getSteppingOperation(), true));
        unaryManager.executeStrategy(a);

        unaryManager.setStrategy(new OperationInvertedGauss(getSteppingOperation(), true));
        unaryManager.executeStrategy(getLastSavedMatrix());

        Matrix inverseMatrix = ((DoubleMatrix) getLastSavedMatrix()).getMatrix2();

        saveStep(inverseMatrix, hints.hintInverseMatrix());

        return inverseMatrix;
    }
}
