package NQueens;



import NQueens.Skandium.NQueens;
import cl.niclabs.skandium.Skandium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NQueensBenchmark {
    private static long sequentialTime = 1;

    public static void nQueensBenchmark () {
        int nQueen = 7;
        int granularity = 5;

        sequentialNQueensTest(nQueen);
        System.out.println();

        threadingTest(nQueen, granularity);
        System.out.println();

        forkJoinNQueensTest(nQueen, granularity, 8);
        System.out.println();

        cachedThreadPoolTest(nQueen, granularity);
        System.out.println();

        skandiumNQueensTest(nQueen, granularity, 8);
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

    public static void threadingTest(int nQueen, int granularity) {
        long stime = System.nanoTime();
        ThreadingNQueens nQueens = new ThreadingNQueens(nQueen, 0, granularity, new int[nQueen][nQueen]);
        nQueens.start();
        try {
            nQueens.join();
            List<int[][]> solutions = nQueens.getSolutions();
            long etime = System.nanoTime();
            System.out.println("Number of solutions produced by Threading is: "+solutions.size());
            System.out.println("Time cost by Threading solution is: "+(etime-stime)+" nm");
            System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long forkJoinNQueensTest (int nQueen, int granularity, int pthreads) {
        ForkJoinPool pool = new ForkJoinPool(pthreads);
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

        return etime-stime;
    }

    public static long skandiumNQueensTest (int nQueen, int granularity, int pthreads) {
        long stime = System.nanoTime();
        List<int[][]> solutions = new ArrayList<>();
        try{
            solutions = NQueens.nQueens(nQueen, granularity, pthreads);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        long etime = System.nanoTime();
        System.out.println("Number of solutions produced by Skandium is: "+solutions.size());
        System.out.println("Time cost by skandium solution is: "+(etime-stime)+" nm");
        System.out.println("Speedup is: "+sequentialTime/(etime-stime));
        return etime-stime;
    }
}
