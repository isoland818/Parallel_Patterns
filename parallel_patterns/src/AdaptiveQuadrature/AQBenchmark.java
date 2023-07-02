package AdaptiveQuadrature;

import AdaptiveQuadrature.Skandium.Quad;

import java.util.concurrent.*;

public class AQBenchmark {
    private static long sequentialTime = 1;
    public static void AQBenchmark() throws ExecutionException, InterruptedException {
        double left = 0;
        double right = 50;
        int granularity = 1280;

        double fleft = SequentialAQ.func(left);
        double fright = SequentialAQ.func(right);

        sequentialTest(left, right, fleft, fright);
        System.out.println();

        forkJoinTest(left, right, fleft, fright, granularity);
        System.out.println();

        cachedPoolTest(left, right, fleft, fright, granularity);
        System.out.println();

        fixedPoolTest(left, right, fleft, fright, granularity);
        System.out.println();

        skandiumTest(left, right, fleft, fright, granularity);
        System.out.println();
    }

    public static void sequentialTest(double left, double right, double fleft, double fright) {
        long stime = System.nanoTime();
        System.out.println("Result produced by sequential is: " +SequentialAQ.quad(left, right, fleft, fright, 0));
        long etime = System.nanoTime();
        sequentialTime = etime-stime;
        System.out.println("Time cost by sequential solution is: " + (etime-stime));
    }

    public static void forkJoinTest(double left, double right, double fleft, double fright, double granularity) {
        ForkJoinPool pool = new ForkJoinPool(8);
        ForkJoinAQ aq = new ForkJoinAQ(left, right, fleft, fright, 0, granularity);
        long stime = System.nanoTime();
        System.out.println("Result produced by ForkJoin is: " + pool.invoke(aq));
        long etime = System.nanoTime();
        pool.shutdown();
        System.out.println("Time cost by ForkJoin solution is: " + (etime-stime));
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void cachedPoolTest(double left, double right, double fleft, double fright, double granularity) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long stime = System.nanoTime();
        Future<Double> future = executorService.submit(() -> ExecutorServiceAQ.quad(left, right, fleft, fright, 0, granularity, executorService));
        System.out.println("Result produced by Cached thread pool is: " + future.get());
        long etime = System.nanoTime();
        executorService.shutdown();
        System.out.println("Time cost by Cached thread pool solution is: " + (etime-stime));
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void fixedPoolTest(double left, double right, double fleft, double fright, double granularity) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long stime = System.nanoTime();
        Future<Double> future = executorService.submit(() -> ExecutorServiceAQ.quadNewPool(left, right, fleft, fright, 0, granularity, executorService));
        System.out.println("Result produced by fixed thread pool is: " + future.get());
        long etime = System.nanoTime();
        System.out.println("Time cost by fixed thread pool solution is: " + (etime-stime));
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }

    public static void skandiumTest(double left, double right, double fleft, double fright, double granularity) throws ExecutionException, InterruptedException {
        long stime = System.nanoTime();
        System.out.println("Result produced by skandium is: " + Quad.quad(left, right, fleft, fright, 0, granularity, 8));
        long etime = System.nanoTime();
        System.out.println("Time cost by skandium solution is: " + (etime-stime));
        System.out.println("Speedup is: " + sequentialTime/(etime-stime));
    }
}
