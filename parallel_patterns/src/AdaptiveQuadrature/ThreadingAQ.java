package AdaptiveQuadrature;

public class ThreadingAQ extends Thread {
    private double left;
    private double right;
    private double fleft;
    private double fright;
    private double lrarea;
    private double granularity;
    private double result;

    public ThreadingAQ (double left, double right, double fleft, double fright, double lrarea, double granularity) {
        this.left=left;
        this.right=right;
        this.fleft=fleft;
        this.fright=fright;
        this.lrarea=lrarea;
        this.granularity=granularity;
    }

    @Override
    public void run() {
        double mid = (left + right) / 2;
        double fmid = SequentialAQ.func(mid);
        double larea = (fleft + fmid) * (mid - left) / 2;
        double rarea = (fmid + fright) * (right - mid) / 2;
        if (Math.abs((larea + rarea) - lrarea) > granularity) {
            ThreadingAQ leftQuad = new ThreadingAQ(left, mid, fleft, fmid, larea, granularity);
            ThreadingAQ rightQuad = new ThreadingAQ(mid, right, fmid, fright, rarea, granularity);

            leftQuad.start();
            rightQuad.start();
            try {
                leftQuad.join();
                rightQuad.join();

                result = leftQuad.getResult() + rightQuad.getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            result = SequentialAQ.quad(left,right,fleft,fright,lrarea);
        }
    }

    public double getResult() {
        return result;
    }
}
