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
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertEquals(6, result[0].getRowSum());
        assertEquals(15, result[1].getRowSum());
        assertEquals(24, result[2].getRowSum());
        assertEquals(12, result[0].getColSum());
        assertEquals(15, result[1].getColSum());
        assertEquals(18, result[2].getColSum());
    }

    @Test
    void testSumWith2x2Matrix() {
        int[][] matrix = {
                {10, 20},
                {30, 40}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertEquals(30, result[0].getRowSum());
        assertEquals(70, result[1].getRowSum());
        assertEquals(40, result[0].getColSum());
        assertEquals(60, result[1].getColSum());
    }

    @Test
    void testSumWith1x1Matrix() {
        int[][] matrix = {
                {42}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertEquals(42, result[0].getRowSum());
        assertEquals(42, result[0].getColSum());
    }

    @Test
    void testAsyncSumWith3x3Matrix() throws Exception {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertEquals(6, result[0].getRowSum());
        assertEquals(15, result[1].getRowSum());
        assertEquals(24, result[2].getRowSum());
        assertEquals(12, result[0].getColSum());
        assertEquals(15, result[1].getColSum());
        assertEquals(18, result[2].getColSum());
    }

    @Test
    void testAsyncSumWith2x2Matrix() throws Exception {
        int[][] matrix = {
                {10, 20},
                {30, 40}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertEquals(30, result[0].getRowSum());
        assertEquals(70, result[1].getRowSum());
        assertEquals(40, result[0].getColSum());
        assertEquals(60, result[1].getColSum());
    }

    @Test
    void testAsyncSumWith1x1Matrix() throws Exception {
        int[][] matrix = {
                {42}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertEquals(42, result[0].getRowSum());
        assertEquals(42, result[0].getColSum());
    }

    @Test
    void testAsyncSumWithEmptyMatrix() throws Exception {
        int[][] matrix = {};
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);
        assertEquals(0, result.length);
    }

    @Test
    void testSumWithEmptyMatrix() {
        int[][] matrix = {};
        RolColSum.Sums[] result = RolColSum.sum(matrix);
        assertEquals(0, result.length);
    }
}