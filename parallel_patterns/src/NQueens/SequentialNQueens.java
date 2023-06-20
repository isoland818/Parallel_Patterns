package NQueens;

import java.util.ArrayList;
import java.util.List;

public class SequentialNQueens {
    public static List<int[][]> solveNQueens(int n) {
        List<int[][]> solutions = new ArrayList<>();
        int[][] board = new int[n][n];
        solveNQueensHelper(board, 0, n, solutions);
        return solutions;
    }

    private static void solveNQueensHelper(int[][] board, int col, int n, List<int[][]> solutions) {
        if (col >= n) {
            solutions.add(copyBoard(board));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (isSafe(board, i, col, n)) {
                board[i][col] = 1;
                solveNQueensHelper(board, col + 1, n, solutions);
                board[i][col] = 0;
            }
        }
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
