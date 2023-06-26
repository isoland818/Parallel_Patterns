package Quicksort.SkandiumQuickSort;

import Quicksort.SequentialQuickSort;
import cl.niclabs.skandium.muscles.Execute;

import java.util.Arrays;

public class Sort implements Execute<Range, Range> {
    @Override
    public Range execute(Range range) throws Exception {
        if (range.end <= range.start) return range;

        SequentialQuickSort.quickSort(range.array, range.start, range.end);

        return range;
    }
}
