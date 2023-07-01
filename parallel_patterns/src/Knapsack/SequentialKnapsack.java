package Knapsack;

import java.util.Arrays;

public class SequentialKnapsack {

    public static int knapsack(int[] values, int[] weights, int capacity, int index) {
        if (index < 0 || capacity == 0) {
            return 0;
        }

        if (weights[index] > capacity) {
            return knapsack(values, weights, capacity, index - 1);
        } else {
            int includeItem = values[index] + knapsack(values, weights, capacity - weights[index], index - 1);
            int excludeItem = knapsack(values, weights, capacity, index - 1);
            return Math.max(includeItem, excludeItem);
        }
    }

}

