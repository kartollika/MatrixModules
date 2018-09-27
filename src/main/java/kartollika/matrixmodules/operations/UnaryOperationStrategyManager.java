package kartollika.matrixmodules.operations;

import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

import java.util.List;

public class UnaryOperationStrategyManager {

    private SteppingOperation steppingOperation;
    private UnaryStrategy unaryStrategy;

    public void setStrategy(UnaryStrategy unaryStrategy) {
        this.unaryStrategy = unaryStrategy;
        if (unaryStrategy instanceof Stepped) {
            steppingOperation = ((Stepped) unaryStrategy).getSteppingOperation();
        }
    }

    public Matrix executeStrategy(Matrix a) {
        return unaryStrategy.execute(a);
    }

    public List<Object[]> getSteps() {
        if (steppingOperation == null) {
            return null;
        }
        return steppingOperation.getSteps();
    }
}
