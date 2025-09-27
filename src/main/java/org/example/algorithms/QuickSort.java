package org.example.algorithms;

import java.util.Random;
import util.Metrics;

public class QuickSort {
    private static final Random random = new Random();

    public static void quickSort(int[] arr, int start, int end, Metrics metrics) {
        while (start < end) {
            metrics.enterRecursion();
            // randomized pivot selection
            int pivotIdx = start + random.nextInt(end - start + 1);
            swap(arr, pivotIdx, end, metrics);
            int pivot = partition(arr, start, end, metrics);
            // use recursion only on the determined smaller partition
            if (pivot - start < end - pivot) {
                quickSort(arr, start, pivot - 1, metrics);
                start = pivot + 1;
            } else {
                quickSort(arr, pivot + 1, end, metrics);
                end = pivot - 1;
            }
            metrics.exitRecursion();
        }
    }

    static int partition(int[] arr, int start, int end, Metrics metrics) {
        int pivot = arr[end];
        int i = start - 1;
        for (int j = start; j <= end - 1; j++) {
            metrics.incrementComparisons();
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j, metrics);
            }
        }
        i++;
        swap(arr, i, end, metrics);
        return i;
    }

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            metrics.incrementSwaps();
        }
    }

    // Public API
    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        metrics.reset();
        metrics.startTimer();
        quickSort(arr, 0, arr.length - 1, metrics);
        metrics.stopTimer();
    }
}
