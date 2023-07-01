package Fibonacci.Skandium;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Fib {
    public static long fib(int n, int granularity, int pthreads) throws ExecutionException, InterruptedException {
        Skeleton<Integer, Long> fib = new DaC<Integer, Long>(
                new ShouldSplit(granularity),
                new SplitProblem(),
                new Calculate(),
                new MergeResult()
                );

        Skandium skandium = new Skandium(pthreads);

        Stream<Integer, Long> stream = skandium.newStream(fib);

        Future<Long> future = stream.input(n);

        long result = future.get();

        return result;
    }

}
