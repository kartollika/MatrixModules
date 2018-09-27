package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.matrix.Matrix;

import java.util.List;

public interface ISteppable {

    void saveStep(Matrix matrix, String hint);

    List<Object[]> getSteps();
}
