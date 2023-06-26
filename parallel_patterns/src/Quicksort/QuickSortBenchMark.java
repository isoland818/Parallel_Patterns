package Quicksort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class QuickSortBenchMark {
    private static List<int[]> cases = new ArrayList<>();

    private static long sequentialTime=1;

    static {
        generateCases();
    }

    public static void sortBenchmark () {
        int granularity = 256;

        SequentialQuickSortTest();
        System.out.println();

        ThreadingQuickSortTest(granularity);
        System.out.println();

//        ForkJoinQuickSortTest(granularity, 32);
//        System.out.println();
//
//        cachedThreadPoolTest(granularity);
//        System.out.println();
//
//        fixedThreadPoolTest(granularity);
//        System.out.println();


    }

    private static void generateCases () {
        for (int i = 0; i < 5; i++) {
            int newCase[] = new int[10000];
            for (int j = 0; j < 10000; j++) {
                newCase[j] = (int) (Math.random() * 500000);
            }
            cases.add(newCase);
        }
    }

    public static long SequentialQuickSortTest () {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            SequentialQuickSort.quickSort(curCase, 0, curCase.length-1);
            totalTime+=System.nanoTime()-stime;
        }
        System.out.println("Average time cost by Sequential Quick Sort is: " + totalTime/5 + " nm");
        sequentialTime=totalTime/5;
        return totalTime/5;
    }

    public static long ThreadingQuickSortTest (int granularity){
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            ThreadingQuickSort sort = new ThreadingQuickSort(curCase, 0, curCase.length-1, granularity);
            sort.start();
            try{
                sort.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalTime+=System.nanoTime()-stime;
        }
        System.out.println("Average time cost by Threading Sort is: " + totalTime/5 + " nm");
        System.out.println("Speedup is: " + 5*sequentialTime/totalTime);
        sequentialTime=totalTime/5;
        return totalTime/5;
    }

    public static long ForkJoinQuickSortTest (int granularity, int poolSize) {
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            ForkJoinQuickSort sort = new ForkJoinQuickSort(curCase, 0, curCase.length-1, granularity);
            pool.invoke(sort);
            totalTime+=System.nanoTime()-stime;
        }
        System.out.println("Average time cost by ForkJoin Quick Sort is: " + totalTime/5 + " nm");
        return totalTime/5;
    }

    public static long cachedThreadPoolTest (int granulartiy) {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            ExecutorService pool = Executors.newCachedThreadPool();
            long stime = System.nanoTime();
            ExecutorServiceQuickSort.quickSort(curCase, 0, curCase.length-1, granulartiy, pool);
            totalTime+=System.nanoTime()-stime;
            pool.shutdown();
        }
        System.out.println("Average time cost by Cached Thread Pool Quick Sort is: " + totalTime/5 + " nm");
        return totalTime/5;
    }

    public static long fixedThreadPoolTest (int granulartiy) {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            ExecutorServiceQuickSort.quickSortNewPool(curCase, 0, curCase.length-1, granulartiy, Executors.newFixedThreadPool(2));
            totalTime+=System.nanoTime()-stime;
        }
        System.out.println("Average time cost by Fixed Thread Pool Quick Sort is: " + totalTime/5 + " nm");
        return totalTime/5;
    }

    public static long scheduledThreadPoolTest (int granulartiy) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            ExecutorServiceQuickSort.quickSortNewPool(curCase, 0, curCase.length-1, granulartiy, Executors.newScheduledThreadPool(2));
            long stime = System.nanoTime();
            totalTime+=System.nanoTime()-stime;
        }
        System.out.println("Average time cost by Scheduled Thread Pool Quick Sort is: " + totalTime/5 + " nm");
        return totalTime/5;
    }


    public static int[] copyCase (int[] curCase) {
        int n = curCase.length;
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = curCase[i];
        }
        return copy;
    }
}
