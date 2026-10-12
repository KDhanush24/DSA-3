package com.texthack.dto;

import java.util.List;

public class FlowResponse {
    public static class FlowStepDto {
        public List<Integer> path;
        public int bottleneck;
        public int cumulativeFlow;

        public FlowStepDto() {}
        public FlowStepDto(List<Integer> path, int bottleneck, int cumulativeFlow) {
            this.path = path;
            this.bottleneck = bottleneck;
            this.cumulativeFlow = cumulativeFlow;
        }
    }

    public static class FlowEdgeResult {
        public int from;
        public int to;
        public int capacity;
        public int flow;

        public FlowEdgeResult() {}
        public FlowEdgeResult(int from, int to, int capacity, int flow) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }
    }

    private int maxFlow;
    private List<FlowStepDto> steps;
    private List<Integer> sourcePartition;
    private List<Integer> sinkPartition;
    private List<FlowEdgeResult> edgeResults;
    private String algorithmName;
    private String timeComplexity;
    private long executionTimeNs;

    public int getMaxFlow() { return maxFlow; }
    public void setMaxFlow(int maxFlow) { this.maxFlow = maxFlow; }

    public List<FlowStepDto> getSteps() { return steps; }
    public void setSteps(List<FlowStepDto> steps) { this.steps = steps; }

    public List<Integer> getSourcePartition() { return sourcePartition; }
    public void setSourcePartition(List<Integer> sourcePartition) { this.sourcePartition = sourcePartition; }

    public List<Integer> getSinkPartition() { return sinkPartition; }
    public void setSinkPartition(List<Integer> sinkPartition) { this.sinkPartition = sinkPartition; }

    public List<FlowEdgeResult> getEdgeResults() { return edgeResults; }
    public void setEdgeResults(List<FlowEdgeResult> edgeResults) { this.edgeResults = edgeResults; }

    public String getAlgorithmName() { return algorithmName; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }

    public String getTimeComplexity() { return timeComplexity; }
    public void setTimeComplexity(String timeComplexity) { this.timeComplexity = timeComplexity; }

    public long getExecutionTimeNs() { return executionTimeNs; }
    public void setExecutionTimeNs(long executionTimeNs) { this.executionTimeNs = executionTimeNs; }
}
