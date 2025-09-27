package org.example.algorithms;

import util.Metrics;
import java.util.*;

public class ClosestPairOfPoints {
    public static class Point {
        public final double x, y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double minDistance(Point[] points, Metrics metrics) {
        if (points == null || points.length < 2) throw new IllegalArgumentException("need at least 2 points");
        metrics.reset();
        metrics.startTimer();
        Point[] pts = Arrays.copyOf(points, points.length);
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        double result = minDistUtil(pts, 0, pts.length, metrics);
        metrics.stopTimer();
        return result;
    }

    private static double minDistUtil(Point[] points, int left, int right, Metrics metrics) {
        metrics.enterRecursion();
        try {
            int n = right - left;
            if (n <= 3) {
                double minDist = Double.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    for (int j = i + 1; j < right; j++) {
                        metrics.incrementComparisons();
                        minDist = Math.min(minDist, distance(points[i], points[j]));
                    }
                }
                return minDist;
            }
            int mid = (left + right) / 2;
            double midX = points[mid].x;
            double dl = minDistUtil(points, left, mid, metrics);
            double dr = minDistUtil(points, mid, right, metrics);
            double d = Math.min(dl, dr);
            List<Point> strip = new ArrayList<>();
            for (int i = left; i < right; i++) {
                if (Math.abs(points[i].x - midX) < d) {
                    strip.add(points[i]);
                }
            }
            double stripDist = stripClosest(strip, d, metrics);
            return Math.min(d, stripDist);
        } finally {
            metrics.exitRecursion();
        }
    }

    private static double stripClosest(List<Point> strip, double d, Metrics metrics) {
        double minDist = d;
        strip.sort(Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && j <= i + 7 && (strip.get(j).y - strip.get(i).y) < minDist; j++) {
                metrics.incrementComparisons();
                minDist = Math.min(minDist, distance(strip.get(i), strip.get(j)));
            }
        }
        return minDist;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
