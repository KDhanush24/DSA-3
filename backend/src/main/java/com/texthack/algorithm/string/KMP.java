package com.texthack.algorithm.string;

import com.texthack.datastructure.CustomArrayList;

public class KMP {
    public static class KMPResult {
        public final CustomArrayList<Integer> matches;
        public final int[] lps;

        public KMPResult(CustomArrayList<Integer> matches, int[] lps) {
            this.matches = matches;
            this.lps = lps;
        }
    }

    public static CustomArrayList<Integer> search(String text, String pattern) {
        return searchWithDetails(text, pattern).matches;
    }

    public static KMPResult searchWithDetails(String text, String pattern) {
        CustomArrayList<Integer> matches = new CustomArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || text.length() < pattern.length()) {
            int[] emptyLps = (pattern != null && !pattern.isEmpty()) ? computeLPSArray(pattern) : new int[0];
            return new KMPResult(matches, emptyLps);
        }

        int[] lps = computeLPSArray(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == pattern.length()) {
                matches.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return new KMPResult(matches, lps);
    }

    public static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int length = 0;
        int i = 1;
        lps[0] = 0;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
