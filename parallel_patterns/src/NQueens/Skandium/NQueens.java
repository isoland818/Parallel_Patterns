package NQueens.Skandium;

import Quicksort.SkandiumQuickSort.SplitList;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.DaC;
import cl.niclabs.skandium.skeletons.Skeleton;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NQueens {
    public static List<int[][]> nQueens(int nQueen, int granularity, int pthreads) throws ExecutionException, InterruptedException {
        int[][] board = new int[nQueen][nQueen];

        Skeleton<Range, List<int[][]>> nQueens = new DaC<Range, List<int[][]>>(new ShouldSplit(granularity), new SplitProblem(), new Solve(), new CollectSolution());

        Skandium skandium = new Skandium(pthreads);

        Stream<Range, List<int[][]>> stream = skandium.newStream(nQueens);

        Future<List<int[][]>> future = stream.input(new Range(board, nQueen, 0));

        List<int[][]> solutions = future.get();

        skandium.shutdown();

        return solutions;
    }
}
