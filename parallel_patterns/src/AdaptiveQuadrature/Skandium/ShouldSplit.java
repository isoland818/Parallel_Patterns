package AdaptiveQuadrature.Skandium;

import AdaptiveQuadrature.SequentialAQ;
import cl.niclabs.skandium.muscles.Condition;

public class ShouldSplit implements Condition<Range> {
    private final double granularity;

    public ShouldSplit(double granularity) {
        this.granularity=granularity;
    }
    @Override
    public boolean condition(Range range) throws Exception {
        double larea = (range.fleft + range.fmid) * (range.mid - range.left) / 2;
        double rarea = (range.fmid + range.fright) * (range.right - range.mid) / 2;
        return Math.abs((larea + rarea) - range.lrarea) > granularity;
    }
}
