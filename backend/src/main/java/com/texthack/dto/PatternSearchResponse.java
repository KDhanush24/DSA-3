package com.texthack.dto;

import java.util.List;
import java.util.ArrayList;
import com.texthack.datastructure.CustomArrayList;

public class PatternSearchResponse {
    private String algorithm;
    private long executionTimeNs;
    private List<Integer> matches;
    private int matchCount;
    private List<DocumentSearchMatch> documentMatches;
    private int[] lpsArray;
    private int[] zArray;
    private Long rollingHash;
    private int spuriousHits;
    private String timeComplexity;
    private String spaceComplexity;
    private String explanation;

    public PatternSearchResponse() {
        matches = new ArrayList<>();
        documentMatches = new ArrayList<>();
    }

    public void setMatchesFromCustom(CustomArrayList<Integer> customMatches) {
        this.matches = new ArrayList<>();
        if (customMatches != null) {
            for (int i = 0; i < customMatches.size(); i++) {
                this.matches.add(customMatches.get(i));
            }
        }
        this.matchCount = this.matches.size();
    }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }

    public List<Integer> getMatches() { return matches; }
    public void setMatches(List<Integer> matches) { this.matches = matches; }

    public int getMatchCount() { return matchCount; }
    public void setMatchCount(int matchCount) { this.matchCount = matchCount; }

    public List<DocumentSearchMatch> getDocumentMatches() { return documentMatches; }
    public void setDocumentMatches(List<DocumentSearchMatch> documentMatches) { this.documentMatches = documentMatches; }

    public int[] getLpsArray() { return lpsArray; }
    public void setLpsArray(int[] lpsArray) { this.lpsArray = lpsArray; }

    public int[] getzArray() { return zArray; }
    public void setzArray(int[] zArray) { this.zArray = zArray; }

    public Long getRollingHash() { return rollingHash; }
    public void setRollingHash(Long rollingHash) { this.rollingHash = rollingHash; }

    public int getSpuriousHits() { return spuriousHits; }
    public void setSpuriousHits(int spuriousHits) { this.spuriousHits = spuriousHits; }

    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }

    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
}
