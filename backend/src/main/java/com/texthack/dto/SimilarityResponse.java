package com.texthack.dto;

import java.util.List;

public class SimilarityResponse {
    public static class SubstringMatch {
        public String substring;
        public int length;
        public int posA;
        public int posB;

        public SubstringMatch() {}
        public SubstringMatch(String substring, int length, int posA, int posB) {
            this.substring = substring;
            this.length = length;
            this.posA = posA;
            this.posB = posB;
        }
    }

    private double similarityScore;
    private String longestCommonSubstring;
    private int longestCommonSubstringLength;
    private List<SubstringMatch> commonSubstrings;
    private int[] suffixArray;
    private int[] lcpArray;
    private String explanation;
    private long executionTimeNs;
    private String algorithm;

    public double getSimilarityScore() { return similarityScore; }
    public void setSimilarityScore(double similarityScore) { this.similarityScore = similarityScore; }

    public String getLongestCommonSubstring() { return longestCommonSubstring; }
    public void setLongestCommonSubstring(String longestCommonSubstring) { this.longestCommonSubstring = longestCommonSubstring; }

    public int getLongestCommonSubstringLength() { return longestCommonSubstringLength; }
    public void setLongestCommonSubstringLength(int longestCommonSubstringLength) { this.longestCommonSubstringLength = longestCommonSubstringLength; }

    public List<SubstringMatch> getCommonSubstrings() { return commonSubstrings; }
    public void setCommonSubstrings(List<SubstringMatch> commonSubstrings) { this.commonSubstrings = commonSubstrings; }

    public int[] getSuffixArray() { return suffixArray; }
    public void setSuffixArray(int[] suffixArray) { this.suffixArray = suffixArray; }

    public int[] getLcpArray() { return lcpArray; }
    public void setLcpArray(int[] lcpArray) { this.lcpArray = lcpArray; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
}
