package FourierTransformation.Skandium;

import FourierTransformation.Complex;

public class Range {
    int n;
    Complex[] x;

    public Range(Complex[] x) {
        this.n=x.length;
        this.x=copyArray(x, n);
    }

    public static Complex[] copyArray(Complex[] x, int n) {
        Complex[] copy = new Complex[n];
        for (int i = 0; i < n; i++) {
            copy[i] = x[i];
        }
        return copy;
    }
}
