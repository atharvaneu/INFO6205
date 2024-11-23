package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithComparableHelper;
import edu.neu.coe.info6205.util.Config;

import static edu.neu.coe.info6205.util.Config.*;
import static edu.neu.coe.info6205.util.Config.CUTOFF_DEFAULT;

public class HeapSort<X extends Comparable<X>> extends SortWithComparableHelper<X> {

    public HeapSort(Helper<X> helper) {
        super(helper);
    }

    /**
     * Constructor for HeapSort
     *
     * @param N      the number elements we expect to sort.
     * @param nRuns  the expected number of runs.
     * @param config the configuration.
     */
    public HeapSort(int N, int nRuns, Config config) {
        super(DESCRIPTION + getConfigString(config), N, nRuns, config);
    }

    private static String getConfigString(Config config) {
        StringBuilder stringBuilder = new StringBuilder();

//        stringBuilder.append("currently sorting using HeapSort");

        return stringBuilder.toString();
    }

    public void sort(X[] array, int from, int to) {
        if (array == null || array.length <= 1) return;

        // XXX construction phase
        buildMaxHeap(array);

        // XXX sort-down phase
        Helper<X> helper = getHelper();
        // TODO we over-count hits in the swap operation -- fix it.
        for (int i = array.length - 1; i >= 1; i--) {
            helper.swap(array, 0, i);
            maxHeap(array, i, 0);
        }
    }

    private void buildMaxHeap(X[] array) {
        int half = array.length / 2;
        for (int i = half; i >= 0; i--) maxHeap(array, array.length, i);
    }

    private void maxHeap(X[] array, int heapSize, int index) {
        // TODO we over-count hits in the swap operation -- fix it.
        Helper<X> helper = getHelper();
        final int left = index * 2 + 1;
        final int right = index * 2 + 2;
        int largest = index;
        if (left < heapSize && helper.compare(array, largest, left) < 0) largest = left;
        if (right < heapSize && helper.compare(array, largest, right) < 0) largest = right;
        if (index != largest) {
            helper.swap(array, index, largest);
            maxHeap(array, heapSize, largest);
        }
    }

    private static final String DESCRIPTION = "HeapSort";
}