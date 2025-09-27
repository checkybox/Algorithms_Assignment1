package org.example.algorithms;

import org.junit.jupiter.api.Test;
import util.Metrics;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    void selectsKthSmallestAcrossAllK() {
        int[] base = {7, 2, 1, 6, 8, 5, 3, 4, 9, 0};
        int[] sorted = Arrays.copyOf(base, base.length);
        Arrays.sort(sorted);
        for (int k = 0; k < base.length; k++) {
            int[] arr = Arrays.copyOf(base, base.length);
            Metrics metrics = new Metrics();
            int val = DeterministicSelect.select(arr, k, metrics);
            assertEquals(sorted[k], val, "k=" + k + " should match sorted[k]");
            assertTrue(metrics.getComparisons() > 0);
            assertTrue(metrics.getTimeNanoseconds() > 0);
        }
    }

    @Test
    void worksOnSingleElement() {
        int[] arr = {42};
        Metrics metrics = new Metrics();
        int v = DeterministicSelect.select(arr, 0, metrics);
        assertEquals(42, v);
        assertTrue(metrics.getTimeNanoseconds() >= 0);
    }

    @Test
    void handlesDuplicates() {
        int[] base = {5, 1, 5, 3, 5, 2, 5};
        int[] sorted = Arrays.copyOf(base, base.length);
        Arrays.sort(sorted);
        for (int k = 0; k < base.length; k++) {
            int[] arr = Arrays.copyOf(base, base.length);
            Metrics metrics = new Metrics();
            int v = DeterministicSelect.select(arr, k, metrics);
            assertEquals(sorted[k], v);
        }
    }

    @Test
    void invalidInputsThrow() {
        Metrics metrics = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(null, 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{}, 0, metrics));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1,2,3}, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> DeterministicSelect.select(new int[]{1,2,3}, 3, metrics));
    }

    @Test
    void metricsResetWorks() {
        int[] arr = {3, 1, 2};
        Metrics metrics = new Metrics();
        DeterministicSelect.select(arr, 1, metrics);
        metrics.reset();
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getMaxDepth());
        assertEquals(0, metrics.getTimeNanoseconds());
    }
}

