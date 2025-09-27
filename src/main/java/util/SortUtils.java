package util;

import java.util.Random;

public class SortUtils {
    public static void swap(int[] arr, int i, int j) {
        // safeguards
        if (arr == null || i < 0 || j < 0 || i >= arr.length || j >= arr.length) return;
        if (i != j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static int partition(int[] arr, int start, int end) {
        // safeguards
        if (arr == null || start < 0 || end >= arr.length || start > end) return -1;
        int pivot = arr[end];
        int i = start - 1;
        for (int j = start; j <= end - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, end);
        return i + 1;
    }

    public static void shuffle(int[] arr) {
        // safeguard
        if (arr == null) return;
        Random rand = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(arr, i, j);
        }
    }
}

