package org.example.algorithms;

import org.example.algorithms.DeterministicSelect;
import org.example.algorithms.MergeSort;
import util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SelectVsSortCorrectnessTest {
    @Test
    void testSelectMatchesSortKth() {
        Random rnd = new Random(12345);
        int n = 1000;
        for (int trial = 0; trial < 100; trial++) {
            int[] arr = rnd.ints(n, 0, 1_000_000).toArray();
            int k = rnd.nextInt(n); // 0-based
            int[] arrCopy = Arrays.copyOf(arr, arr.length);
            Metrics metrics = new Metrics();
            int selectResult = DeterministicSelect.select(arrCopy, k, metrics);

            int[] sorted = Arrays.copyOf(arr, arr.length);
            Arrays.sort(sorted);
            int expected = sorted[k]; // 0-based
            assertEquals(expected, selectResult, "Select result should match sorted array's k-th element");
        }
    }

    @Test
    void testMergeSortCorrectnessRandomAndAdversarial() {
        Random rnd = new Random(42);
        int n = 1000;
        // Random array
        int[] arr = rnd.ints(n, 0, 1_000_000).toArray();
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Metrics metrics = new Metrics();
        MergeSort.sort(arrCopy, metrics);
        int[] expected = Arrays.copyOf(arr, arr.length);
        Arrays.sort(expected);
        assertArrayEquals(expected, arrCopy, "MergeSort should sort random array correctly");

        // Adversarial: reverse sorted
        int[] adversarial = new int[n];
        for (int i = 0; i < n; i++) adversarial[i] = n - i;
        int[] adversarialCopy = Arrays.copyOf(adversarial, adversarial.length);
        Metrics metrics2 = new Metrics();
        MergeSort.sort(adversarialCopy, metrics2);
        Arrays.sort(adversarial);
        assertArrayEquals(adversarial, adversarialCopy, "MergeSort should sort adversarial array correctly");
    }

    @Test
    void testQuickSortRecursionDepthBounded() {
        Random rnd = new Random(42);
        int n = 1000;
        int[] arr = rnd.ints(n, 0, 1_000_000).toArray();
        int[] arrCopy = Arrays.copyOf(arr, arr.length);
        Metrics metrics = new Metrics();
        org.example.algorithms.QuickSort.sort(arrCopy, metrics);
        int maxDepth = metrics.getMaxDepth();
        int expectedBound = 2 * (int)Math.floor(Math.log(n) / Math.log(2)) + 5;
        assertTrue(maxDepth <= expectedBound, "QuickSort recursion depth should be bounded");
    }
}
