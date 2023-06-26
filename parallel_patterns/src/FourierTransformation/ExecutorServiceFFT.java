package FourierTransformation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceFFT {
    public static Complex[] executorServiceFFT (Complex[] x, int granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        int n = x.length;

        if (n <=granularity) {
            return SequentialFFT.fft(x);
        }

        Complex[] even = new Complex[n/2];
        Complex[] odd = new Complex[n/2];

        for (int i = 0; i < n/2; i++) {
            even[i] = x[2*i];
            odd[i] = x[2*i+1];
        }

        Future<Complex[]> evenTask = executorService.submit(() -> executorServiceFFT(even, granularity, executorService));
        Future<Complex[]> oddTask = executorService.submit(() -> executorServiceFFT(odd, granularity, executorService));

        Complex[] evenResult = evenTask.get();
        Complex[] oddResult = oddTask.get();

        Complex[] result = new Complex[n];
        for (int i = 0; i < n/2; i++) {
            double theta = -2 * Math.PI * i /n;
            Complex twiddle = new Complex(Math.cos(theta), Math.sin(theta));
            result[i] = evenResult[i].plus(twiddle.times(oddResult[i]));
            result[i + n/2] = evenResult[i].minus(twiddle.times(oddResult[i]));
        }
        return result;
    }

    public static Complex[] executorServiceFFTNewPool (Complex[] x, int granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        int n = x.length;

        if (n <=granularity) {
            return SequentialFFT.fft(x);
        }

        Complex[] even = new Complex[n/2];
        Complex[] odd = new Complex[n/2];

        for (int i = 0; i < n/2; i++) {
            even[i] = x[2*i];
            odd[i] = x[2*i+1];
        }

        Future<Complex[]> evenTask = executorService.submit(() -> executorServiceFFTNewPool(even, granularity, Executors.newFixedThreadPool(2)));
        Future<Complex[]> oddTask = executorService.submit(() -> executorServiceFFTNewPool(odd, granularity, Executors.newFixedThreadPool(2)));

        executorService.shutdown();

        Complex[] evenResult = evenTask.get();
        Complex[] oddResult = oddTask.get();

        Complex[] result = new Complex[n];
        for (int i = 0; i < n/2; i++) {
            double theta = -2 * Math.PI * i /n;
            Complex twiddle = new Complex(Math.cos(theta), Math.sin(theta));
            result[i] = evenResult[i].plus(twiddle.times(oddResult[i]));
            result[i + n/2] = evenResult[i].minus(twiddle.times(oddResult[i]));
        }
        return result;
    }
}
