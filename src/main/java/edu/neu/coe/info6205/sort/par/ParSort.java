package edu.neu.coe.info6205.sort.par;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
class ParSort {

    public static int cutoff = 1000; // DEFAULT

    public synchronized static void sort(int[] array, int from, int to, ForkJoinPool pool) {

        if (to - from < cutoff) {
            Arrays.sort(array, from, to);
        }
        else {
            // FIXME next few lines should be removed from public repo.
            int mid = from + (to - from) / 2;
            CompletableFuture<int[]> parsort1 = parsort(array, from, mid, pool); // TO IMPLEMENT
            CompletableFuture<int[]> parsort2 = parsort(array, mid, to, pool); // TO IMPLEMENT
            CompletableFuture<int[]> parsort = parsort1.thenCombine(parsort2, ParSort::merge);

            parsort.whenComplete((result, throwable) -> System.arraycopy(result, 0, array, from, result.length));
//            System.out.println("# threads: "+ ForkJoinPool.commonPool().getRunningThreadCount());
            parsort.join();
        }
    }

    private synchronized static int[] merge(int[] xs1, int[] xs2) {
        int[] result = new int[xs1.length + xs2.length];
        // TO IMPLEMENT
        int i = 0, j = 0 ;

        // merge array xs1 and xs2
        for (int k = 0; k < result.length; k++) {
            if (i >= xs1.length) {
                result[k] = xs2[j++];
            } else if (j >= xs2.length) {
                result[k] = xs1[i++];
            } else if (xs2[j] < xs1[i]) {
                result[k] = xs2[j++];
            } else {
                result[k] = xs1[i++];
            }
        }
        return result;

    }

    private synchronized static CompletableFuture<int[]> parsort(int[] array, int from, int to, ForkJoinPool pool) {
        return CompletableFuture.supplyAsync(
                () -> {
                    int[] result = new int[to - from];
                    // TO IMPLEMENT
                    System.arraycopy(array, from, result, 0, result.length);
                    sort(result, 0, to - from, pool);
                    return result;
                }
        );
    }
}