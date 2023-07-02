package NQueens;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.List;

public class ExecutorServiceNQueens {

    public static List<int[][]> nQueens (int[][] board, int n, int col, int granularity) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(n);
        List<int[][]> solutions = new ArrayList<>();
        if (n-col<=granularity) {
            SequentialNQueens.solveNQueens(board, col, n, solutions);
            return solutions;
        }
        List<Future<List<int[][]>>> futures = new ArrayList<>();
        for (int i=0; i<n; i++){
            if (isSafe(board, i, col, n)){
                board[i][col]=1;
                int[][] copy = copyBoard(board);
                futures.add(executorService.submit(() -> nQueens(copy, n, col+1, granularity)));
                board[i][col]=0;
            }
        }
        executorService.shutdown();
        for(Future<List<int[][]>> future: futures) {
            solutions.addAll(future.get());
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


    public synchronized static void printBoard(int[][] board, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j]+"  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
