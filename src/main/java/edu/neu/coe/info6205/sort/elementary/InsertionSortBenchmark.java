package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class InsertionSortBenchmark {
    /**
     * Generates an array of random integers.
     *
     * This method creates an array of the specified size and populates it
     * with random integers using Java's {@code Random} class.
     *
     * @param size the size of the array to generate.
     * @return an array of random integers of the specified size.
     */
    private static Integer[] generateRandomArray(int size) {

        Random random = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    /**
     * Generates an ordered array of integers.
     *
     * This method creates an array of the specified size and populates it
     * with integers in ascending order starting from 0.
     *
     * @param size the size of the array to generate.
     * @return an ordered array of increasing integers.
     */
    private static Integer[] generateOrderedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    /**
     * Generates a partially ordered array of integers.
     *
     * This method creates an array that is partially ordered. The first half
     * of the array is in ascending order, and the second half is populated with
     * random integers.
     *
     * @param size the size of the array to generate.
     * @return a partially ordered array of integers with the first half in order
     *         and the second half randomized.
     */
    private static Integer[] generatePartiallyOrderedArray(int size) {
        Integer[] array = generateOrderedArray(size);
        Random random = new Random();

        for (int i = size / 2; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    /**
     * Generates an array of integers in reverse order.
     *
     * This method creates an array of the specified size and populates it
     * with integers in decreasing order, starting from {@code size} and
     * decrementing to 1.
     *
     * @param size the size of the array to generate.
     * @return an array of integers in reverse order.
     *
     */
    private static Integer[] generateReverseOrderedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }


    /**
     * Benchmarks the performance of insertion sort on a given array.
     *
     * This method uses the {@link InsertionSortBasic} implementation and measures the time
     * taken to sort a copy of the provided array multiple times. It utilizes the {@link Benchmark_Timer}
     * class to run and time the sorting operation.
     *
     * @param description a description of the benchmark (e.g., "Random", "Ordered", etc.).
     * @param a the array to be sorted. A copy of this array will be used during each run of the benchmark.
     */
    private static void benchmark(String description, Integer[] a) {

        InsertionSortBasic<Integer> insertionSort = InsertionSortBasic.create();

        // we use a supplier to lazily generate an array, since our timer code uses a supplier to extract value from
        Supplier<Integer[]> supplier = () -> Arrays.copyOf(a, a.length);

        Benchmark<Integer[]> timer = new Benchmark_Timer<>(
                "\nInsertion Sort " + description, // Description of the benchmark
                null,
                insertionSort::sort, // The function to benchmark (sorting)
                null
        );

        // perform the benchmark using repeat method from Benchmark_Timer for m = 10 times
        double time = timer.runFromSupplier(supplier, 10);

        // print the mean lap times
        System.out.printf("Average time for %s: %.2f ms\n\n", description, time);

    }

    private static void benchmark() {

        int[] sizes = {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};

        for (int n : sizes) {
            Integer[] randomArray = generateRandomArray(n);
            Integer[] orderedArray = generateOrderedArray(n);
            Integer[] partiallyOrderedArray = generatePartiallyOrderedArray(n);
            Integer[] reverseOrderedArray = generateReverseOrderedArray(n);

            System.out.println("==========================" + n + " elements" + "==========================");

            benchmark("Random\t\t\t", randomArray);
            benchmark("Ordered\t\t\t", orderedArray);
            benchmark("Partially Ordered\t\t\t", partiallyOrderedArray);
            benchmark("Reverse Ordered\t\t\t", reverseOrderedArray);

        }
    }

    public static void main(String[] args) {
        InsertionSortBenchmark.benchmark();
    }
}
