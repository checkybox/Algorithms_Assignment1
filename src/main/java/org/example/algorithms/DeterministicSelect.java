package org.example.algorithms;

import util.Metrics;

public class DeterministicSelect {
    private static final int GROUP_SIZE = 5;

    // Public API
    public static int select(int[] arr, int k, Metrics metrics) {
        if (arr == null) throw new IllegalArgumentException("array is null");
        if (k < 0 || k >= arr.length) throw new IllegalArgumentException("k out of bounds");
        metrics.reset();
        metrics.startTimer();
        int result = select(arr, 0, arr.length - 1, k, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int select(int[] a, int left, int right, int k, Metrics metrics) {
        while (true) {
            if (left == right) return a[left];
            int pivotIndex = medianOfMedians(a, left, right, metrics);
            int pivotFinal = partition(a, left, right, pivotIndex, metrics);
            int leftSize = pivotFinal - left;
            if (k == leftSize) return a[pivotFinal];
            // prefer recursing into the smaller side
            int rightSize = right - pivotFinal;
            if (k < leftSize) {
                if (leftSize <= rightSize) {
                    metrics.enterRecursion();
                    int res = select(a, left, pivotFinal - 1, k, metrics);
                    metrics.exitRecursion();
                    return res;
                } else {
                    right = pivotFinal - 1;
                }
            } else {
                int newK = k - leftSize - 1;
                if (rightSize <= leftSize) {
                    metrics.enterRecursion();
                    int res = select(a, pivotFinal + 1, right, newK, metrics);
                    metrics.exitRecursion();
                    return res;
                } else {
                    left = pivotFinal + 1;
                    k = newK;
                }
            }
        }
    }

    private static int medianOfMedians(int[] a, int left, int right, Metrics metrics) {
        int n = right - left + 1;
        if (n <= GROUP_SIZE) {
            insertionSort(a, left, right, metrics);
            return left + n / 2;
        }
        int dst = left;
        for (int i = left; i <= right; i += GROUP_SIZE) {
            int subRight = Math.min(i + GROUP_SIZE - 1, right);
            insertionSort(a, i, subRight, metrics);
            int medianIdx = i + (subRight - i) / 2;
            swap(a, dst++, medianIdx, metrics);
        }
        int medLeft = left;
        int medRight = dst - 1;
        return medianOfMedians(a, medLeft, medRight, metrics);
    }

    private static int partition(int[] a, int left, int right, int pivotIndex, Metrics metrics) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, right, metrics);
        int store = left;
        for (int i = left; i < right; i++) {
            metrics.incrementComparisons();
            if (a[i] < pivot) {
                swap(a, store++, i, metrics);
            }
        }
        swap(a, store, right, metrics);
        return store;
    }

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

    private static void swap(int[] arr, int i, int j, Metrics metrics) {
        if (i != j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            metrics.incrementSwaps();
        }
    }
}
