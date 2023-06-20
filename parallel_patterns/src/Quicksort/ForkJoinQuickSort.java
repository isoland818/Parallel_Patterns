package Quicksort;

import java.util.concurrent.RecursiveAction;

public class ForkJoinQuickSort extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;
    private final int granularity;

    public ForkJoinQuickSort(int[] array, int start, int end, int granularity){
        this.array=array;
        this.start=start;
        this.end=end;
        this.granularity=granularity;
    }
    @Override
    protected void compute() {
        if(array.length>granularity){
            int pivotIndex = partition(array, start, end);

            ForkJoinQuickSort left = new ForkJoinQuickSort(array, start, pivotIndex-1, granularity);
            ForkJoinQuickSort right = new ForkJoinQuickSort(array, pivotIndex+1, end, granularity);

            invokeAll(left, right);
        }else{
            SequentialQuickSort.quickSort(array, start, end);
        }
    }

    private int partition(int[] array, int start, int end) {
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

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void baseSort(int[] array, int start, int end){

    }
}
