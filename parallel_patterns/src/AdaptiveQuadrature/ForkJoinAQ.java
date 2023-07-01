package AdaptiveQuadrature;

import cl.niclabs.skandium.skeletons.For;

import java.util.concurrent.RecursiveTask;

public class ForkJoinAQ extends RecursiveTask<Double> {
    private double left;
    private double right;
    private double fleft;
    private double fright;
    private double lrarea;
    private double granularity;

    public ForkJoinAQ (double left, double right, double fleft, double fright, double lrarea, double granularity) {
        this.left=left;
        this.right=right;
        this.fleft=fleft;
        this.fright=fright;
        this.lrarea=lrarea;
        this.granularity=granularity;
    }

    @Override
    protected Double compute() {
        double mid = (left + right) / 2;
        double fmid = SequentialAQ.func(mid);
        double larea = (fleft + fmid) * (mid - left) / 2;
        double rarea = (fmid + fright) * (right - mid) / 2;
        if (Math.abs((larea + rarea) - lrarea) > granularity) {
            ForkJoinAQ leftQuad = new ForkJoinAQ(left, mid, fleft, fmid, larea, granularity);
            ForkJoinAQ rightQuad = new ForkJoinAQ(mid, right, fmid, fright, rarea, granularity);
            invokeAll(leftQuad, rightQuad);
            return leftQuad.join() + rightQuad.join();
        } else {
            return SequentialAQ.quad(left,right,fleft,fright,lrarea); 
        }
    }

}

