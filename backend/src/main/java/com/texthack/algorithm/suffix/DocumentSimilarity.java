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
        // Separators '#' and '$'
        String combined = docA + "#" + docB + "$";
        result.generalizedString = combined;

        int[] sa = SuffixArray.buildSuffixArray(combined);
        int[] lcp = KasaiLCP.buildLCPArray(combined, sa);

        result.suffixArray = sa;
        result.lcpArray = lcp;

        int maxLcp = 0;
        String bestLcs = "";
        int minSignificantLength = 3; // ignore 1-2 char noise

        for (int i = 1; i < combined.length(); i++) {
            int prevSuffix = sa[i - 1];
            int currSuffix = sa[i];

            boolean prevInA = prevSuffix < lenA;
            boolean currInA = currSuffix < lenA;
            boolean prevInB = prevSuffix > lenA && prevSuffix < combined.length() - 1;
            boolean currInB = currSuffix > lenA && currSuffix < combined.length() - 1;

            // One suffix in Doc A and one suffix in Doc B
            if ((prevInA && currInB) || (currInA && prevInB)) {
                int matchLen = lcp[i];
                if (matchLen > 0) {
                    String sub = combined.substring(currSuffix, currSuffix + matchLen);

                    if (matchLen > maxLcp) {
                        maxLcp = matchLen;
                        bestLcs = sub;
                    }

                    if (matchLen >= minSignificantLength) {
                        int posA = prevInA ? prevSuffix : currSuffix;
                        int posB = prevInB ? (prevSuffix - lenA - 1) : (currSuffix - lenA - 1);

                        // Avoid duplicate or substring-contained additions
                        boolean alreadyCovered = false;
                        for (int k = 0; k < result.commonSubstrings.size(); k++) {
                            String existing = result.commonSubstrings.get(k).substring;
                            if (existing.contains(sub)) {
                                alreadyCovered = true;
                                break;
                            }
                        }

                        if (!alreadyCovered && result.commonSubstrings.size() < 12) {
                            result.commonSubstrings.add(new CommonSubstring(sub, matchLen, posA, posB));
                        }
                    }
                }
            }
        }

        result.longestCommonSubstring = bestLcs;
        result.longestCommonSubstringLength = maxLcp;

        // Calculate total shared text length from significant non-duplicate substrings
        long totalSharedCover = 0;
        for (int k = 0; k < result.commonSubstrings.size(); k++) {
            totalSharedCover += result.commonSubstrings.get(k).length;
        }

        // Academic similarity metric:
        double harmonicDenom = (lenA + lenB) / 2.0;
        double lcsRatio = (double) maxLcp / harmonicDenom;
        double coverRatio = (double) totalSharedCover / harmonicDenom;
        double combinedRatio = Math.min(1.0, 0.6 * lcsRatio + 0.4 * coverRatio);

        result.similarityScore = Math.min(100.0, Math.round(combinedRatio * 1000.0) / 10.0);
        result.explanation = "Derived using Generalized Suffix Array and Kasai LCP array. Evaluates Longest Common Substrings (LCS) and overlapping token coverage across documents.";

        return result;
    }
}
