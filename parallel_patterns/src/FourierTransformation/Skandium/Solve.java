package FourierTransformation.Skandium;

import FourierTransformation.SequentialFFT;
import cl.niclabs.skandium.muscles.Execute;

public class Solve implements Execute<Range, Range> {
    @Override
    public Range execute(Range range) throws Exception {
        return new Range(SequentialFFT.fft(range.x));
    }
}
