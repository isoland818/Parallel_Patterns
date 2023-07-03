package FourierTransformation;

public class Complex {
    private final double real;
    private final double imag;

    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public Complex plus(Complex other) {
        double newReal = this.real + other.real;
        double newImag = this.imag + other.imag;
        return new Complex(newReal, newImag);
    }

    public Complex minus(Complex other) {
        double newReal = this.real - other.real;
        double newImag = this.imag - other.imag;
        return new Complex(newReal, newImag);
    }

    public Complex times(Complex other) {
        double newReal = this.real * other.real - this.imag * other.imag;
        double newImag = this.real * other.imag + this.imag * other.real;
        return new Complex(newReal, newImag);
    }

    public boolean equals(Complex other) {
        return this.real == other.real && this.imag == other.imag;
    }
}
