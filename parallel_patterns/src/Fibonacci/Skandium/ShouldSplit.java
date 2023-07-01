package Fibonacci.Skandium;

import cl.niclabs.skandium.muscles.Condition;

public class ShouldSplit implements Condition<Integer> {
    private int granularity;

    public ShouldSplit(int granularity) {
        this.granularity=granularity;
    }
    @Override
    public boolean condition(Integer n) throws Exception {
        return n > granularity;
    }
}
