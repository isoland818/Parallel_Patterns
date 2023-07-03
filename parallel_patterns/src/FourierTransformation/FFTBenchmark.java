package FourierTransformation;

import FourierTransformation.Skandium.FFT;

import java.util.concurrent.*;

public class FFTBenchmark {
    private static long sequentialTime = 1;
    private static int size = (int) Math.pow(2, 15);
    private static Complex[] answer = new Complex[size];
    private static Complex[] x = new Complex[size];

    static {
        generateSignals();
    }
    public static void fftBenchmark () throws ExecutionException, InterruptedException {
        int granularity = (int) Math.pow(2, 13);

        sequentialTest();
        System.out.println();
//
//        threadingTest(granularity);
//        System.out.println();
//
//        forkJoinTest(granularity);
//        System.out.println();
//
//        cachedPoolTest(granularity);
//        System.out.println();
//
//        fixedPoolTest(granularity);
//        System.out.println();

        skandiumTest(granularity);
        System.out.println();
    }

    public static void sequentialTest() {
        long stime = System.nanoTime();
        answer = SequentialFFT.fft(x);
        long etime = System.nanoTime();
        System.out.println("Time cost by sequential solution is: "+(etime-stime));
        sequentialTime = etime-stime;
    }

    public static void threadingTest(int granularity) {
        long stime = System.nanoTime();
        ThreadingFFT fft = new ThreadingFFT(x, granularity);
        fft.start();
        try {
            fft.join();
            Complex[] result = fft.getResult();
            long etime = System.nanoTime();
            System.out.println(checkResult(result, answer));
            System.out.println("Time cost by Threading solution is: "+(etime-stime));
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void forkJoinTest(int granularity) {
        ForkJoinPool pool = new ForkJoinPool(8);
        ForkJoinFFT fft = new ForkJoinFFT(x, granularity);
        long stime = System.nanoTime();
        Complex[] result = pool.invoke(fft);
        System.out.println(checkResult(result, answer));
        long etime = System.nanoTime();
        System.out.println("Time cost by forkjoin solution is: "+(etime-stime));
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
    }

    public static void cachedPoolTest(int granularity) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long stime = System.nanoTime();
        Future<Complex[]> future = executorService.submit(() -> ExecutorServiceFFT.executorServiceFFT(x, granularity, executorService));
        Complex[] result = future.get();
        System.out.println(checkResult(result, answer));
        long etime = System.nanoTime();
        executorService.shutdown();
        System.out.println("Time cost by cached pool is: "+(etime-stime));
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
    }

    public static void fixedPoolTest(int granularity) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        long stime = System.nanoTime();
        Future<Complex[]> future = executorService.submit(() -> ExecutorServiceFFT.executorServiceFFTNewPool(x, granularity, Executors.newFixedThreadPool(2)));
        Complex[] result = future.get();
        System.out.println(checkResult(result, answer));
        long etime = System.nanoTime();
        System.out.println("Time cost by fixed pool is: "+(etime-stime));
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
    }

    public static void skandiumTest(int granularity) throws ExecutionException, InterruptedException {
        long stime = System.nanoTime();
        Complex[] result = FFT.fft(x, granularity, 8);
        System.out.println(checkResult(result, answer));
        long etime = System.nanoTime();
        System.out.println("Time cost by skandium is: "+(etime-stime));
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
    }

    private static void generateSignals() {
        for (int i = 0; i < size; i++) {
            x[i] = new Complex(Math.random()*200, 0);
        }
    }

    private static boolean checkResult(Complex[] result, Complex[] answer) {
        for (int i = 0; i < size; i++) {
            if (!result[i].equals(answer[i])) {
                return false;
            }
        }
        return true;
    }
}
