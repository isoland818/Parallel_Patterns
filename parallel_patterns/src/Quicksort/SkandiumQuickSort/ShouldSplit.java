package Quicksort.SkandiumQuickSort;

import cl.niclabs.skandium.muscles.Condition;

public class ShouldSplit implements Condition<Range> {

    int granularity;

    public ShouldSplit (int granularity) {
        this.granularity=granularity;
    }
    @Override
    public boolean condition(Range range) throws Exception {
        return range.end - range.start > granularity;
    }
}
