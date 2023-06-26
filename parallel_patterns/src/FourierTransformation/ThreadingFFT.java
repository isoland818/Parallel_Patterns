package FourierTransformation;

import java.util.concurrent.Future;

public class ThreadingFFT extends Thread{

    private final Complex[] x;

    private final int granularity;
    private Complex[] result;

    public ThreadingFFT(Complex[] x, int granularity) {
        this.x=x;
        this.granularity=granularity;
    }

    @Override
    public void run() {
        int n = x.length;

        if (n <=granularity) {
            result = SequentialFFT.fft(x);
            return;
        }

        Complex[] even = new Complex[n/2];
        Complex[] odd = new Complex[n/2];

        for (int i = 0; i < n/2; i++) {
            even[i] = x[2*i];
            odd[i] = x[2*i+1];
        }

        ThreadingFFT evenTask = new ThreadingFFT(even, granularity);
        ThreadingFFT oddTask = new ThreadingFFT(odd, granularity);

        evenTask.start();
        oddTask.start();

        try {
            evenTask.join();
            oddTask.join();

            Complex[] evenResult = evenTask.getResult();
            Complex[] oddResult = oddTask.getResult();

            result = new Complex[n];
            for (int i = 0; i < n/2; i++) {
                double theta = -2 * Math.PI * i /n;
                Complex twiddle = new Complex(Math.cos(theta), Math.sin(theta));
                result[i] = evenResult[i].plus(twiddle.times(oddResult[i]));
                result[i + n/2] = evenResult[i].minus(twiddle.times(oddResult[i]));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Complex[] getResult() {
        return result;
    }
}
