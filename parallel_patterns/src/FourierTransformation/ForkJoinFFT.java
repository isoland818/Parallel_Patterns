package FourierTransformation;

import java.util.concurrent.RecursiveTask;

public class ForkJoinFFT extends RecursiveTask<Complex[]> {

    private final Complex[] x;

    private final int granularity;

    public ForkJoinFFT(Complex[] x, int granularity) {
        this.x=x;
        this.granularity=granularity;
    }
    @Override
    protected Complex[] compute() {
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

        ForkJoinFFT evenTask = new ForkJoinFFT(even, granularity);
        ForkJoinFFT oddTask = new ForkJoinFFT(odd, granularity);

        invokeAll(evenTask, oddTask);

        Complex[] evenResult = evenTask.join();
        Complex[] oddResult = oddTask.join();

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
