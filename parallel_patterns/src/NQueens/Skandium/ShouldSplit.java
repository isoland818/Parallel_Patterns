package NQueens.Skandium;

import cl.niclabs.skandium.muscles.Condition;

public class ShouldSplit implements Condition<Range> {
    private int granularity;

    public ShouldSplit(int granularity) {
        this.granularity=granularity;
    }
    @Override
    public boolean condition(Range range) throws Exception {
        return range.size - range.col > granularity;
    }
}
