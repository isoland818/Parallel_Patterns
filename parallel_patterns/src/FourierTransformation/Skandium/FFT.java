package FourierTransformation.Skandium;

import FourierTransformation.Complex;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FFT {
    public static Complex[] fft(Complex[] x, int granularity, int pthreads) throws ExecutionException, InterruptedException {
        Skeleton<Range, Range> fft = new DaC<Range, Range>(
                new ShouldSplit(granularity),
                new SplitProblem(),
                new Solve(),
                new MergeResult()
        );

        Skandium skandium = new Skandium(pthreads);

        Stream<Range, Range> stream = skandium.newStream(fft);

        Future<Range> future = stream.input(new Range(x));

        Complex[] result = future.get().x;

        skandium.shutdown();

        return result;
    }

}
