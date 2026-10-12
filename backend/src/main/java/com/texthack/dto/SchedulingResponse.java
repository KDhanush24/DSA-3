package com.texthack.dto;

import java.util.List;

public class SchedulingResponse {
    public static class MatchedEdgeDto {
        public int u;
        public int v;

        public MatchedEdgeDto() {}
        public MatchedEdgeDto(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    private List<Integer> approximateCover;
    private List<MatchedEdgeDto> maximalMatching;
    private List<Integer> optimalCover;
    private int approxSize;
    private int optimalSize;
    private double approximationRatio;
    private String guaranteeExplanation;
    private long executionTimeNs;

    public List<Integer> getApproximateCover() { return approximateCover; }
    public void setApproximateCover(List<Integer> approximateCover) { this.approximateCover = approximateCover; }

    public List<MatchedEdgeDto> getMaximalMatching() { return maximalMatching; }
    public void setMaximalMatching(List<MatchedEdgeDto> maximalMatching) { this.maximalMatching = maximalMatching; }

    public List<Integer> getOptimalCover() { return optimalCover; }
    public void setOptimalCover(List<Integer> optimalCover) { this.optimalCover = optimalCover; }

    public int getApproxSize() { return approxSize; }
    public void setApproxSize(int approxSize) { this.approxSize = approxSize; }

    public int getOptimalSize() { return optimalSize; }
    public void setOptimalSize(int optimalSize) { this.optimalSize = optimalSize; }

    public double getApproximationRatio() { return approximationRatio; }
    public void setApproximationRatio(double approximationRatio) { this.approximationRatio = approximationRatio; }

    public String getGuaranteeExplanation() { return guaranteeExplanation; }
    public void setGuaranteeExplanation(String guaranteeExplanation) { this.guaranteeExplanation = guaranteeExplanation; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }
}
