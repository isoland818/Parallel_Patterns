package AdaptiveQuadrature.Skandium;

import cl.niclabs.skandium.muscles.Merge;

public class MergeResult implements Merge<Double, Double> {
    @Override
    public Double merge(Double[] results) throws Exception {
        return results[0] + results[1];
    }
}
