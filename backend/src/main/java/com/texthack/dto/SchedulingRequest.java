package com.texthack.dto;

import java.util.List;

public class SchedulingRequest {
    public static class GraphEdge {
        public int u;
        public int v;

        public GraphEdge() {}
        public GraphEdge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    private int numVertices;
    private List<String> vertexLabels;
    private List<GraphEdge> edges;

    public int getNumVertices() { return numVertices; }
    public void setNumVertices(int numVertices) { this.numVertices = numVertices; }

    public List<String> getVertexLabels() { return vertexLabels; }
    public void setVertexLabels(List<String> vertexLabels) { this.vertexLabels = vertexLabels; }

    public List<GraphEdge> getEdges() { return edges; }
    public void setEdges(List<GraphEdge> edges) { this.edges = edges; }
}
