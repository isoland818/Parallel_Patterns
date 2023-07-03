package Knapsack;

import Knapsack.Skandium.Knapsack;

import java.io.*;
import java.util.concurrent.*;

public class KnapsackBenchmark {

    public static int[] values = new int[40];
    public static int[] weights = new int[40];
    public static int capacity;

    private static long sequentialTime = 1;
    static {
        try {
            readProblem();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void knapsackBenchmark() throws ExecutionException, InterruptedException {
        int index=39;
        int granularity = 30;

        sequentialTest();
        System.out.println();

        threadingTest(index, granularity);
        System.out.println();

        forkJoinTest(index, granularity);
        System.out.println();

        cachedPoolTest(index, granularity);
        System.out.println();

        fixedPoolTest(index, granularity);
        System.out.println();

        skandiumTest(index, granularity);
        System.out.println();
    }

    public static void sequentialTest() {
        long stime = System.nanoTime();
        System.out.println("Solution produced by Sequential is: "+SequentialKnapsack.knapsack(values, weights, capacity, 39));
        long etime = System.nanoTime();
        System.out.println("Time cost is: "+(etime-stime)+" nm");
        sequentialTime=etime-stime;
    }

    public static void threadingTest(int index, int granularity) {
        ThreadingKnapsack.weights=weights;
        ThreadingKnapsack.values=values;
        long stime = System.nanoTime();
        ThreadingKnapsack knapsack = new ThreadingKnapsack(index, granularity, capacity);
        knapsack.start();
        try {
            knapsack.join();
            int result = knapsack.getResult();
            long etime = System.nanoTime();
            System.out.println("Solution produced by Threading is: "+result);
            System.out.println("Time cost is: "+(etime-stime)+" nm");
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void forkJoinTest(int index, int granularity) {
        ForkJoinPool pool = new ForkJoinPool(8);
        ForkJoinKnapsack.weights=weights;
        ForkJoinKnapsack.values=values;
        ForkJoinKnapsack knapsack = new ForkJoinKnapsack(index, granularity, capacity);
        long stime = System.nanoTime();
        System.out.println("Solution produced by ForkJoin is: "+pool.invoke(knapsack));
        long etime = System.nanoTime();
        System.out.println("Time cost is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));

    }

    public static void cachedPoolTest(int index, int granularity) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorServiceKnapsack.weights=weights;
        ExecutorServiceKnapsack.values=values;
        long stime = System.nanoTime();
        Future<Integer> future = executorService.submit(() -> ExecutorServiceKnapsack.knapsack(index, granularity, capacity, executorService));
        try {
            int result = future.get();
            long etime = System.nanoTime();
            executorService.shutdown();
            System.out.println("Solution produced by Cached Thread Pool is: "+result);
            System.out.println("Time cost is: "+(etime-stime)+" nm");
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void fixedPoolTest(int index, int granularity) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorServiceKnapsack.weights=weights;
        ExecutorServiceKnapsack.values=values;
        long stime = System.nanoTime();
        try {
            int result = ExecutorServiceKnapsack.knapsackNewPool(index, granularity, capacity);
            long etime = System.nanoTime();
            executorService.shutdown();
            System.out.println("Solution produced by Fixed Thread Pool is: "+result);
            System.out.println("Time cost is: "+(etime-stime)+" nm");
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void skandiumTest(int index, int granularity) throws ExecutionException, InterruptedException {
        long stime = System.nanoTime();
        int result = Knapsack.knapsack(index, capacity, granularity, 8);
        long etime = System.nanoTime();
        System.out.println("Solution produced by Skandium is: "+result);
        System.out.println("Time cost is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
    }

    public static void readProblem() throws IOException {
        File file = new File("D:\\Edinburgh\\Parallell_Patterns\\Parallel_Patterns\\parallel_patterns\\benchmarks\\knapsack.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        capacity=Integer.parseInt(br.readLine());
        String line;
        int index = 0;
        while((line=br.readLine())!=null) {
            String[] lineContent = line.split(",");
            weights[index] = Integer.parseInt(lineContent[0]);
            values[index] = Integer.parseInt(lineContent[1]);
            index++;
        }
    }
}
