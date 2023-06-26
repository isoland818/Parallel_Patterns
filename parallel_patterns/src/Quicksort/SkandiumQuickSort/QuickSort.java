package Quicksort.SkandiumQuickSort;

import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class QuickSort {

    public static void quickSort(int[] array) throws ExecutionException, InterruptedException {
        Skeleton<Range, Range> sort = new DaC<Range, Range>(
                new ShouldSplit(128),
                new SplitList(),
                new Sort(),
                new MergeList()
        );

        Skandium skandium = new Skandium(8);

        Stream<Range, Range> stream = skandium.newStream(sort);

        Future<Range> future = stream.input(new Range(array, 0, array.length-1));

        future.get();
    }

}
