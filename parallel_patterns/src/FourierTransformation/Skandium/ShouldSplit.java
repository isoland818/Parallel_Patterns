package FourierTransformation.Skandium;

import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Split;

public class ShouldSplit implements Condition<Range> {
    private int granularity;

    public ShouldSplit(int granularity) {
        this.granularity=granularity;
    }

    @Override
    public boolean condition(Range range) throws Exception {
        return range.n > granularity;
    }
}
