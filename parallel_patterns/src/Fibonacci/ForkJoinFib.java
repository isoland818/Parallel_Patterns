package Fibonacci;


import java.util.concurrent.RecursiveTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFib extends RecursiveTask<Long> {
    private final int n;

    private final int granularity;

    public ForkJoinFib(int n, int granularity) {
        this.n = n;
        this.granularity=granularity;
    }

    @Override
    protected Long compute() {
        if (n <= granularity) {
            return SequentialFib.fib(n);
        }

        ForkJoinFib fib1 = new ForkJoinFib(n - 1, granularity);
        ForkJoinFib fib2 = new ForkJoinFib(n - 2, granularity);

        invokeAll(fib1, fib2);

        return fib1.join()+ fib2.join();
    }
}
