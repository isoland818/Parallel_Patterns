import Fibonacci.ExecutorServiceFib;
import Fibonacci.FibBenchmark;
import Fibonacci.ForkJoinFib;
import Fibonacci.SequentialFib;
import NQueens.ForkJoinNQueens;
import NQueens.SequentialNQueens;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        FibBenchmark.fibBenchmark();
    }

    public static void printBoard(int[][] board, int n){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j]+"  ");
            }
            System.out.println();
        }
    }
}