package AdaptiveQuadrature;

public class SequentialAQ {

    private static double EPILISON = 0.0001;
    public static double quad (double left, double right, double fleft, double fright, double lrarea) {
        double mid = (left + right) / 2;
        double fmid = func(mid);
        double larea = (fleft + fmid) * (mid - left) / 2;
        double rarea = (fmid + fright) * (right - mid) / 2;
        if (Math.abs((larea + rarea) - lrarea) > EPILISON) {
            larea = quad(left, mid, fleft, fmid, larea);
            rarea = quad(mid, right, fmid, fright, rarea);
        }

        return larea + rarea;
    }

    public static double func (double x) {
        return Math.pow(x, 3) / 32 - Math.pow(x, 2) / 8 - 6 * x + 50;
//        return 8;
    }
}
