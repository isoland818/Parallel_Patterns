package AdaptiveQuadrature;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceAQ {
    public static double quad (double left, double right, double fleft, double fright, double lrarea, double granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        double mid = (left + right) / 2;
        double fmid = SequentialAQ.func(mid);
        double larea = (fleft + fmid) * (mid - left) / 2;
        double rarea = (fmid + fright) * (right - mid) / 2;
        if (Math.abs((larea + rarea) - lrarea) > granularity) {
            Future<Double> leftSub = executorService.submit(() -> quad(left, mid, fleft, fmid, larea, granularity, executorService));
            Future<Double> rightSub = executorService.submit(() -> quad(mid, right, fmid, fright, rarea, granularity, executorService));
            return leftSub.get()+rightSub.get();
        } else {
            return SequentialAQ.quad(left, right, fleft, fright, lrarea);
        }
    }

    public static double quadNewPool (double left, double right, double fleft, double fright, double lrarea, double granularity, ExecutorService executorService) throws ExecutionException, InterruptedException {
        double mid = (left + right) / 2;
        double fmid = SequentialAQ.func(mid);
        double larea = (fleft + fmid) * (mid - left) / 2;
        double rarea = (fmid + fright) * (right - mid) / 2;
        if (Math.abs((larea + rarea) - lrarea) > granularity) {
            Future<Double> leftSub = executorService.submit(() -> quad(left, mid, fleft, fmid, larea, granularity, Executors.newFixedThreadPool(2)));
            Future<Double> rightSub = executorService.submit(() -> quad(mid, right, fmid, fright, rarea, granularity, Executors.newFixedThreadPool(2)));
            executorService.shutdown();
            return leftSub.get()+rightSub.get();
        } else {
            return SequentialAQ.quad(left, right, fleft, fright, lrarea);
        }
    }
}
