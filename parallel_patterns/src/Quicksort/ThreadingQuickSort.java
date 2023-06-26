package Quicksort;

public class ThreadingQuickSort extends Thread{
    private final int[] array;
    private final int start;
    private final int end;
    private final int granularity;

    public ThreadingQuickSort(int[] array, int start, int end, int granularity) {
        this.array=array;
        this.start=start;
        this.end=end;
        this.granularity=granularity;
    }

    @Override
    public void run(){
        if(end-start+1<=granularity) {
            SequentialQuickSort.quickSort(array, start, end);
        }else{
            int pivot = partition(array, start, end);
            ThreadingQuickSort leftSort = new ThreadingQuickSort(array, start, pivot-1, granularity);
            ThreadingQuickSort rightSort = new ThreadingQuickSort(array, pivot+1, end, granularity);

            leftSort.start();
            rightSort.start();

            try{
                leftSort.join();
                rightSort.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
}
