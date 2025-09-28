package org.example.algorithms;

import org.junit.jupiter.api.Test;
import util.Metrics;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class EdgeCasesTest {

    @Test
    void mergeSort_handlesEmptyAndSingle() {
        Metrics m1 = new Metrics();
        int[] empty = new int[0];
        MergeSort.sort(empty, m1);
        assertArrayEquals(new int[0], empty);

        Metrics m2 = new Metrics();
        int[] one = new int[]{42};
        MergeSort.sort(one, m2);
        assertArrayEquals(new int[]{42}, one);
    }

    @Test
    void mergeSort_sortsWithDuplicates() {
        int[] a = {5, 1, 2, 2, 3, 1, 4, 4, 0, 0};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        assertArrayEquals(expected, a);
    }

    @Test
    void quickSort_handlesEmptyAndSingle() {
        Metrics m1 = new Metrics();
        int[] empty = new int[0];
        QuickSort.sort(empty, m1);
        assertArrayEquals(new int[0], empty);

        Metrics m2 = new Metrics();
        int[] one = new int[]{7};
        QuickSort.sort(one, m2);
        assertArrayEquals(new int[]{7}, one);
    }

    @Test
    void quickSort_sortsWithDuplicates() {
        int[] a = {3, 3, 2, 1, 2, 1, 1, 4, 4, 0};
        int[] expected = Arrays.copyOf(a, a.length);
        Arrays.sort(expected);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertArrayEquals(expected, a);
    }

    @Test
    void deterministicSelect_throwsOnEmptyOrBadK() {
        Metrics m = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(null, 0, m));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[0], 0, m));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1}, -1, m));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1}, 1, m));
    }

    @Test
    void deterministicSelect_worksWithDuplicates() {
        Random rnd = new Random(123);
        for (int n : new int[]{1, 2, 5, 10, 50, 100}) {
            int[] arr = rnd.ints(n, 0, 5).toArray(); // many duplicates
            for (int k = 0; k < n; k++) {
                int[] a1 = Arrays.copyOf(arr, arr.length);
                int[] a2 = Arrays.copyOf(arr, arr.length);
                Metrics m = new Metrics();
                int got = DeterministicSelect.select(a1, k, m);
                Arrays.sort(a2);
                int exp = a2[k];
                assertEquals(exp, got, "k="+k+", n="+n);
            }
        }
    }

    @Test
    void closestPair_twoPointsAndDuplicates() {
        Metrics m1 = new Metrics();
        ClosestPairOfPoints.Point p1 = new ClosestPairOfPoints.Point(0, 0);
        ClosestPairOfPoints.Point p2 = new ClosestPairOfPoints.Point(3, 4);
        double d = ClosestPairOfPoints.minDistance(new ClosestPairOfPoints.Point[]{p1, p2}, m1);
        assertEquals(5.0, d, 1e-9);

        Metrics m2 = new Metrics();
        ClosestPairOfPoints.Point p3 = new ClosestPairOfPoints.Point(1, 1);
        ClosestPairOfPoints.Point p4 = new ClosestPairOfPoints.Point(1, 1);
        double d2 = ClosestPairOfPoints.minDistance(new ClosestPairOfPoints.Point[]{p3, p4}, m2);
        assertEquals(0.0, d2, 1e-9);
    }
}

