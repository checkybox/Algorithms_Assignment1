package org.example.algorithms;

import org.example.algorithms.ClosestPairOfPoints.Point;
import org.junit.jupiter.api.Test;
import util.Metrics;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairOfPointsTest {
    @Test
    void testSimpleCase() {
        Point[] points = {
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 2)
        };
        Metrics metrics = new Metrics();
        double d = ClosestPairOfPoints.minDistance(points, metrics);
        assertEquals(Math.sqrt(2), d, 1e-9);
        assertTrue(metrics.getComparisons() > 0);
        assertTrue(metrics.getTimeNanoseconds() > 0);
    }

    @Test
    void testIdenticalPoints() {
        Point[] points = {
            new Point(1, 1),
            new Point(1, 1),
            new Point(2, 2)
        };
        Metrics metrics = new Metrics();
        double d = ClosestPairOfPoints.minDistance(points, metrics);
        assertEquals(0.0, d, 1e-9);
    }

    @Test
    void testNegativeCoordinates() {
        Point[] points = {
            new Point(-1, -2),
            new Point(0, 0),
            new Point(1, 2),
            new Point(2, 3)
        };
        Metrics metrics = new Metrics();
        double d = ClosestPairOfPoints.minDistance(points, metrics);
        assertEquals(Math.sqrt(2), d, 1e-9);
    }

    @Test
    void testThrowsOnTooFewPoints() {
        Point[] points = {
            new Point(1, 1)
        };
        Metrics metrics = new Metrics();
        assertThrows(IllegalArgumentException.class, () -> ClosestPairOfPoints.minDistance(points, metrics));
    }

    @Test
    void testMetricsResetWorks() {
        Point[] points = {
            new Point(0, 0),
            new Point(1, 1),
            new Point(2, 2)
        };
        Metrics metrics = new Metrics();
        ClosestPairOfPoints.minDistance(points, metrics);
        metrics.reset();
        assertEquals(0, metrics.getComparisons());
        assertEquals(0, metrics.getSwaps());
        assertEquals(0, metrics.getMaxDepth());
        assertEquals(0, metrics.getTimeNanoseconds());
    }
}
