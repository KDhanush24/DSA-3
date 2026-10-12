package com.texthack.algorithm.suffix;

/**
 * Practical O(n log^2 n) Suffix Array construction using prefix doubling.
 * Strictly uses primitive arrays and custom sorting (no java.util collections).
 */
public class SuffixArray {

    public static class SuffixTuple {
        public int index;
        public int rank1;
        public int rank2;

        public SuffixTuple(int index, int rank1, int rank2) {
            this.index = index;
            this.rank1 = rank1;
            this.rank2 = rank2;
        }
    }

    public static int[] buildSuffixArray(String s) {
        if (s == null || s.isEmpty()) return new int[0];
        int n = s.length();
        SuffixTuple[] tuples = new SuffixTuple[n];

        // Initial ranks based on first character ASCII/Unicode value
        for (int i = 0; i < n; i++) {
            int nextRank = (i + 1 < n) ? (int) s.charAt(i + 1) : -1;
            tuples[i] = new SuffixTuple(i, (int) s.charAt(i), nextRank);
        }

        customSort(tuples, 0, n - 1);

        int[] ind = new int[n];
        for (int k = 4; k < 2 * n; k *= 2) {
            int rank = 0;
            int prevRank = tuples[0].rank1;
            tuples[0].rank1 = rank;
            ind[tuples[0].index] = 0;

            for (int i = 1; i < n; i++) {
                if (tuples[i].rank1 == prevRank && tuples[i].rank2 == tuples[i - 1].rank2) {
                    prevRank = tuples[i].rank1;
                    tuples[i].rank1 = rank;
                } else {
                    prevRank = tuples[i].rank1;
                    tuples[i].rank1 = ++rank;
                }
                ind[tuples[i].index] = i;
            }

            for (int i = 0; i < n; i++) {
                int nextIndex = tuples[i].index + k / 2;
                tuples[i].rank2 = (nextIndex < n) ? tuples[ind[nextIndex]].rank1 : -1;
            }

            customSort(tuples, 0, n - 1);
        }

        int[] suffixArr = new int[n];
        for (int i = 0; i < n; i++) {
            suffixArr[i] = tuples[i].index;
        }
        return suffixArr;
    }

    // Custom quicksort for SuffixTuple without any java.util dependencies
    private static void customSort(SuffixTuple[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            customSort(arr, low, pi - 1);
            customSort(arr, pi + 1, high);
        }
    }

    private static int partition(SuffixTuple[] arr, int low, int high) {
        SuffixTuple pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot) < 0) {
                i++;
                SuffixTuple temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        SuffixTuple temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    private static int compare(SuffixTuple a, SuffixTuple b) {
        if (a.rank1 != b.rank1) {
            return Integer.compare(a.rank1, b.rank1);
        }
        return Integer.compare(a.rank2, b.rank2);
    }
}
