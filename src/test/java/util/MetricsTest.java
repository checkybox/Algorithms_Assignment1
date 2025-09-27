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
        metrics.enterRecursion();
        metrics.enterRecursion();
        metrics.enterRecursion();
        metrics.exitRecursion();
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

    @Test
    void testTimerMethods() throws InterruptedException {
        Metrics metrics = new Metrics();
        metrics.startTimer();
        Thread.sleep(50); // sleep for 50ms
        metrics.stopTimer();
        long elapsed = metrics.getTimeMilliseconds();
        assertTrue(elapsed >= 50 && elapsed < 200, "Timer should measure elapsed time in ms");
    }

    @Test
    void testTimerReset() throws InterruptedException {
        Metrics metrics = new Metrics();
        metrics.startTimer();
        Thread.sleep(10);
        metrics.stopTimer();
        metrics.reset();
        assertEquals(0, metrics.getTimeMilliseconds(), "Timer should reset to 0");
    }
}
