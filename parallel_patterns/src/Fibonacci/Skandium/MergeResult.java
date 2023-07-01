package Fibonacci.Skandium;

import cl.niclabs.skandium.muscles.Merge;

public class MergeResult implements Merge<Long, Long> {
    @Override
    public Long merge(Long[] longs) throws Exception {
        return longs[0] + longs[1];
    }
}
