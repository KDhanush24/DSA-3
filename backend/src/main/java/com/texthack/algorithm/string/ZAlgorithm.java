package com.texthack.algorithm.string;

import com.texthack.datastructure.CustomArrayList;

public class ZAlgorithm {
    public static class ZResult {
        public final CustomArrayList<Integer> matches;
        public final int[] zArray;
        public final String concatenated;

        public ZResult(CustomArrayList<Integer> matches, int[] zArray, String concatenated) {
            this.matches = matches;
            this.zArray = zArray;
            this.concatenated = concatenated;
        }
    }

    public static CustomArrayList<Integer> search(String text, String pattern) {
        return searchWithDetails(text, pattern).matches;
    }

    public static ZResult searchWithDetails(String text, String pattern) {
        CustomArrayList<Integer> matches = new CustomArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || text.length() < pattern.length()) {
            return new ZResult(matches, new int[0], "");
        }

        String concat = pattern + "$" + text;
        int[] Z = computeZArray(concat);

        for (int i = 0; i < Z.length; ++i) {
            if (Z[i] == pattern.length()) {
                matches.add(i - pattern.length() - 1);
            }
        }
        return new ZResult(matches, Z, concat);
    }

    public static int[] computeZArray(String str) {
        int n = str.length();
        int[] Z = new int[n];
        int left = 0, right = 0;

        for (int i = 1; i < n; ++i) {
            if (i > right) {
                left = right = i;
                while (right < n && str.charAt(right - left) == str.charAt(right)) {
                    right++;
                }
                Z[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (Z[k] < right - i + 1) {
                    Z[i] = Z[k];
                } else {
                    left = i;
                    while (right < n && str.charAt(right - left) == str.charAt(right)) {
                        right++;
                    }
                    Z[i] = right - left;
                    right--;
                }
            }
        }
        return Z;
    }
}
