package AdaptiveQuadrature.Skandium;

import AdaptiveQuadrature.SequentialAQ;

public class Range {
    double left;
    double right;
    double mid;
    double fleft;
    double fright;
    double fmid;
    double lrarea;

    public Range(double left, double right, double fleft, double fright, double lrarea) {
        this.left=left;
        this.right=right;
        this.mid=(left+right)/2;
        this.fleft=fleft;
        this.fright=fright;
        this.fmid= SequentialAQ.func(mid);
        this.lrarea=lrarea;
    }

}
