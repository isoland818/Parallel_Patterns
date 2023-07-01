package Knapsack.Skandium;

import cl.niclabs.skandium.muscles.Merge;

public class MergeResult implements Merge<Range, Range> {
    @Override
    public Range merge(Range[] ranges) throws Exception {
        if (ranges.length==1) {
            return ranges[0];
        } else {
            int include = ranges[0].result;
            int exclude = ranges[1].result + Range.values[ranges[1].index];
            if (include > exclude) {
                return ranges[0];
            } else {
                ranges[1].result = exclude;
                return ranges[1];
            }
        }
    }
}
