package org.example.algorithms;

import org.junit.jupiter.api.Test;
import util.Metrics;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void testSortCorrectness() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void testSortEmptyArray() {
        int[] arr = {};
        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSortSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testMetricsTracking() {
        int[] arr = {3, 2, 1};
        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getSwaps() > 0);
        assertTrue(metrics.getMaxDepth() > 0);
        assertTrue(metrics.getTimeNanoseconds() >= 0);
    }
}
