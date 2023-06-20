package Fibonacci;

public class SequentialFib {
    public static Long fib (int n) {
        if (n<=1) {
            return (long) n;
        } else {
            return fib(n-1) + fib(n-2);
        }
    }
}
