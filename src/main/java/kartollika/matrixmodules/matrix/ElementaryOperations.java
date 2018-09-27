package kartollika.matrixmodules.matrix;

public interface ElementaryOperations {

    Matrix swapLines(int lineSwap1, int lineSwap2);

    Matrix multiplyLineByNumber(int line, Number times) throws CloneNotSupportedException;

    Matrix subtractWithMultiply(int lineToSubtractFrom, int lineToSubtract, Number num);
}
