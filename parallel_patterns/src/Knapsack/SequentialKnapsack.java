package Knapsack;

import java.util.Arrays;

public class SequentialKnapsack {

    // Function to solve the knapsack problem
    public static int knapsack(int[] values, int[] weights, int capacity) {
        int n = values.length;
        return knapsackHelper(values, weights, capacity, n - 1);
    }

    // Helper function to solve the knapsack problem recursively
    private static int knapsackHelper(int[] values, int[] weights, int capacity, int index) {
        if (index < 0 || capacity == 0) {
            return 0;
        }

        if (weights[index] > capacity) {
            return knapsackHelper(values, weights, capacity, index - 1);
        } else {
            int includeItem = values[index] + knapsackHelper(values, weights, capacity - weights[index], index - 1);
            int excludeItem = knapsackHelper(values, weights, capacity, index - 1);
            return Math.max(includeItem, excludeItem);
        }
    }

}

