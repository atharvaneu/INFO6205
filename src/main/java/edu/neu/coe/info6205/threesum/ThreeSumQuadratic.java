package edu.neu.coe.info6205.threesum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of ThreeSum which follows the approach of dividing the solution-space into
 * N sub-spaces where each sub-space corresponds to a fixed value for the middle index of the three values.
 * Each sub-space is then solved by expanding the scope of the other two indices outwards from the starting point.
 * Since each sub-space can be solved in O(N) time, the overall complexity is O(N^2).
 * <p>
 * NOTE: The array provided in the constructor MUST be ordered.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    public Triple[] getTriples() {
        List<Triple> triples = new ArrayList<>();
        for (int i = 0; i < length; i++) triples.addAll(getTriples(i));
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Get a list of Triples such that the middle index is the given value j.
     *
     * @param j the index of the middle value.
     * @return a Triple such that
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        int target = -a[j]; // We want a[i] + a[high] = -a[j]

        // since data is sorted, we can use a two pointers on opposite ends, and move one or the other depending on their sum
        int low = 0;
        int high = length - 1;

        while (low < j && high > j) {
            int sum = a[low] + a[high];

            if (sum == target) {
                triples.add(new Triple(a[low], a[j], a[high])); // Found a valid triple
                low++;
                high--;  // move i and high inward - closer to j
            } else if (sum < target) {
                low++;  // if sum is less than target, only move i toward j - implying we are increasing sum
            } else {
                high--;  // if sum is greater than target, only move high toward j - implying we are decreasing sum
            }
        }

        return triples;
    }

    private final int[] a;
    private final int length;
}