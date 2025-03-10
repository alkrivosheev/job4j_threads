package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;
import static ru.job4j.pools.ParallelSearch.parallelSearch;

class ParallelSearchTest {
    @Test
    public void testIntegerArray() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int target = 7;
        int expectedIndex = 6;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testStringArray() {
        String[] array = {"apples", "task", "cherry", "date", "EVE"};
        String target = "cherry";
        int expectedIndex = 2;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testSmallArray() {
        Integer[] array = {5, 3, 8, 1};
        int target = 3;
        int expectedIndex = 1;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testLargeArray() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i + 1;
        }
        int target = 50;
        int expectedIndex = 49;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5};
        int target = 10;
        int expectedIndex = -1;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }

    @Test
    public void testEmptyArray() {
        Integer[] array = {};
        int target = 5;
        int expectedIndex = -1;
        int result = parallelSearch(array, target);
        assertEquals(expectedIndex, result);
    }
}