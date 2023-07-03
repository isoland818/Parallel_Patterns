import AdaptiveQuadrature.AQBenchmark;
import Fibonacci.ExecutorServiceFib;
import Fibonacci.FibBenchmark;
import Fibonacci.ForkJoinFib;
import Fibonacci.SequentialFib;
import FourierTransformation.FFTBenchmark;
import Knapsack.KnapsackBenchmark;
import NQueens.ForkJoinNQueens;
import NQueens.NQueensBenchmark;
import NQueens.SequentialNQueens;
import Quicksort.ExecutorServiceQuickSort;
import Quicksort.ForkJoinQuickSort;
import Quicksort.QuickSortBenchMark;
import Quicksort.SequentialQuickSort;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        QuickSortBenchMark.sortBenchmark();
//        AQBenchmark.AQBenchmark();
        FFTBenchmark.fftBenchmark();
    }

    private static void executorService(int[] array){
        ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorServiceQuickSort.quickSort(array, 0, 63, 8, pool);

        try{
            pool.awaitTermination(1000000, TimeUnit.MICROSECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            StringBuilder arrayStr = new StringBuilder();
            for (int j = 0; j < 64; j++) {
                arrayStr.append(array[j]).append(", ");
            }
            System.out.println(arrayStr);
            pool.shutdown();
        }
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