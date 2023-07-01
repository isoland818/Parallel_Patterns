package AdaptiveQuadrature.Skandium;

import AdaptiveQuadrature.SequentialAQ;
import cl.niclabs.skandium.muscles.Execute;

public class Calculate implements Execute<Range, Double> {
    @Override
    public Double execute(Range range) throws Exception {
        return SequentialAQ.quad(range.left, range.right, range.fleft, range.fright, range.lrarea);
    }
}
