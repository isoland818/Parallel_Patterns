package Knapsack;

import Fibonacci.ThreadingFib;

public class ThreadingKnapsack extends Thread{
    public static int[] values;
    public static int[] weights;

    private int index;
    private int granularity;
    private int capacity;
    private int result;

    public ThreadingKnapsack (int index, int granularity, int capacity) {
        this.index=index;
        this.granularity=granularity;
        this.capacity=capacity;
    }

    @Override
    public void run(){
        if (index < granularity) {
            result = SequentialKnapsack.knapsack(values, weights, capacity, index);
            return;
        }
        if (weights[index] > capacity) {
            ThreadingKnapsack knapsack = new ThreadingKnapsack(index-1, granularity, capacity);
            knapsack.start();
            try{
                knapsack.join();
                result= knapsack.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            ThreadingKnapsack includeItem = new ThreadingKnapsack(index-1, granularity, capacity-weights[index]);
            ThreadingKnapsack excludeItem = new ThreadingKnapsack(index-1, granularity, capacity);

            includeItem.start();
            excludeItem.start();
            try {
                includeItem.join();
                excludeItem.join();
                result=Math.max(values[index]+includeItem.getResult(), excludeItem.getResult());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getResult(){
        return result;
    }
}
