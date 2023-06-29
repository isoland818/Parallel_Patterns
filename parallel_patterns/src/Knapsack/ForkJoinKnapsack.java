package Knapsack;

import Fibonacci.ForkJoinFib;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinKnapsack extends RecursiveTask<Integer> {
    public static int[] values;
    public static int[] weights;

    private int capacity;

    private int index;

    private int granularity;

    public ForkJoinKnapsack(int index, int granularity, int capacity) {
        this.index=index;
        this.granularity=granularity;
        this.capacity=capacity;
    }
    @Override
    protected Integer compute() {
        if (index < granularity) {
            return SequentialKnapsack.knapsack(values, weights, capacity, index);
        }
        if (weights[index] > capacity) {
            ForkJoinKnapsack knapsack = new ForkJoinKnapsack(index-1, granularity, capacity);
            invokeAll(knapsack);
            return knapsack.join();
        }else {
            ForkJoinKnapsack includeItem = new ForkJoinKnapsack(index-1, granularity, capacity-weights[index]);
            ForkJoinKnapsack excludeItem = new ForkJoinKnapsack(index-1, granularity, capacity);

            invokeAll(includeItem, excludeItem);
            return Math.max(values[index]+includeItem.join(), excludeItem.join());
        }
    }
}
