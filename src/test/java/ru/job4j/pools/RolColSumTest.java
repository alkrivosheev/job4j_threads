package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RolColSumTest {

    @Test
    void testSumWith3x3Matrix() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testSumWith2x2Matrix() {
        int[][] matrix = {
                {10, 20},
                {30, 40}
        };

        Sums[] expected = {
                new Sums(30, 40),
                new Sums(70, 60)
        };

        Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testSumWith1x1Matrix() {
        int[][] matrix = {
                {42}
        };

        Sums[] expected = {
                new Sums(42, 42)
        };

        Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testAsyncSumWith3x3Matrix() throws Exception {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testAsyncSumWith2x2Matrix() throws Exception {
        int[][] matrix = {
                {10, 20},
                {30, 40}
        };

        Sums[] expected = {
                new Sums(30, 40),
                new Sums(70, 60)
        };

        Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testAsyncSumWith1x1Matrix() throws Exception {
        int[][] matrix = {
                {42}
        };

        Sums[] expected = {
                new Sums(42, 42)
        };

        Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void testAsyncSumWithEmptyMatrix() throws Exception {
        int[][] matrix = {};

        Sums[] result = RolColSum.asyncSum(matrix);
        assertEquals(0, result.length);
    }

    @Test
    void testSumWithEmptyMatrix() {
        int[][] matrix = {};

        Sums[] result = RolColSum.sum(matrix);
        assertEquals(0, result.length);
    }
}