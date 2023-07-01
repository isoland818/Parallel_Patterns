package FourierTransformation.Skandium;

import FourierTransformation.Complex;

public class Range {
    int n;
    Complex[] x;
    Complex[] result;

    public Range(Complex[] x) {
        this.n=x.length;
        this.x=x;
    }
}
