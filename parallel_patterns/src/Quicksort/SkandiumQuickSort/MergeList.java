package Quicksort.SkandiumQuickSort;

import cl.niclabs.skandium.muscles.Merge;


import java.util.Random;

public class MergeList implements Merge<Range, Range> {
    @Override
    public Range merge(Range[] ranges) throws Exception {
        Range result = new Range (ranges[0].array, ranges[0].start, ranges[1].end);
        return result;
    }
}
