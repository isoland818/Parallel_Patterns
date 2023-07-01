package NQueens.Skandium;

import NQueens.SequentialNQueens;
import cl.niclabs.skandium.muscles.Execute;

import java.util.ArrayList;
import java.util.List;

public class Solve implements Execute<Range, List<int[][]>> {
    @Override
    public List<int[][]> execute(Range range) throws Exception {
        List<int[][]> solutions = new ArrayList<>();
        SequentialNQueens.solveNQueens(range.array, range.col, range.size, solutions);
        return solutions;
    }
}
