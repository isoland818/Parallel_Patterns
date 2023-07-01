package Fibonacci.Skandium;

import cl.niclabs.skandium.muscles.Split;

public class SplitProblem implements Split<Integer, Integer> {
    @Override
    public Integer[] split(Integer n) throws Exception {
        return new Integer[]{n - 1, n - 2};
    }
}
