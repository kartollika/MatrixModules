package kartollika.matrixmodules.operations;

public class SwapCounter {

    private static SwapCounter ourInstance = null;
    private int swaps;

    private SwapCounter() {
    }

    public static SwapCounter getInstance() {
        if (ourInstance == null) {
            ourInstance = new SwapCounter();
        }
        return ourInstance;
    }

    public void deleteCounter() {
        ourInstance = null;
    }

    public void incSwaps() {
        swaps++;
    }

    public int getSwaps() {
        return swaps;
    }
}
