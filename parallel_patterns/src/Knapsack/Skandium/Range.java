package Knapsack.Skandium;

public class Range {
    public static int[] values;
    public static int[] weights;

    int capacity;
    int index;
    int result;

    public Range(int index, int capacity) {
        this.index=index;
        this.capacity=capacity;
        this.result=0;
    }
}
