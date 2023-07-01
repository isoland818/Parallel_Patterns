package Knapsack.Skandium;

import Knapsack.SequentialKnapsack;
import cl.niclabs.skandium.muscles.Execute;

public class Solve implements Execute<Range, Range> {
    @Override
    public Range execute(Range range) throws Exception {
        range.result=SequentialKnapsack.knapsack(Range.values, Range.weights, range.capacity, range.index);
        return range;
    }
}
