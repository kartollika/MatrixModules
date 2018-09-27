package kartollika.matrixmodules;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Main {

    public static void main(String[] args) {
        Double d = 461001.14155151615;
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("#", decimalFormatSymbols);
        format.setMaximumFractionDigits(8);
        System.out.println(format.format(d));
    }

}
