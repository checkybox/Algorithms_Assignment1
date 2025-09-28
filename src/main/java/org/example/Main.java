package org.example;

import org.example.algorithms.MergeSort;
import org.example.algorithms.QuickSort;
import org.example.algorithms.DeterministicSelect;
import org.example.algorithms.ClosestPairOfPoints;
import org.example.algorithms.ClosestPairOfPoints.Point;
import util.Metrics;
import java.util.Random;
import java.util.Arrays;

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

    public static void runCli(String[] args) {
        if (args.length == 0 || Arrays.asList(args).contains("--help")) {
            printCliUsage();
            return;
        }

        String algo = null;
        int size = -1;
        int k = -1;
        String csvFile = null;
        long seed = System.currentTimeMillis();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo":
                    if (i + 1 < args.length) algo = args[++i];
                    break;
                case "--size":
                    if (i + 1 < args.length) size = Integer.parseInt(args[++i]);
                    break;
                case "--k":
                    if (i + 1 < args.length) k = Integer.parseInt(args[++i]);
                    break;
                case "--csv":
                    if (i + 1 < args.length) csvFile = args[++i];
                    break;
                case "--seed":
                    if (i + 1 < args.length) seed = Long.parseLong(args[++i]);
                    break;
            }
        }

        if (algo == null || size <= 0 || csvFile == null) {
            System.err.println("Missing required arguments.");
            printCliUsage();
            return;
        }

        Random random = new Random(seed);
        Metrics metrics = new Metrics();
        switch (algo.toLowerCase()) {
            case "mergesort": {
                int[] arr = new int[size];
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                MergeSort.sort(arr, metrics);
                writeCsvHeader(csvFile, "MergeSort");
                metrics.writeCsv(csvFile, size, metrics.getTimeNanoseconds());
                printSummary("MergeSort", size, metrics, csvFile);
                break;
            }
            case "quicksort": {
                int[] arr = new int[size];
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                QuickSort.sort(arr, metrics);
                writeCsvHeader(csvFile, "QuickSort");
                metrics.writeCsv(csvFile, size, metrics.getTimeNanoseconds());
                printSummary("QuickSort", size, metrics, csvFile);
                break;
            }
            case "select": {
                if (k < 0 || k >= size) {
                    System.err.println("For select, --k must be in [0, size-1]");
                    return;
                }
                int[] arr = new int[size];
                for (int i = 0; i < size; i++) arr[i] = random.nextInt(100000);
                int val = DeterministicSelect.select(arr, k, metrics);
                writeCsvHeader(csvFile, "DeterministicSelect");
                metrics.writeCsv(csvFile, size, metrics.getTimeNanoseconds());
                printSummary("DeterministicSelect", size, metrics, csvFile);
                System.out.println("Selected value (k=" + k + "): " + val);
                break;
            }
            case "closest": {
                Point[] points = new Point[size];
                for (int i = 0; i < size; i++) {
                    points[i] = new Point(random.nextDouble() * 10000, random.nextDouble() * 10000);
                }
                double dist = ClosestPairOfPoints.minDistance(points, metrics);
                writeCsvHeader(csvFile, "ClosestPairOfPoints");
                try (java.io.FileWriter fw = new java.io.FileWriter(csvFile, true)) {
                    fw.write(String.format("ClosestPairOfPoints,%d,%d,%d,%d,%.8f\n",
                        size,
                        metrics.getTimeNanoseconds(),
                        metrics.getComparisons(),
                        metrics.getMaxDepth(),
                        dist));
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
                printSummary("ClosestPairOfPoints", size, metrics, csvFile);
                System.out.println("Closest distance: " + dist);
                break;
            }
            default:
                System.err.println("Unknown algorithm: " + algo);
                printCliUsage();
        }
    }

    private static void printCliUsage() {
        System.out.println("Usage: java -jar algorithms.jar --algo <mergesort|quicksort|select|closest> --size <n> [--k <k>] --csv <file> [--seed <seed>]");
        System.out.println("Examples:");
        System.out.println("  --algo mergesort --size 10000 --csv mergesort.csv");
        System.out.println("  --algo select --size 10000 --k 5000 --csv select.csv");
        System.out.println("  --algo closest --size 1000 --csv closest.csv");
    }

    private static void writeCsvHeader(String filename, String algo) {
        try (java.io.FileWriter fw = new java.io.FileWriter(filename)) {
            if (algo.equals("ClosestPairOfPoints")) {
                fw.write(algo + ",array size,time(ns),comparisons,maxdepth,distance\n");
            } else {
                fw.write(algo + ",array size,time(ns),comparisons,swaps,maxdepth\n");
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printSummary(String algo, int size, Metrics metrics, String csvFile) {
        System.out.printf("%s: n=%d, time(ns)=%d, comparisons=%d, swaps=%d, maxDepth=%d, CSV=%s\n",
            algo, size, metrics.getTimeNanoseconds(), metrics.getComparisons(), metrics.getSwaps(), metrics.getMaxDepth(), csvFile);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            runCli(args);
        } else {
            testMergeSort();
            testQuickSort();
            testDeterministicSelect();
            testClosestPairOfPoints();
        }
    }
}