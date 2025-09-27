package org.example.algorithms;

import org.junit.jupiter.api.Test;
import util.Metrics;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void testSortCorrectness() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void testSortEmptyArray() {
        int[] arr = {};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void testSortSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void testSortAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testSortReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void testSortWithDuplicates() {
        int[] arr = {2, 3, 2, 1, 3, 1};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertArrayEquals(new int[]{1, 1, 2, 2, 3, 3}, arr);
    }

    @Test
    void testMetricsTracking() {
        int[] arr = {3, 2, 1};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getSwaps() > 0);
        assertTrue(metrics.getMaxDepth() > 0);
        assertTrue(metrics.getTimeNanoseconds() > 0);
    }

    @Test
    void testMetricsReset() {
        int[] arr = {3, 2, 1};
        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);
        metrics.reset();
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getMaxDepth());
        assertEquals(0, metrics.getTimeNanoseconds());
    }
}

