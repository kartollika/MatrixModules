package kartollika.matrixmodules.matrix;

import java.util.Iterator;

public abstract class MatrixIterator implements Iterator<Number> {

    Integer rowCursor = 0;
    Integer columnCursor = 0;

    public int getRowCursor() {
        return rowCursor;
    }

    public int getColumnCursor() {
        return columnCursor;
    }
}
