package Knapsack.Skandium;

import cl.niclabs.skandium.muscles.Split;

public class SplitProblem implements Split<Range, Range> {
    @Override
    public Range[] split(Range range) throws Exception {
        if (Range.weights[range.index] > range.capacity) {
            return new Range[]{new Range(range.index-1, range.capacity)};
        } else {
            return new Range[]{
                    new Range(range.index-1, range.capacity),
                    new Range(range.index-1, range.capacity-Range.weights[range.index])
            };
        }

    }
}
