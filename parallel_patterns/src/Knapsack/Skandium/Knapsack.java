package Knapsack.Skandium;


import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Knapsack {
    public static int knapsack (int index, int capacity, int granularity, int pthreads) throws ExecutionException, InterruptedException {
        Skeleton<Range, Range> knapsack = new DaC<Range, Range>(
                new ShouldSplit(granularity),
                new SplitProblem(),
                new Solve(),
                new MergeResult()
        );

        Skandium skandium = new Skandium(pthreads);

        Stream<Range, Range> stream = skandium.newStream(knapsack);

        Future<Range> future = stream.input(new Range(index, capacity));

        return future.get().result;
    }
}
