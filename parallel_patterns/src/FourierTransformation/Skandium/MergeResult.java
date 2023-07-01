package FourierTransformation.Skandium;

import FourierTransformation.Complex;
import cl.niclabs.skandium.muscles.Merge;

public class MergeResult implements Merge<Range, Range> {
    @Override
    public Range merge(Range[] ranges) throws Exception {
        Complex[] evenResult = ranges[0].result;
        Complex[] oddResult = ranges[1].result;
        int size = ranges[0].n;
        Complex[] result = new Complex[size*2];
        for (int i = 0; i < size; i++) {
            double theta = - Math.PI * i /size;
            Complex twiddle = new Complex(Math.cos(theta), Math.sin(theta));
            result[i] = evenResult[i].plus(twiddle.times(oddResult[i]));
            result[i + size] = evenResult[i].minus(twiddle.times(oddResult[i]));
        }
        return new Range(result);
    }
}
