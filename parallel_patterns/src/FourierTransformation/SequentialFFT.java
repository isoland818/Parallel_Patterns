package FourierTransformation;

public class SequentialFFT {
    public static Complex[] fft(Complex[] x) {
        int n = x.length;

        // Base case: if the input size is 1, return the input
        if (n == 1) {
            return x;
        }

        // Divide the input into even and odd indices
        Complex[] even = new Complex[n/2];
        Complex[] odd = new Complex[n/2];
        for (int i = 0; i < n/2; i++) {
            even[i] = x[2*i];
            odd[i] = x[2*i + 1];
        }

        // Recursively compute the FFT on even and odd indices
        Complex[] evenResult = fft(even);
        Complex[] oddResult = fft(odd);

        // Combine the results by multiplying with twiddle factors and adding
        Complex[] result = new Complex[n];
        for (int k = 0; k < n/2; k++) {
            double theta = -2 * Math.PI * k / n;
            Complex twiddle = new Complex(Math.cos(theta), Math.sin(theta));
            result[k] = evenResult[k].plus(twiddle.times(oddResult[k]));
            result[k + n/2] = evenResult[k].minus(twiddle.times(oddResult[k]));
        }

        return result;
    }
}
