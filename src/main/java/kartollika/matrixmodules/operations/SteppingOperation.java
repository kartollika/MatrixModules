package kartollika.matrixmodules.operations;

import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.concrete.unary.ISteppable;

import java.util.LinkedList;
import java.util.List;

public class SteppingOperation implements ISteppable {

    public OperationHints hints;

    private List<Object[]> steps;

    public SteppingOperation() {
        steps = new LinkedList<>();
        hints = new OperationHints();
    }

    public void setTextHints(OperationHints hints) {
        this.hints = hints;
    }

    @Override
    public void saveStep(Matrix matrix, String hint) {
        if (steps != null) {
            steps.add(new Object[]{matrix, hint});
        }
    }

    @Override
    public List<Object[]> getSteps() {
        return steps;
    }

}
