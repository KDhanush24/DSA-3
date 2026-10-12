package com.texthack.algorithm.string;

import com.texthack.datastructure.CustomArrayList;

public class RabinKarp {
    public static final int BASE = 256;
    public static final int MOD = 1000000007; // Prime modulus

    public static class RKResult {
        public final CustomArrayList<Integer> matches;
        public final long patternHash;
        public final int spuriousHits;

        public RKResult(CustomArrayList<Integer> matches, long patternHash, int spuriousHits) {
            this.matches = matches;
            this.patternHash = patternHash;
            this.spuriousHits = spuriousHits;
        }
    }

    public static CustomArrayList<Integer> search(String text, String pattern) {
        return searchWithDetails(text, pattern).matches;
    }

    public static RKResult searchWithDetails(String text, String pattern) {
        CustomArrayList<Integer> matches = new CustomArrayList<>();
        if (text == null || pattern == null || pattern.isEmpty() || text.length() < pattern.length()) {
            return new RKResult(matches, 0, 0);
        }

        int m = pattern.length();
        int n = text.length();
        long p = 0; // hash value for pattern
        long t = 0; // hash value for text window
        long h = 1;
        int spuriousHits = 0;

        // h = pow(BASE, m-1) % MOD
        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MOD;
        }

        // Initial hashes
        for (int i = 0; i < m; i++) {
            p = (BASE * p + pattern.charAt(i)) % MOD;
            t = (BASE * t + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (p == t) {
                // Character by character verification to guard against hash collisions
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        spuriousHits++;
                        break;
                    }
                }
                if (match) {
                    matches.add(i);
                }
            }

            if (i < n - m) {
                t = (BASE * (t - text.charAt(i) * h) + text.charAt(i + m)) % MOD;
                if (t < 0) {
                    t += MOD;
                }
            }
        }

        return new RKResult(matches, p, spuriousHits);
    }
}
