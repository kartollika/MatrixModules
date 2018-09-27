package kartollika.matrixmodules.operations;

import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.interfaces.BinaryStrategy;
import kartollika.matrixmodules.operations.interfaces.BinaryWithNumberStrategy;

public class BinaryOperationStrategyManager {

    private BinaryStrategy binaryStrategy;
    private BinaryWithNumberStrategy binaryWithNumberStrategy;

    public void setStrategy(BinaryStrategy binaryStrategy) {
        this.binaryStrategy = binaryStrategy;
    }

    public void setStrategy(BinaryWithNumberStrategy binaryWithNumberStrategy) {
        this.binaryWithNumberStrategy = binaryWithNumberStrategy;
    }

    public Matrix executeStrategy(Matrix a, Matrix b) {
        return binaryStrategy.execute(a, b);
    }

    public Matrix executeStrategy(Matrix a, Number k) {
        return binaryWithNumberStrategy.execute(a, k);
    }
}
