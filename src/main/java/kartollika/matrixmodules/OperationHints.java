package kartollika.matrixmodules;

public class OperationHints {

    public String hintMultiplyLineByNumber(int line, Number coefficient) {
        return "Multiply " + (line + 1) + " row with " + coefficient;
    }

    public String hintSwapLines(int line1, int line2) {
        return "Swap " + (line1 + 1) + " with " + (line2 + 1);
    }

    public String hintSubtractLineFromLineMultipliedByNumber(int lineToSubtractFrom,
                                                             int lineToSubtract, Number coefficient) {
        return "Subtract from line " + (lineToSubtractFrom + 1) + " line " + (lineToSubtract + 1) + "multiplied by" + coefficient;
    }

    public String hintDeterminantGetResult(int swaps) {
        return "Multiply every element on the main diagonal and multiply by (-1)^"
                + swaps + " - transposition sign";
    }

    public String hintSystemHasSolves() {
        return "System has solves:";
    }

    public String hintSystemHasNotSolves() {
        return "System has no solves";
    }

    public String hintInverseMatrix() {
        return "Inverse matrix:";
    }

    public String scrollDownToSeeMore() {
        return "Scroll down to see more";
    }

    public String hintDeterminantResult() {
        return "Determinant is:";
    }

    public String transformToDiagonal() {
        return "Transform matrix to diagonal form";
    }

    public String transformToUpperTriangle() {
        return "Transform matrix to upper triangle form";
    }
}
