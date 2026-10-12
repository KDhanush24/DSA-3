package com.texthack.algorithm.suffix;

/**
 * Practical O(n log^2 n) Suffix Array construction using prefix doubling.
 * Strictly uses primitive arrays and custom sorting (no java.util collections).
 */
public class SuffixArray {

    public static int[] buildSuffixArray(String s) {
        if (s == null || s.isEmpty()) return new int[0];
        int n = s.length();
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] tempRank = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = (int) s.charAt(i);
        }

        for (int k = 1; k < n; k *= 2) {
            final int len = k;
            quickSort(sa, rank, len, 0, n - 1);

            tempRank[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                boolean same1 = rank[sa[i]] == rank[sa[i - 1]];
                int r2Curr = (sa[i] + len < n) ? rank[sa[i] + len] : -1;
                int r2Prev = (sa[i - 1] + len < n) ? rank[sa[i - 1] + len] : -1;
                boolean same2 = r2Curr == r2Prev;

                tempRank[sa[i]] = (same1 && same2) ? tempRank[sa[i - 1]] : tempRank[sa[i - 1]] + 1;
            }

            for (int i = 0; i < n; i++) {
                rank[i] = tempRank[i];
            }

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }
        }
        return sa;
    }

    private static void quickSort(int[] sa, int[] rank, int len, int low, int high) {
        if (low < high) {
            int pi = partition(sa, rank, len, low, high);
            quickSort(sa, rank, len, low, pi - 1);
            quickSort(sa, rank, len, pi + 1, high);
        }
    }

    private static int partition(int[] sa, int[] rank, int len, int low, int high) {
        int pivot = sa[high];
        int i = low - 1;
        int n = rank.length;
        int pRank1 = rank[pivot];
        int pRank2 = (pivot + len < n) ? rank[pivot + len] : -1;

        for (int j = low; j < high; j++) {
            int curr = sa[j];
            int cRank1 = rank[curr];
            int cRank2 = (curr + len < n) ? rank[curr + len] : -1;

            if (cRank1 < pRank1 || (cRank1 == pRank1 && cRank2 < pRank2)) {
                i++;
                int temp = sa[i];
                sa[i] = sa[j];
                sa[j] = temp;
            }
        }
        int temp = sa[i + 1];
        sa[i + 1] = sa[high];
        sa[high] = temp;
        return i + 1;
    }
}
