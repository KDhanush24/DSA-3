package com.texthack.dto;

import java.util.List;

public class FlowRequest {
    public static class FlowEdgeInput {
        public int from;
        public int to;
        public int capacity;

        public FlowEdgeInput() {}
        public FlowEdgeInput(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
        }
    }

    private int numVertices;
    private List<String> nodeLabels;
    private List<FlowEdgeInput> edges;
    private int source;
    private int sink;
    private String algorithm; // "EDMONDS_KARP" or "DINIC"

    public int getNumVertices() { return numVertices; }
    public void setNumVertices(int numVertices) { this.numVertices = numVertices; }

    public List<String> getNodeLabels() { return nodeLabels; }
    public void setNodeLabels(List<String> nodeLabels) { this.nodeLabels = nodeLabels; }

    public List<FlowEdgeInput> getEdges() { return edges; }
    public void setEdges(List<FlowEdgeInput> edges) { this.edges = edges; }

    public int getSource() { return source; }
    public void setSource(int source) { this.source = source; }

    public int getSink() { return sink; }
    public void setSink(int sink) { this.sink = sink; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
}
