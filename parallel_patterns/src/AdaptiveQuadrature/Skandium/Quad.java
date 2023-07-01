package AdaptiveQuadrature.Skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class Quad {
    public static double quad(double left, double right, double fleft, double fright, double lrarea, double granularity, int pthreads) throws ExecutionException, InterruptedException {
        Skeleton<Range, Double> quad = new DaC<Range, Double>(
                new ShouldSplit(granularity),
                new SplitProblem(),
                new Calculate(),
                new MergeResult()
        );

        Skandium skandium = new Skandium(pthreads);

        Stream<Range, Double> stream = skandium.newStream(quad);

        Future<Double> future = stream.input(new Range(left,right,fleft,fright,lrarea));

        Double result = future.get();

        return result;
    }
}
