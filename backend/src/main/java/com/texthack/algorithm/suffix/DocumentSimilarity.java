package com.texthack.algorithm.suffix;

import com.texthack.datastructure.CustomArrayList;

public class DocumentSimilarity {

    public static class CommonSubstring {
        public String substring;
        public int length;
        public int docAIndex;
        public int docBIndex;

        public CommonSubstring(String substring, int length, int docAIndex, int docBIndex) {
            this.substring = substring;
            this.length = length;
            this.docAIndex = docAIndex;
            this.docBIndex = docBIndex;
        }
    }

    public static class SimilarityResult {
        public double similarityScore; // 0.0 to 100.0
        public CustomArrayList<CommonSubstring> commonSubstrings;
        public int longestCommonSubstringLength;
        public String longestCommonSubstring;
        public int[] suffixArray;
        public int[] lcpArray;
        public String generalizedString;
        public String explanation;

        public SimilarityResult() {
            commonSubstrings = new CustomArrayList<>();
        }
    }

    public static SimilarityResult analyze(String docA, String docB) {
        SimilarityResult result = new SimilarityResult();

        if (docA == null) docA = "";
        if (docB == null) docB = "";

        if (docA.isEmpty() || docB.isEmpty()) {
            result.similarityScore = 0.0;
            result.longestCommonSubstring = "";
            result.longestCommonSubstringLength = 0;
            result.explanation = "One or both documents are empty.";
            return result;
        }

        if (docA.equals(docB)) {
            result.similarityScore = 100.0;
            result.longestCommonSubstring = docA;
            result.longestCommonSubstringLength = docA.length();
            result.explanation = "Documents are identical (100% match).";
            return result;
        }

        int lenA = docA.length();
        int lenB = docB.length();
        // Separators '#' and '$' (safe characters unlikely to appear or distinctive)
        String combined = docA + "#" + docB + "$";
        result.generalizedString = combined;

        int[] sa = SuffixArray.buildSuffixArray(combined);
        int[] lcp = KasaiLCP.buildLCPArray(combined, sa);

        result.suffixArray = sa;
        result.lcpArray = lcp;

        int maxLcp = 0;
        int maxLcpIdx = -1;
        long totalCommonCover = 0;
        int minSignificantLength = 3; // ignore tiny 1-2 char noise

        for (int i = 1; i < combined.length(); i++) {
            int prevSuffix = sa[i - 1];
            int currSuffix = sa[i];

            boolean prevInA = prevSuffix < lenA;
            boolean currInA = currSuffix < lenA;
            boolean prevInB = prevSuffix > lenA && prevSuffix < combined.length() - 1;
            boolean currInB = currSuffix > lenA && currSuffix < combined.length() - 1;

            // One suffix in Doc A and one suffix in Doc B
            if ((prevInA && currInB) || (currInB && prevInA)) {
                int matchLen = lcp[i];
                if (matchLen > 0) {
                    if (matchLen > maxLcp) {
                        maxLcp = matchLen;
                        maxLcpIdx = Math.min(prevSuffix, currSuffix);
                    }

                    if (matchLen >= minSignificantLength) {
                        int posA = prevInA ? prevSuffix : currSuffix;
                        int posB = prevInB ? (prevSuffix - lenA - 1) : (currSuffix - lenA - 1);
                        String sub = combined.substring(sa[i], sa[i] + matchLen);
                        
                        // Avoid storing excessive duplicate entries
                        boolean alreadyAdded = false;
                        for (int k = 0; k < result.commonSubstrings.size(); k++) {
                            if (result.commonSubstrings.get(k).substring.equals(sub)) {
                                alreadyAdded = true;
                                break;
                            }
                        }
                        if (!alreadyAdded && result.commonSubstrings.size() < 15) {
                            result.commonSubstrings.add(new CommonSubstring(sub, matchLen, posA, posB));
                            totalCommonCover += matchLen;
                        }
                    }
                }
            }
        }

        if (maxLcpIdx != -1 && maxLcp > 0) {
            result.longestCommonSubstringLength = maxLcp;
            result.longestCommonSubstring = combined.substring(maxLcpIdx, maxLcpIdx + maxLcp);
        } else {
            result.longestCommonSubstring = "";
            result.longestCommonSubstringLength = 0;
        }

        // Academic similarity metric:
        // Harmonic mean of shared substrings coverage + LCS ratio
        double lcsRatio = (2.0 * maxLcp) / (lenA + lenB);
        double coverRatio = (2.0 * totalCommonCover) / (lenA + lenB);
        double combinedRatio = Math.min(1.0, 0.4 * lcsRatio + 0.6 * coverRatio);

        result.similarityScore = Math.round(combinedRatio * 1000.0) / 10.0;
        result.explanation = "Derived using Generalized Suffix Array and Kasai LCP array. Evaluates Longest Common Substrings (LCS) and overlapping token coverage across documents.";

        return result;
    }
}
