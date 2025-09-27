package org.example;

import org.example.algorithms.*;
import org.example.algorithms.ClosestPairOfPoints.Point;
import util.Metrics;
import java.util.Random;

public class Main {
    public static void initializeCsv(String filename, String algorithmName) {
        try (java.io.FileWriter fw = new java.io.FileWriter(filename)) {
            fw.write(algorithmName + ",array size,time(ns),comparisons,swaps,maxdepth\n");
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100000); // from 0 to 100,000
        }
        return arr;
    }

    public static Point[] generateRandomPoints(int size) {
        Random random = new Random();
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(
                random.nextDouble() * 10000,
                random.nextDouble() * 10000
            );
        }
        return points;
    }

    public static void testMergeSort() {
        int[] sizes = {10, 100, 500, 1000, 5000, 10000, 50000, 100000};
        String csvFile = "mergesort_results.csv";
        initializeCsv(csvFile, "MergeSort");
        System.out.println("MergeSort,array size,time(ns),comparisons,swaps,maxdepth");
        for (int n : sizes) {
            int[] arr = generateRandomArray(n);
            Metrics metrics = new Metrics();
            MergeSort.sort(arr, metrics);
            metrics.writeCsv(csvFile, n, metrics.getTimeNanoseconds());
            System.out.printf("MergeSort,%d,%d,%d,%d,%d\n",
                n,
                metrics.getTimeNanoseconds(),
                metrics.getComparisons(),
                metrics.getSwaps(),
                metrics.getMaxDepth()
            );
        }
        System.out.println("Results written to " + csvFile + "\n");
    }

    public static void testQuickSort() {
        int[] sizes = {10, 100, 500, 1000, 5000, 10000, 50000, 100000};
        String csvFile = "quicksort_results.csv";
        initializeCsv(csvFile, "QuickSort");
        System.out.println("QuickSort,array size,time(ns),comparisons,swaps,maxdepth");
        for (int n : sizes) {
            int[] arr = generateRandomArray(n);
            Metrics metrics = new Metrics();
            QuickSort.sort(arr, metrics);
            metrics.writeCsv(csvFile, n, metrics.getTimeNanoseconds());
            System.out.printf("QuickSort,%d,%d,%d,%d,%d\n",
                n,
                metrics.getTimeNanoseconds(),
                metrics.getComparisons(),
                metrics.getSwaps(),
                metrics.getMaxDepth()
            );
        }
        System.out.println("Results written to " + csvFile + "\n");
    }

    public static void testDeterministicSelect() {
        int[] sizes = {10, 100, 500, 1000, 5000, 10000, 50000, 100000};
        String csvFile = "deterministicselect_results.csv";
        initializeCsv(csvFile, "DeterministicSelect");
        System.out.println("DeterministicSelect,array size,time(ns),comparisons,swaps,maxdepth");
        for (int n : sizes) {
            int[] arr = generateRandomArray(n);
            int k = n / 2; // median (0-based)
            Metrics metrics = new Metrics();
            int val = DeterministicSelect.select(arr, k, metrics);
            metrics.writeCsv(csvFile, n, metrics.getTimeNanoseconds());
            System.out.printf("DeterministicSelect,%d,%d,%d,%d,%d\n",
                n,
                metrics.getTimeNanoseconds(),
                metrics.getComparisons(),
                metrics.getSwaps(),
                metrics.getMaxDepth()
            );
        }
        System.out.println("Results written to " + csvFile + "\n");
    }

    public static void testClosestPairOfPoints() {
        int[] sizes = {10, 100, 500, 1000, 5000, 10000};
        String csvFile = "closestpair_results.csv";
        initializeCsv(csvFile, "ClosestPairOfPoints");
        System.out.println("ClosestPairOfPoints,array size,time(ns),comparisons,maxdepth,distance");
        for (int n : sizes) {
            Point[] points = generateRandomPoints(n);
            Metrics metrics = new Metrics();
            double dist = ClosestPairOfPoints.minDistance(points, metrics);
            try (java.io.FileWriter fw = new java.io.FileWriter(csvFile, true)) {
                fw.write(String.format("ClosestPairOfPoints,%d,%d,%d,%d,%.8f\n",
                    n,
                    metrics.getTimeNanoseconds(),
                    metrics.getComparisons(),
                    metrics.getMaxDepth(),
                    dist));
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("ClosestPairOfPoints,%d,%d,%d,%d,%.8f\n",
                n,
                metrics.getTimeNanoseconds(),
                metrics.getComparisons(),
                metrics.getMaxDepth(),
                dist);
        }
        System.out.println("Results written to " + csvFile + "\n");
    }

    public static void main(String[] args) {
        testMergeSort();
        testQuickSort();
        testDeterministicSelect();
        testClosestPairOfPoints();
    }
}