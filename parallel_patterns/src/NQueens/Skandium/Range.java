package NQueens.Skandium;

public class Range {
    int[][] array;
    int size;
    int col;

    public Range (int[][] array, int size, int col) {
        this.array=copyBoard(array);
        this.size=size;
        this.col=col;
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
