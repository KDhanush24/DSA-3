package com.texthack.algorithm.flow;

import com.texthack.datastructure.CustomArrayList;

public class CustomFlowGraph {

    public static class Edge {
        public int u;
        public int v;
        public int capacity;
        public int flow;
        public int revIndex; // index of reverse edge in graph.adj[v]

        public Edge(int u, int v, int capacity, int revIndex) {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
            this.flow = 0;
            this.revIndex = revIndex;
        }

        public int remainingCapacity() {
            return capacity - flow;
        }
    }

    private final int numVertices;
    private final CustomArrayList<Edge>[] adj;
    private final CustomArrayList<String> vertexNames;

    @SuppressWarnings("unchecked")
    public CustomFlowGraph(int numVertices) {
        this.numVertices = numVertices;
        this.adj = new CustomArrayList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            this.adj[i] = new CustomArrayList<>();
        }
        this.vertexNames = new CustomArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.vertexNames.add("Node" + i);
        }
    }

    public void setVertexName(int index, String name) {
        if (index >= 0 && index < numVertices) {
            vertexNames.set(index, name);
        }
    }

    public String getVertexName(int index) {
        return (index >= 0 && index < numVertices) ? vertexNames.get(index) : String.valueOf(index);
    }

    public void addEdge(int u, int v, int capacity) {
        if (u < 0 || u >= numVertices || v < 0 || v >= numVertices) {
            throw new IllegalArgumentException("Invalid vertex index");
        }
        Edge a = new Edge(u, v, capacity, adj[v].size());
        Edge b = new Edge(v, u, 0, adj[u].size()); // residual edge with 0 capacity
        adj[u].add(a);
        adj[v].add(b);
    }

    public int getNumVertices() {
        return numVertices;
    }

    public CustomArrayList<Edge> getEdgesFrom(int u) {
        return adj[u];
    }
}
