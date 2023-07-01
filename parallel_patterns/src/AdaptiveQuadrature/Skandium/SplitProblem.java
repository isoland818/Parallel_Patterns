package AdaptiveQuadrature.Skandium;

import AdaptiveQuadrature.SequentialAQ;
import cl.niclabs.skandium.muscles.Split;

public class SplitProblem implements Split<Range, Range> {
    @Override
    public Range[] split(Range range) throws Exception {
        double larea = (range.fleft + range.fmid) * (range.mid - range.left) / 2;
        double rarea = (range.fmid + range.fright) * (range.right - range.mid) / 2;
        return new Range[]{
                new Range(range.left, range.mid, range.fleft, range.fmid, larea),
                new Range(range.mid, range.right, range.fmid, range.fright, rarea)
        };
    }
}
