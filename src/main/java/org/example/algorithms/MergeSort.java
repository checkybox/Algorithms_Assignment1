package org.example.algorithms;

import util.Metrics;

public class MergeSort {
    private static final int INSERTION_SORT_CUTOFF = 16;

    // Insertion sort for small subarrays
    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incrementComparisons();
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                    metrics.incrementSwaps();
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
        }
    }

    static void merge(int[] arr, int left, int mid, int right, int[] buffer, Metrics metrics) {
        // copy arr[] to buffer[]
        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }

        int i = left; // index for sorted array
        int l = left; // buffer array left index
        int r = mid + 1; // buffer array right index

        while (l <= mid && r <= right) {
            metrics.incrementComparisons();
            if (buffer[l] <= buffer[r]) {
                arr[i++] = buffer[l++];
            } else {
                arr[i++] = buffer[r++];
            }
        }

        // copy leftovers if any
        while (l <= mid) {
            arr[i++] = buffer[l++];
        }
        while (r <= right) {
            arr[i++] = buffer[r++];
        }
    }

    static void mergeSort(int[] arr, int l, int r, int[] buffer, Metrics metrics) {
        metrics.enterRecursion();
        if (r - l + 1 <= INSERTION_SORT_CUTOFF) {
            insertionSort(arr, l, r, metrics);
            metrics.exitRecursion();
            return;
        }
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m, buffer, metrics);
            mergeSort(arr, m + 1, r, buffer, metrics);
            merge(arr, l, m, r, buffer, metrics);
        }
        metrics.exitRecursion();
    }

    // Public API
    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        metrics.reset();
        metrics.startTimer();
        int[] buffer = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, buffer, metrics);
        metrics.stopTimer();
    }
}
