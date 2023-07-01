package NQueens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinNQueens extends RecursiveTask<List<int[][]>> {

    private final int n;

    private final int col;
    private int[][] board;
    private final int granularity;

    public ForkJoinNQueens(int n, int col, int granularity, int[][] board){
        this.n=n;
        this.col=col;
        this.granularity=granularity;
        this.board=copyBoard(board);
    }
    @Override
    protected List<int[][]> compute() {
        List<int[][]> solutions = new ArrayList<>();
        if (n-col<=granularity) {
            SequentialNQueens.solveNQueens(board, col, n, solutions);
            return solutions;
        }
        List<ForkJoinNQueens> subTasks = new ArrayList<>();
        for (int i=0; i<n; i++){
            if (isSafe(board, i, col, n)){
                board[i][col]=1;
                subTasks.add(new ForkJoinNQueens(n, col+1, granularity, board));
                board[i][col]=0;
            }
        }
        invokeAll(subTasks);
        for (ForkJoinNQueens subTask: subTasks) {
            solutions.addAll(subTask.join());
        }
        return solutions;
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

    private static int[][] copyBoard(int[][] board) {
        int n = board.length;
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, n);
        }
        return copy;
    }
}
