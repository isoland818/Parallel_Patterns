package Fibonacci;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class ParallelStreamFib {
    public static long fib (int n) {
        AtomicReference<Long> result = new AtomicReference<>(0L);

        fibGenerator(n).parallel().forEach(fib -> result.updateAndGet(current -> current + fib));

        return result.get();
    }

    private static Stream<Long> fibGenerator(int n) {
        return Stream.concat(
                Stream.of(0L, 1L),
                Stream.iterate(
                        new long[]{0L, 1L},
                        f -> new long[]{f[1], f[0]+f[1]}).map(f -> f[1])).limit(n+1);
    }
}
