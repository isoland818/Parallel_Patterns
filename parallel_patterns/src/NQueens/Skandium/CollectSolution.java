package NQueens.Skandium;

import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.List;

public class CollectSolution implements Merge<List<int[][]>, List<int[][]>> {
    @Override
    public List<int[][]> merge(List<int[][]>[] solutions) throws Exception {
        List<int[][]> result = new ArrayList<>();
        for (List<int[][]> solution: solutions) {
            result.addAll(solution);
        }
        return result;
    }
}
