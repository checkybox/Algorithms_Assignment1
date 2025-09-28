package util;

import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    private int comparisons;
    private int swaps;
    private int currentDepth;
    private int maxDepth;

    /* time-related fields and methods */
    private long startTime;
    private long timeNanoseconds;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        timeNanoseconds = System.nanoTime() - startTime;
    }

    public long getTimeNanoseconds() {
        return timeNanoseconds;
    }

    public void incrementComparisons() {
        comparisons++;
    }

    public void incrementSwaps() {
        swaps++;
    }

    // call when entering a recursive call
    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    // call when exiting a recursive call
    public void exitRecursion() {
        if (currentDepth > 0) {
            currentDepth--;
        }
    }

    public int getComparisons() {
        return comparisons;
    }

    public int getSwaps() {
        return swaps;
    }

    public int getCurrentDepth() {
        return currentDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void reset() {
        comparisons = 0;
        swaps = 0;
        currentDepth = 0;
        maxDepth = 0;
        startTime = 0;
        timeNanoseconds = 0;
    }

    public void writeCsv(String filename, String algorithmName, int n, long timeNanos) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(algorithmName + "," + n + "," + timeNanos + "," +
                    getComparisons() + "," +
                    getSwaps() + "," +
                    getMaxDepth() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
