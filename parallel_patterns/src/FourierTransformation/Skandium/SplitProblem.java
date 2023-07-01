package FourierTransformation.Skandium;

import FourierTransformation.Complex;
import cl.niclabs.skandium.muscles.Split;

public class SplitProblem implements Split<Range, Range> {
    @Override
    public Range[] split(Range range) throws Exception {
        int size = range.n/2;
        Complex[] even = new Complex[size];
        Complex[] odd = new Complex[size];

        for (int i = 0; i < size; i++) {
            even[i] = range.x[2*i];
            odd[i] = range.x[2*i+1];
        }

        return new Range[]{
                new Range(even),
                new Range(odd)
        };
    }
}
