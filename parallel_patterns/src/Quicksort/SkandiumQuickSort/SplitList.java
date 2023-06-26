package Quicksort.SkandiumQuickSort;

import cl.niclabs.skandium.muscles.Split;

public class SplitList implements Split<Range, Range> {
    @Override
    public Range[] split(Range range) throws Exception {
        int pivotIndex = partition(range.array, range.start, range.end);

        Range[] subRanges = {new Range(range.array, range.start, pivotIndex-1), new Range(range.array, pivotIndex+1, range.end)};

        return subRanges;
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
}
