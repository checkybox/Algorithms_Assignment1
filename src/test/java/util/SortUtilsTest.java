package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class SortUtilsTest {
    @Test
    void testSwap() {
        int[] arr = {1, 2, 3};
        SortUtils.swap(arr, 0, 2);
        assertArrayEquals(new int[]{3, 2, 1}, arr);
        SortUtils.swap(arr, 1, 1); // no change
        assertArrayEquals(new int[]{3, 2, 1}, arr);
        SortUtils.swap(arr, -1, 2); // out of bounds, no change
        assertArrayEquals(new int[]{3, 2, 1}, arr);
    }

    @Test
    void testPartition() {
        int[] arr = {4, 3, 2, 1};
        int pivotIdx = SortUtils.partition(arr, 0, arr.length - 1);
        // after partition, all elements < pivot (1) are to the left, but since 1 is smallest, it should be at index 0
        assertEquals(0, pivotIdx);
        assertEquals(1, arr[0]);
        // test with invalid bounds
        assertEquals(-1, SortUtils.partition(arr, -1, 10));
    }

    @Test
    void testShuffle() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] original = Arrays.copyOf(arr, arr.length);
        SortUtils.shuffle(arr);
        Arrays.sort(arr);
        assertArrayEquals(original, arr);
        SortUtils.shuffle(null); // test shuffle on null
    }
}

