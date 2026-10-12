package com.texthack.dto;

import java.util.List;

public class FuzzySearchResponse {
    private List<FuzzyMatch> suggestions;
    private long executionTimeNs;
    private int[][] dpMatrix;
    private String matrixWord1;
    private String matrixWord2;
    private String algorithm;
    private String timeComplexity;
    private String spaceComplexity;

    public List<FuzzyMatch> getSuggestions() { return suggestions; }
    public void setSuggestions(List<FuzzyMatch> suggestions) { this.suggestions = suggestions; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }

    public int[][] getDpMatrix() { return dpMatrix; }
    public void setDpMatrix(int[][] dpMatrix) { this.dpMatrix = dpMatrix; }

    public String getMatrixWord1() { return matrixWord1; }
    public void setMatrixWord1(String matrixWord1) { this.matrixWord1 = matrixWord1; }

    public String getMatrixWord2() { return matrixWord2; }
    public void setMatrixWord2(String matrixWord2) { this.matrixWord2 = matrixWord2; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }

    public String getSpaceComplexity() { return spaceComplexity; }
    public void setSpaceComplexity(String spaceComplexity) { this.spaceComplexity = spaceComplexity; }
}
