package NQueens;

import java.util.ArrayList;
import java.util.List;

public class ThreadingNQueens extends Thread{
    private int n;
    private int col;
    private int granularity;
    private int[][] board;

    private List<int[][]> solutions;

    public ThreadingNQueens(int n, int col, int granularity, int[][] board) {
        this.n=n;
        this.col=col;
        this.granularity=granularity;
        this.board=copyBoard(board);
        this.solutions=new ArrayList<>();
    }

    @Override
    public void run(){
        if(n-col<=granularity){
            SequentialNQueens.solveNQueens(board, col, n, solutions);
            return;
        }
        List<ThreadingNQueens> subtasks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(isSafe(board, i, col, n)){
                board[i][col]=1;
                ThreadingNQueens subtask = new ThreadingNQueens(n, col+1, granularity, board);
                subtask.start();
                subtasks.add(subtask);
                board[i][col]=0;
            }
        }

        try{
            for (ThreadingNQueens subtask: subtasks) {
                subtask.join();
                solutions.addAll(subtask.getSolutions());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<int[][]> getSolutions(){
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
