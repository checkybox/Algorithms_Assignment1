package org.example;

import org.example.algorithms.MergeSort;
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
        System.out.println("Results written to " + csvFile);
    }

    public static void main(String[] args) {
        testMergeSort();
    }
}