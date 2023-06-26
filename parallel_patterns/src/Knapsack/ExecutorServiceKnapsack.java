package Knapsack;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceKnapsack {
    public static int[] values;
    public static int[] weights;

    public static int knapsack(int index, int granularity, int capacity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        if (index < granularity) {
            return SequentialKnapsack.knapsack(values, weights, capacity, index);
        }

        if (weights[index] > capacity) {
            Future<Integer> knapsack = executorService.submit(() -> knapsack(index-1, granularity, capacity, executorService));
            return knapsack.get();
        }else {
            Future<Integer> includeItem = executorService.submit(() -> knapsack(index-1, granularity, capacity-weights[index], executorService));
            Future<Integer> excludeItem = executorService.submit(() -> knapsack(index-1, granularity, capacity, executorService));
            return Math.max(values[index]+includeItem.get(), excludeItem.get());
        }
    }

    public static int knapsackNewPool(int index, int granularity, int capacity) throws ExecutionException, InterruptedException {
        if (index < granularity) {
            return SequentialKnapsack.knapsack(values, weights, capacity, index);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        if (weights[index] > capacity) {
            Future<Integer> knapsack = executorService.submit(() -> knapsackNewPool(index-1, granularity, capacity));
            executorService.shutdown();
            return knapsack.get();
        }else {
            Future<Integer> includeItem = executorService.submit(() -> knapsackNewPool(index-1, granularity, capacity-weights[index]));
            Future<Integer> excludeItem = executorService.submit(() -> knapsackNewPool(index-1, granularity, capacity));
            executorService.shutdown();
            return Math.max(values[index]+includeItem.get(), excludeItem.get());
        }

    }
}
