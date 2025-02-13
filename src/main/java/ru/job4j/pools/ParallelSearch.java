package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T target;
    private final int low;
    private final int high;

    private int lineSearch() {
        for (int i = low; i <= high; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public ParallelSearch(T[] array, T target, int low, int high) {
        this.array = array;
        this.target = target;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        if (high - low <= 10) {
            return lineSearch();
        }
        int mid = low + (high - low) / 2;
        ParallelSearch<T> leftTask = new ParallelSearch<>(array, target, low, mid);
        ParallelSearch<T> rightTask = new ParallelSearch<>(array, target, mid + 1, high);
        leftTask.fork();
        int rightResult = rightTask.compute();
        int leftResult = leftTask.join();
        return leftResult != -1 ? leftResult : rightResult;
    }

    public static <T> int parallelSearch(T[] array, T target) {
        if (array == null || array.length == 0 || target == null) {
            return -1;
        }
        ForkJoinPool pool = ForkJoinPool.commonPool();
        ParallelSearch<T> task = new ParallelSearch<>(array, target, 0, array.length - 1);
        return pool.invoke(task);
    }
}
