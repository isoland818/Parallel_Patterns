package Fibonacci.Skandium;

import Fibonacci.SequentialFib;
import cl.niclabs.skandium.muscles.Execute;

public class Calculate implements Execute<Integer, Long> {
    @Override
    public Long execute(Integer n) throws Exception {
        return SequentialFib.fib(n);
    }
}
