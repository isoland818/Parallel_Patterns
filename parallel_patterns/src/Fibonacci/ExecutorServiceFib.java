package Fibonacci;

import java.util.concurrent.*;

public class ExecutorServiceFib {
    public static Long fib(int n, int granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        if (n<=granularity) {
            return SequentialFib.fib(n);
        }
        Future<Long> leftFuture = executorService.submit(() -> fib(n-1, granularity, executorService));
        Future<Long> rightFuture = executorService.submit(() -> fib(n-2, granularity, executorService));
        
        return leftFuture.get()+rightFuture.get();
    }

    public static Long fibNewPool(int n, int granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        if (n<=granularity) {
            return SequentialFib.fib(n);
        }
        Future<Long> leftFuture = executorService.submit(() -> fibNewPool(n-1, granularity, Executors.newFixedThreadPool(2)));
        Future<Long> rightFuture = executorService.submit(() -> fibNewPool(n-2, granularity, Executors.newFixedThreadPool(2)));

        executorService.shutdown();
        return leftFuture.get()+rightFuture.get();
    }

    public static Long fibNewPool(int n, int granularity, ExecutorService executorServiceLeft, ExecutorService executorServiceRight) throws ExecutionException, InterruptedException {
        if (n<=granularity) {
            return SequentialFib.fib(n);
        }
        Future<Long> leftFuture = executorServiceLeft.submit(() -> fibNewPool(n-1, granularity, Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));
        Future<Long> rightFuture = executorServiceRight.submit(() -> fibNewPool(n-2, granularity, Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));

        executorServiceLeft.shutdown();
        executorServiceRight.shutdown();
        return leftFuture.get()+rightFuture.get();
    }
}
