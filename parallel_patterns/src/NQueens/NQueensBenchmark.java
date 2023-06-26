package NQueens;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;

public class NQueensBenchmark {
    private static long sequentialTime = 1;

    public static void nQueensBenchmark () {
        int nQueen = 14;
        int granularity = 13;

        sequentialNQueensTest(nQueen);
        System.out.println();

        forkJoinNQueensTest(nQueen, granularity);
        System.out.println();

//        cachedThreadPoolTest(nQueen, granularity);
//        System.out.println();

        threadingNQueensTest(nQueen, granularity);
        System.out.println();
    }

    public static void sequentialNQueensTest(int nQueens){
        long stime = System.nanoTime();
        List<int[][]> solutions = SequentialNQueens.solveNQueens(nQueens);
        long etime = System.nanoTime();
        System.out.println("Number of solutions produced by sequential is: "+solutions.size());
        System.out.println("Time cost by sequential solution is: "+(etime-stime)+" nm");
        sequentialTime = etime-stime;
    }

    public static long forkJoinNQueensTest (int nQueen, int granularity) {
        ForkJoinPool pool = new ForkJoinPool(16);
        int[][] board = new int[nQueen][nQueen];
        ForkJoinNQueens nQueens = new ForkJoinNQueens(nQueen, 0, granularity, board);
        long stime = System.nanoTime();
        List<int[][]> solutions = pool.invoke(nQueens);
        long etime = System.nanoTime();
        System.out.println("Number of solutions produced by sequential is: "+solutions.size());
        System.out.println("Time cost by ForkJoin solution is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        return etime-stime;
    }

    public static long cachedThreadPoolTest (int nQueen, int granularity) {
        int[][] board = new int[nQueen][nQueen];
        List<int[][]> solutions = new ArrayList<>();
        long stime = System.nanoTime();
        try{
            solutions = ExecutorServiceNQueens.nQueens(board, nQueen, 0, granularity);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long etime;
        etime = System.nanoTime();
        System.out.println("Number of solutions produced by cached thread pool is: "+solutions.size());
        System.out.println("Time cost by cached thread pool solution is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));


//        System.out.println("Number of solutions produced by cached thread pool is: "+solutions.size());
//        System.out.println("Time cost by cached thread pool solution is: "+(etime-stime)+" nm");
//        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        return etime-stime;
    }

    public static long threadingNQueensTest (int nQueen, int granularity) {
        int[][] board = new int[nQueen][nQueen];
        long stime = System.nanoTime();
        ThreadingNQueens nQueens = new ThreadingNQueens(nQueen, 0, granularity, board);
        nQueens.start();
        try{
            nQueens.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<int[][]> solutions = nQueens.getSolutions();
        long etime = System.nanoTime();
        System.out.println("Number of solutions produced by threading is: "+solutions.size());
        System.out.println("Time cost by threading solution is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        return etime-stime;
    }
}
