package com.texthack.algorithm.suffix;

/**
 * Kasai's algorithm for constructing Longest Common Prefix (LCP) array in O(n) time.
 * Strictly uses primitive arrays.
 */
public class KasaiLCP {

    public static int[] buildLCPArray(String s, int[] suffixArray) {
        if (s == null || suffixArray == null || s.isEmpty() || suffixArray.length == 0) {
            return new int[0];
        }

        int n = s.length();
        int[] lcp = new int[n];
        int[] rank = new int[n];

        // rank[i] stores the position of suffix starting at i in suffixArray
        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int h = 0; // length of previous LCP
        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int k = suffixArray[rank[i] - 1]; // previous suffix in sorted order
                while (i + h < n && k + h < n && s.charAt(i + h) == s.charAt(k + h)) {
                    h++;
                }
                lcp[rank[i]] = h;
                if (h > 0) {
                    h--;
                }
            }
        }

        return lcp;
    }
}
