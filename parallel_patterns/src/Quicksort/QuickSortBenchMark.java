package Quicksort;

import Quicksort.SkandiumQuickSort.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class QuickSortBenchMark {
    private static List<int[]> cases = new ArrayList<>();
    private static List<int[]> results = new ArrayList<>();
    private static int size = 32795;
    private static long sequentialTime=1;

    static {
        generateCases();
    }

    public static void sortBenchmark () {
        int granularity = 4096;

        SequentialQuickSortTest();
        System.out.println();

        ThreadingQuickSortTest(granularity);
        System.out.println();

        ForkJoinQuickSortTest(granularity, 8);
        System.out.println();

        cachedThreadPoolTest(granularity);
        System.out.println();

        fixedThreadPoolTest(granularity);
        System.out.println();




    }

    private static void generateCases () {
        for (int i = 0; i < 1; i++) {
            int newCase[] = new int[size];
            for (int j = 0; j < size; j++) {
                newCase[j] = (int) (Math.random() * 5* size);
            }
            cases.add(newCase);
        }
    }

    public static void SequentialQuickSortTest () {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = copyCase(cases.get(i));
            long stime = System.nanoTime();
            SequentialQuickSort.quickSort(curCase, 0, curCase.length-1);
            totalTime+=System.nanoTime()-stime;
            results.add(curCase);
        }
        System.out.println("Average time cost by Sequential Quick Sort is: " + totalTime/cases.size() + " nm");
        sequentialTime=totalTime/cases.size();
    }

    public static void ThreadingQuickSortTest (int granularity){
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
            System.out.println(i+": "+checkSort(curCase, results.get(i)));
        }
        System.out.println("Average time cost by Threading Sort is: " + totalTime/cases.size() + " nm");
        System.out.println("Speedup is: " + cases.size()*sequentialTime/totalTime);
    }

    public static void ForkJoinQuickSortTest (int granularity, int poolSize) {
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            ForkJoinQuickSort sort = new ForkJoinQuickSort(curCase, 0, curCase.length-1, granularity);
            pool.invoke(sort);
            totalTime+=System.nanoTime()-stime;
            System.out.println(i+": "+checkSort(curCase, results.get(i)));
        }
        System.out.println("Average time cost by ForkJoin Quick Sort is: " + totalTime/cases.size() + " nm");
        System.out.println("Speedup is: " + cases.size()*sequentialTime/totalTime);
    }

    public static void cachedThreadPoolTest (int granulartiy) {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            ExecutorService executorService = Executors.newCachedThreadPool();
            long stime = System.nanoTime();
            ExecutorServiceQuickSort.quickSort(curCase, 0, curCase.length-1, granulartiy, executorService);
//            Future future = executorService.submit(() -> ExecutorServiceQuickSort.quickSort(curCase, 0, curCase.length-1, granulartiy, executorService));
//            while(!future.isDone());
            totalTime+=System.nanoTime()-stime;
            executorService.shutdown();
            System.out.println(i+": "+checkSort(curCase, results.get(i)));
        }
        System.out.println("Average time cost by Cached Thread Pool Quick Sort is: " + totalTime/cases.size() + " nm");
        System.out.println("Speedup is: " + cases.size()*sequentialTime/totalTime);
    }

    public static void fixedThreadPoolTest (int granulartiy) {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            long stime = System.nanoTime();
//            ExecutorServiceQuickSort.quickSortNewPool(curCase, 0, curCase.length-1, granulartiy, Executors.newFixedThreadPool(2));
            executorService.submit(() -> ExecutorServiceQuickSort.quickSortNewPool(curCase, 0, curCase.length-1, granulartiy, Executors.newFixedThreadPool(2)));
            executorService.shutdown();
            totalTime+=System.nanoTime()-stime;
            try{
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                System.out.println(i+": "+checkSort(curCase, results.get(i)));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Average time cost by Fixed Thread Pool Quick Sort is: " + totalTime/cases.size() + " nm");
        System.out.println("Speedup is: " + cases.size()*sequentialTime/totalTime);
    }

    public static void skandiumTest(int granularity) throws ExecutionException, InterruptedException {
        long totalTime = 0;
        for (int i = 0; i < cases.size(); i++) {
            int[] curCase = cases.get(i);
            long stime = System.nanoTime();
            QuickSort.quickSort(curCase);
            totalTime+=System.nanoTime()-stime;
            System.out.println(i+": "+checkSort(curCase, results.get(i)));
        }
        System.out.println("Average time cost by Fixed Thread Pool Quick Sort is: " + totalTime/cases.size() + " nm");
        System.out.println("Speedup is: " + cases.size()*sequentialTime/totalTime);
    }

    public static int[] copyCase (int[] curCase) {
        int n = curCase.length;
        int[] copy = new int[n];
        for (int i = 0; i < n; i++) {
            copy[i] = curCase[i];
        }
        return copy;
    }

    public static boolean checkSort (int[] answer, int[] result) {
        for (int i = 0; i < size; i++) {
            if (answer[i] != result[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < size; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
}
