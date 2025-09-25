package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {

    @Test
    void testComparisonsCounter() {
        Metrics metrics = new Metrics();
        metrics.incrementComparisons();
        metrics.incrementComparisons();
        assertEquals(2, metrics.getComparisons());
    }

    @Test
    void testSwapsCounter() {
        Metrics metrics = new Metrics();
        metrics.incrementSwaps();
        assertEquals(1, metrics.getSwaps());
    }

    @Test
    void testDepthTracking() {
        Metrics metrics = new Metrics();
        metrics.enterRecursion(); // depth = 1, max = 1
        metrics.enterRecursion(); // depth = 2, max = 2
        metrics.enterRecursion(); // depth = 3, max = 3
        metrics.exitRecursion();  // depth = 2
        assertEquals(2, metrics.getCurrentDepth());
        assertEquals(3, metrics.getMaxDepth());
    }

    @Test
    void testReset() {
        Metrics metrics = new Metrics();
        metrics.incrementComparisons();
        metrics.incrementSwaps();
        metrics.enterRecursion();
        metrics.enterRecursion();

        metrics.reset();

        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getCurrentDepth());
        assertEquals(0, metrics.getMaxDepth());
    }
}
