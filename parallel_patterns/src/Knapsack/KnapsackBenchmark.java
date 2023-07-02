package Knapsack;

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

    public static void knapsackBenchmark(){

        int granularity = 30;

        sequentialTest();
        System.out.println();

        forkJoinTest(granularity);
        System.out.println();

        executorServiceTest(granularity);
        System.out.println();
    }

    public static void sequentialTest() {
        long stime = System.nanoTime();
        System.out.println("Solution produced by Sequential is: "+SequentialKnapsack.knapsack(values, weights, capacity, 39));
        long etime = System.nanoTime();
        System.out.println("Time cost is: "+(etime-stime)+" nm");
        sequentialTime=etime-stime;
    }

    public static void forkJoinTest(int granularity) {
        ForkJoinPool pool = new ForkJoinPool(8);
        ForkJoinKnapsack.weights=weights;
        ForkJoinKnapsack.values=values;
        ForkJoinKnapsack knapsack = new ForkJoinKnapsack(39, 15, 50);
        long stime = System.nanoTime();
        System.out.println("Solution produced by ForkJoin is: "+pool.invoke(knapsack));
        long etime = System.nanoTime();
        System.out.println("Time cost is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));

    }

    public static void executorServiceTest(int granularity) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorServiceKnapsack.weights=weights;
        ExecutorServiceKnapsack.values=values;
        long stime = System.nanoTime();
        Future<Integer> future = executorService.submit(() -> ExecutorServiceKnapsack.knapsack(39, granularity, capacity, executorService));
        try {
            int result = future.get();
            long etime = System.nanoTime();
            executorService.shutdown();
            System.out.println("Solution produced by ForkJoin is: "+result);
            System.out.println("Time cost is: "+(etime-stime)+" nm");
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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
