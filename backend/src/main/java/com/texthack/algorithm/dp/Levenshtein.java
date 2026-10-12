package com.texthack.algorithm.dp;

public class Levenshtein {
    public static class Result {
        public final int distance;
        public final int[][] matrix;

        public Result(int distance, int[][] matrix) {
            this.distance = distance;
            this.matrix = matrix;
        }
    }

    public static int calculate(String s1, String s2) {
        return computeWithMatrix(s1, s2).distance;
    }

    public static Result computeWithMatrix(String s1, String s2) {
        if (s1 == null) s1 = "";
        if (s2 == null) s2 = "";

        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = min(
                    dp[i - 1][j] + 1,       // deletion
                    dp[i][j - 1] + 1,       // insertion
                    dp[i - 1][j - 1] + cost // substitution
                );
            }
        }

        return new Result(dp[m][n], dp);
    }

    private static int min(int a, int b, int c) {
        int m = Math.min(a, b);
        return Math.min(m, c);
    }
}
