package kartollika.matrixmodules.operations;

import kartollika.matrixmodules.matrix.Matrix;

public class Stepped {

    private SteppingOperation steppingOperation;

    protected Stepped() {
        steppingOperation = new SteppingOperation();
    }

    protected Stepped(SteppingOperation steppingOperation) {
        this();
        this.steppingOperation = steppingOperation;
    }

    public SteppingOperation getSteppingOperation() {
        return steppingOperation;
    }

    public void saveStep(Matrix matrix, String hint) {
        if (steppingOperation != null) {
            steppingOperation.saveStep(matrix, hint);
        }
    }

    protected Matrix getLastSavedMatrix() {
        return (Matrix) getSteppingOperation()
                .getSteps()
                .get(getSteppingOperation()
                        .getSteps()
                        .size() - 1)[0];
    }
}
