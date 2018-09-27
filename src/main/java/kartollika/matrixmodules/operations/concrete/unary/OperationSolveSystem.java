package kartollika.matrixmodules.operations.concrete.unary;

import kartollika.matrixmodules.CalcWrappeer;
import kartollika.matrixmodules.OperationHints;
import kartollika.matrixmodules.matrix.AugmentedMatrix;
import kartollika.matrixmodules.matrix.Matrix;
import kartollika.matrixmodules.operations.Stepped;
import kartollika.matrixmodules.operations.SteppingOperation;
import kartollika.matrixmodules.operations.UnaryOperationStrategyManager;
import kartollika.matrixmodules.operations.interfaces.UnaryStrategy;

public class OperationSolveSystem extends Stepped implements UnaryStrategy {

    public OperationSolveSystem() {
        super();
    }

    public OperationSolveSystem(SteppingOperation steppingOperation) {
        super(steppingOperation);
    }

    @Override
    public Matrix execute(Matrix a) {
        OperationHints hints = getSteppingOperation().hints;

        UnaryOperationStrategyManager unaryManager = new UnaryOperationStrategyManager();
        unaryManager.setStrategy(new OperationGauss(getSteppingOperation()));
        Matrix betweenResult = unaryManager.executeStrategy(a);
        unaryManager.setStrategy(new OperationInvertedGauss(getSteppingOperation()));
        betweenResult = unaryManager.executeStrategy(betweenResult);

        AugmentedMatrix augmentedMatrix = (AugmentedMatrix) betweenResult;

        int countZeros;
        int rank;
        int vars;

        vars = augmentedMatrix.getColumns();

        StringBuilder sharedHintStringBuilder = new StringBuilder(hints.hintSystemHasSolves()).append("<br>");
        int max_rank = Math.min(augmentedMatrix.getRows(), augmentedMatrix.getColumns());
        StringBuilder[] rowStringBuilder = new StringBuilder[vars];
        for (int i = 0; i < vars; ++i) {
            rowStringBuilder[i] = new StringBuilder();
        }

        rank = max_rank;

        for (int i = augmentedMatrix.getRows() - 1; i >= 0; --i) {
            countZeros = 0;
            Matrix.ColumnIterator columnIterator = augmentedMatrix.getColumnIterator();
            columnIterator.skipTo(i);
            for (int j = 0; j < augmentedMatrix.getColumns(); ++j) {
                Number nextInRowElement = columnIterator.next();
                if (CalcWrappeer.equals(nextInRowElement, 0)) {
                    countZeros++;
                    continue;
                }

                if (rowStringBuilder[i].length() == 0) {
                    if (nextInRowElement.longValue() > 0) {
                        rowStringBuilder[i].append(getElemString(nextInRowElement, j));
                    } else {
                        rowStringBuilder[i].append("- ").append(getElemString(CalcWrappeer.changeSign(nextInRowElement), j));
                    }
                } else {
                    if (nextInRowElement.longValue() > 0) {
                        rowStringBuilder[i].append(" + ").append(getElemString(nextInRowElement, j));
                    } else {
                        rowStringBuilder[i].append(" - ").append(getElemString(CalcWrappeer.changeSign(nextInRowElement), j));
                    }

                }
            }

            if (countZeros == vars) {
                if (!CalcWrappeer.equals(augmentedMatrix.getCoefficient(i), 0)) {
                    saveStep(augmentedMatrix, hints.hintSystemHasNotSolves());
                    /*stepStrings.add(getRes().getString(R.string.noSolves, i + 1, result.getCoefsMap().get(100_000 + i)));
                    stepMatrices.add(new Matrix(result.getValuesMap(), result.getCoefsMap(),
                            result.getRowCount(), result.getColumnCount()));*/

                    return augmentedMatrix;
                }
                rank--;
            } else {
                rowStringBuilder[i].append(" = ").append(augmentedMatrix.getCoefficient(i));
            }
        }

        int cur_rank = rank;
        rank = max_rank;

        while (rank < vars) {
            rank++;
        }

        if (cur_rank > 4) {
            sharedHintStringBuilder.append(" <i><small>(").append(hints.scrollDownToSeeMore()).append(")</small></i><br>");
        }

        int i = 0;
        int q = 0;
        while (rowStringBuilder[i].length() != 0) {
            ++q;
            if (++i == rowStringBuilder.length)
                break;
        }
        for (int j = 0; j < q; ++j) {
            if (j < q - 1) {
                sharedHintStringBuilder.append(rowStringBuilder[j].append("<br>"));
            } else {
                sharedHintStringBuilder.append(rowStringBuilder[j]);
            }
        }

        saveStep(augmentedMatrix, sharedHintStringBuilder.toString());

        return augmentedMatrix;
    }

    private String getElemString(Number elem, int j) {
        String s;

        if (!CalcWrappeer.equals(elem, 1)) {
            s = elem.toString() + "x<sub>" + String.valueOf(j + 1) + "</sub>";
        } else {
            s = "x<sub>" + String.valueOf(j + 1) + "</sub>";
        }
        return s;
    }
}
