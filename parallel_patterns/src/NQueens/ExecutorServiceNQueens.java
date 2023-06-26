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
            System.out.println(solutions.size());
            return solutions;
        }
        List<Future<List<int[][]>>> futures = new ArrayList<>();
        for (int i=0; i<n; i++){
            if (isSafe(board, i, col, n)){
                board[i][col]=1;
                futures.add(executorService.submit(() -> nQueens(copyBoard(board), n, col+1, granularity)));
                board[i][col]=0;
            }
        }
        System.out.println("col: "+col+", futures: "+futures.size());
        executorService.shutdown();
        for(Future<List<int[][]>> future: futures) {
            solutions.addAll(future.get());
        }
        try{
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }catch(InterruptedException e){
            e.printStackTrace();
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
