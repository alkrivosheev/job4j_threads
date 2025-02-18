package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {

    private static Sums calculateSums(int[][] matrix, int index) {
        int n = matrix.length;
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < n; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }
        Sums sums = new Sums(rowSum, colSum);
        return sums;
    }

    public static Sums[] sum(int[][] matrix) {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        for (int i = 0; i < n; i++) {
            sums[i] = calculateSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws Exception {
        int n = matrix.length;
        Sums[] sums = new Sums[n];
        CompletableFuture<?>[] futures = new CompletableFuture[n];
        for (int i = 0; i < n; i++) {
            final int index = i;
            futures[i] = CompletableFuture.runAsync(() -> {
                sums[index] = calculateSums(matrix, index);
            });
        }
        CompletableFuture.allOf(futures).get();
        return sums;
    }
}