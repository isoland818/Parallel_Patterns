package NQueens.Skandium;

import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.List;

public class SplitProblem implements Split<Range, Range> {
    @Override
    public Range[] split(Range range) throws Exception {
        List<Range> subtasks = new ArrayList<>();
        for (int i = 0; i < range.size; i++) {
            if (isSafe(range.array, i, range.col, range.size)) {
                range.array[i][range.col]=1;
                subtasks.add(new Range(range.array, range.size, range.col+1));
                range.array[i][range.col]=0;
            }
        }
        return subtasks.toArray(new Range[0]);
    }

    private static boolean isSafe(int[][] board, int row, int col, int n) {
        int i, j;

        // Check row on the left side
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper diagonal on the left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check lower diagonal on the left side
        for (i = row, j = col; i < n && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

}
