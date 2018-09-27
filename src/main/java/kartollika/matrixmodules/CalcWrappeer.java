package kartollika.matrixmodules;

public class CalcWrappeer {

    public static Number plus(Number number1, Number number2) {
        try {
            Long tryLong1 = Long.parseLong(number1.toString());
            Long tryLong2 = Long.parseLong(number2.toString());
            return tryLong1 + tryLong2;
        } catch (NumberFormatException nfe) {
            try {
                RationalNumber tryRational1 = RationalNumber.parseRational(number1);
                RationalNumber tryRational2 = RationalNumber.parseRational(number2);
                return tryRational1.plus(tryRational2);
            } catch (NumberFormatException nfe1) {
                try {
                    Double double1 = number1.doubleValue();
                    Double double2 = number2.doubleValue();
                    return double1 + double2;
                } catch (NumberFormatException nfe2) {
                    throw new NumberFormatException("Not a number");
                }
            }
        }
    }

    public static Number minus(Number numberFrom, Number numberTo) {
        try {
            RationalNumber tryRational1 = RationalNumber.parseRational(numberFrom);
            RationalNumber tryRational2 = RationalNumber.parseRational(numberTo);
            return tryRational1.minus(tryRational2);
        } catch (NumberFormatException nfe) {
            try {
                Double double1 = numberFrom.doubleValue();
                Double double2 = numberTo.doubleValue();
                return double1 - double2;
            } catch (NumberFormatException nfe1) {
                throw new NumberFormatException("Not a number");
            }
        }
    }

    public static Number times(Number number1, Number number2) {
        try {
            RationalNumber tryRational1 = RationalNumber.parseRational(number1);
            RationalNumber tryRational2 = RationalNumber.parseRational(number2);
            if (tryRational1.toString().length() < 10 && tryRational2.toString().length() < 10) {
                return tryRational1.times(tryRational2);
            } else {
                throw new NumberFormatException("Number is too big");
            }
        } catch (NumberFormatException nfe) {
            try {
                Double double1 = number1.doubleValue();
                Double double2 = number2.doubleValue();
                return double1 * double2;
            } catch (NumberFormatException nfe1) {
                throw new NumberFormatException("Not a number");
            }
        }
    }

    public static Number divide(Number numberNumerator, Number numberDenominator) {
        try {
            RationalNumber tryRational1 = RationalNumber.parseRational(numberNumerator);
            RationalNumber tryRational2 = RationalNumber.parseRational(numberDenominator);
            if (tryRational1.toString().length() < 10 && tryRational2.toString().length() < 10) {
                return tryRational1.times(tryRational2.reverse());
            } else {
                throw new NumberFormatException("Number is too big");
            }
        } catch (NumberFormatException nfe) {
            try {
                double double1 = numberNumerator.doubleValue();
                double double2 = numberDenominator.doubleValue();
                return double1 * (1 / double2);
            } catch (NumberFormatException nfe1) {
                throw new NumberFormatException("Not a number");
            }
        }
    }

    public static boolean equals(Number src, Number other) {
        Number numberSrc;
        Number numberOther;
        try {
            numberSrc = Long.parseLong(src.toString());
            numberOther = Long.parseLong(other.toString());
            return numberSrc.equals(numberOther);
        } catch (NumberFormatException nfe) {
            try {
                numberSrc = RationalNumber.parseRational(src);
                numberOther = RationalNumber.parseRational(other);
                return numberSrc.equals(numberOther);
            } catch (NumberFormatException nfe1) {
                try {
                    numberSrc = src.doubleValue();
                    numberOther = Double.parseDouble(other.toString());
                    return numberSrc.equals(numberOther);
                } catch (NumberFormatException nfe2) {
                    return false;
                }
            }
        }
    }

    public static Number changeSign(Number numberNumerator) {
        try {
            RationalNumber tryRational1 = RationalNumber.parseRational(numberNumerator);
            return tryRational1.changeSign();
        } catch (NumberFormatException nfe) {
            try {
                double double1 = numberNumerator.doubleValue();
                return -double1;
            } catch (NumberFormatException nfe1) {
                throw new NumberFormatException("Not a number");
            }
        }
    }
}
