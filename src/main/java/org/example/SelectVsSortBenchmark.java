package org.example;

import org.example.algorithms.DeterministicSelect;
import util.Metrics;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class SelectVsSortBenchmark {
    @Param({"1000", "10000", "100000"})
    public int arraySize;

    public int[] array;
    public int k;
    public Random random;

    @Setup(Level.Invocation)
    public void setUp() {
        random = new Random();
        array = random.ints(arraySize, 0, arraySize * 10).toArray();
        k = random.nextInt(arraySize); // 0-based for DeterministicSelect
    }

    @Benchmark
    public int selectBenchmark() {
        int[] arrCopy = Arrays.copyOf(array, array.length);
        Metrics metrics = new Metrics();
        return DeterministicSelect.select(arrCopy, k, metrics);
    }

    @Benchmark
    public int sortBenchmark() {
        int[] arrCopy = Arrays.copyOf(array, array.length);
        Arrays.sort(arrCopy);
        return arrCopy[k];
    }
}
