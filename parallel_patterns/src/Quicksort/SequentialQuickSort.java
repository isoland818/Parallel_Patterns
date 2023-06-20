package Quicksort;

public class SequentialQuickSort {

    // Function to perform Quick Sort
    public static void quickSort(int[] array, int start, int end) {
        if (array == null || array.length == 0) {
            return;
        }
        int len = array.length;
        quickSortHelper(array, start, end);
    }

    // Helper function for Quick Sort
    private static void quickSortHelper(int[] array, int start, int end) {
        if (start < end) {
            int pivotIndex = partition(array, start, end);

            quickSortHelper(array, start, pivotIndex - 1);
            quickSortHelper(array, pivotIndex + 1, start);
        }
    }

    // Function to partition the array
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

