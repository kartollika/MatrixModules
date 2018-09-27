package kartollika.matrixmodules.operations.concrete.binary;

import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.BinaryOperationStrategyManager;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.interfaces.BinaryWithNumberStrategy;

public class OperationPower extends Stepped implements BinaryWithNumberStrategy {

    public OperationPower() {
        super();
    }

    public OperationPower(SteppingOperation steppingOperation) {
        super(steppingOperation);
    }

    @Override
    public Matrix execute(Matrix a, Number k) {
        BinaryOperationStrategyManager binaryOperationStrategyManager = new BinaryOperationStrategyManager();
        binaryOperationStrategyManager.setStrategy(new OperationTimes());
        Matrix result = new Matrix(a.getRows(), a.getColumns(), 1);
        Matrix stepMatrix = a;
        int n = k.intValue();

        while (n > 0) {
            if ((n & 1) == 1) {
                result = binaryOperationStrategyManager.executeStrategy(result, stepMatrix);
            }
            stepMatrix = binaryOperationStrategyManager.executeStrategy(stepMatrix, stepMatrix);
            n >>= 1;
        }
        return result;
    }
}
