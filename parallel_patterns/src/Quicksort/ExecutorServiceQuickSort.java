package Quicksort;

import Fibonacci.ExecutorServiceFib;
import Fibonacci.SequentialFib;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceQuickSort {
    public static void quickSort(int[] array, int start, int end, int granularity, ExecutorService executorService) {
        if (start < end) {
            if (end - start + 1 <= granularity) {
                SequentialQuickSort.quickSort(array, start, end);

            } else {
                int pivotIndex = partition(array, start, end);

                executorService.submit(() -> quickSort(array, start, pivotIndex - 1, granularity, executorService));
                executorService.submit(() -> quickSort(array, pivotIndex + 1, end, granularity, executorService));
            }
        }
    }

    public static void quickSortNewPool(int[] array, int start, int end, int granularity, ExecutorService executorService) {
        if (start < end) {
            if (end - start + 1 >= granularity) {
                int pivotIndex = partition(array, start, end);

                executorService.submit(() -> quickSort(array, start, pivotIndex - 1, granularity, Executors.newFixedThreadPool(2)));
                executorService.submit(() -> quickSort(array, pivotIndex + 1, end, granularity, Executors.newFixedThreadPool(2)));
            } else {
                SequentialQuickSort.quickSort(array, start, end);
            }
            executorService.shutdown();
        }
    }

    private static int partition(int[] array, int start, int end) {
        int pivot = array[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }

    // Function to swap two elements in the array
    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void baseSort(int[] array, int start, int end){

    }
}
