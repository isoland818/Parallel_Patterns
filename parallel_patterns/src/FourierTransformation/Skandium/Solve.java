package FourierTransformation.Skandium;

import FourierTransformation.SequentialFFT;
import cl.niclabs.skandium.muscles.Execute;

public class Solve implements Execute<Range, Range> {
    @Override
    public Range execute(Range range) throws Exception {
        range.result= SequentialFFT.fft(range.x);
        return range;
    }
}
