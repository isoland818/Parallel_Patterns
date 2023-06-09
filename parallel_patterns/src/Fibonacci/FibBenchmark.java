package Fibonacci;

import Fibonacci.Skandium.Fib;
import cl.niclabs.skandium.Skandium;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

public class FibBenchmark {

    private static long sequentialTime = 1;
    public static void fibBenchmark () {
        int nFib = 44;
        int granularity = 37;
        int poolSize = 8;

        sequentialFibTest(nFib);
        System.out.println();

        threadingFibTest(nFib, granularity);
        System.out.println();

        forkJoinTest(nFib, granularity, poolSize);
        System.out.println();

        cachedThreadFibTest(nFib, granularity);
        System.out.println();

        fixedThreadFibTest(nFib, granularity);
        System.out.println();

        skandiumFibTest(nFib, granularity, 8);
        System.out.println();
    }

    public static void sequentialFibTest (int n) {
        long stime = System.nanoTime();
        System.out.println("Result from sequential solution is: " + SequentialFib.fib(n));
        long etime = System.nanoTime();
        System.out.println("Time cost by sequential solution: " + (etime-stime) + " nm");
        sequentialTime =  etime-stime;
    }

    public static void threadingFibTest(int n, int granularity) {
        ThreadingFib fib = new ThreadingFib(n, granularity);
        long stime = System.nanoTime();
        fib.start();
        try{
            fib.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long etime = System.nanoTime();
        System.out.println("Result from threading solution is: "+fib.getResult());
        System.out.println("Time cost by threading solution is: " + (etime-stime)+ " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void forkJoinTest (int n, int granularity, int poolSize) {
        ForkJoinFib fib = new ForkJoinFib(n, granularity);
        ForkJoinPool pool = new ForkJoinPool(poolSize);
        long stime = System.nanoTime();
        System.out.println("Result from ForkJoinPool solution is: " + pool.invoke(fib));
        long etime = System.nanoTime();
        System.out.println("Time cost by ForkJoinPool solution: " + (etime-stime) + " nm");
        System.out.println(sequentialTime);
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void cachedThreadFibTest (int n, int granularity) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long stime = System.nanoTime();
        executorServiceTest(n, granularity, executorService);
        long etime = System.nanoTime();
        System.out.println("Time cost by cachedThreadPool is: " + (etime-stime) + " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void fixedThreadFibTest (int n, int granularity) {
        long stime = System.nanoTime();
        try{
            System.out.println("Result from ExecutorService solution is: " + ExecutorServiceFib.fibNewPool(n, granularity, Executors.newFixedThreadPool(2)));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        long etime = System.nanoTime();
        System.out.println("Time cost by fixedThreadPool is: " + (etime-stime) + " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static long scheduledThreadFibTest (int n, int granularity) {
        long stime = System.nanoTime();
        try{
            System.out.println("Result from ExecutorService solution is: " + ExecutorServiceFib.fibNewPool(n, granularity, Executors.newScheduledThreadPool(2)));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        long etime = System.nanoTime();
        System.out.println("Time cost by scheduledThreadPool is: " + (etime-stime) + " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
        return etime-stime;
    }

    public static long singleThreadFibTest (int n, int granularity) {
        long stime = System.nanoTime();
        try{
            System.out.println("Result from ExecutorService solution is: " + ExecutorServiceFib.fibNewPool(n, granularity, Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        long etime = System.nanoTime();
        System.out.println("Time cost by singleThreadPool is: " + (etime-stime) + " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
        return etime-stime;
    }

    public static long skandiumFibTest(int n, int granularity, int pthreads) {
        long stime = System.nanoTime();
        long result=0;
        try{
            result = Fib.fib(n, granularity, pthreads);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long etime = System.nanoTime();
        System.out.println("Result from Skandium solution is: "+ result);
        System.out.println("Time cost by Skandium solution is: " + (etime-stime)+ " nm");
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
        return (etime-stime);
    }
}
