package com.texthack.algorithm.flow;

import com.texthack.datastructure.CustomArrayList;
import com.texthack.datastructure.CustomQueue;

public class Dinic {

    public static class DinicResult {
        public int maxFlow;
        public int phaseCount;
        public CustomArrayList<Integer> sourcePartition;
        public CustomArrayList<Integer> sinkPartition;
        public String algorithmName = "Dinic";
        public String timeComplexity = "O(V^2 * E)";

        public DinicResult() {
            sourcePartition = new CustomArrayList<>();
            sinkPartition = new CustomArrayList<>();
        }
    }

    public static DinicResult computeMaxFlow(CustomFlowGraph graph, int source, int sink) {
        DinicResult result = new DinicResult();
        int n = graph.getNumVertices();
        int[] level = new int[n];
        int[] ptr = new int[n];
        int totalFlow = 0;
        int phases = 0;

        while (bfs(graph, source, sink, level)) {
            phases++;
            for (int i = 0; i < n; i++) {
                ptr[i] = 0;
            }

            while (true) {
                int pushed = dfs(graph, source, sink, Integer.MAX_VALUE, level, ptr);
                if (pushed == 0) break;
                totalFlow += pushed;
            }
        }

        result.maxFlow = totalFlow;
        result.phaseCount = phases;

        // Min-cut computation via BFS reachability in residual graph
        boolean[] reachable = new boolean[n];
        CustomQueue<Integer> q = new CustomQueue<>();
        q.enqueue(source);
        reachable[source] = true;

        while (!q.isEmpty()) {
            int u = q.dequeue();
            CustomArrayList<CustomFlowGraph.Edge> edges = graph.getEdgesFrom(u);
            for (int i = 0; i < edges.size(); i++) {
                CustomFlowGraph.Edge e = edges.get(i);
                if (e.remainingCapacity() > 0 && !reachable[e.v]) {
                    reachable[e.v] = true;
                    q.enqueue(e.v);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (reachable[i]) {
                result.sourcePartition.add(i);
            } else {
                result.sinkPartition.add(i);
            }
        }

        return result;
    }

    private static boolean bfs(CustomFlowGraph graph, int source, int sink, int[] level) {
        int n = graph.getNumVertices();
        for (int i = 0; i < n; i++) level[i] = -1;
        level[source] = 0;

        CustomQueue<Integer> q = new CustomQueue<>();
        q.enqueue(source);

        while (!q.isEmpty()) {
            int u = q.dequeue();
            CustomArrayList<CustomFlowGraph.Edge> edges = graph.getEdgesFrom(u);
            for (int i = 0; i < edges.size(); i++) {
                CustomFlowGraph.Edge e = edges.get(i);
                if (e.remainingCapacity() > 0 && level[e.v] < 0) {
                    level[e.v] = level[u] + 1;
                    q.enqueue(e.v);
                }
            }
        }
        return level[sink] >= 0;
    }

    private static int dfs(CustomFlowGraph graph, int u, int sink, int pushed, int[] level, int[] ptr) {
        if (pushed == 0 || u == sink) return pushed;

        CustomArrayList<CustomFlowGraph.Edge> edges = graph.getEdgesFrom(u);
        for (; ptr[u] < edges.size(); ptr[u]++) {
            CustomFlowGraph.Edge forward = edges.get(ptr[u]);
            int v = forward.v;
            int tr = forward.remainingCapacity();

            if (level[u] + 1 != level[v] || tr <= 0) continue;

            int trPushed = dfs(graph, v, sink, Math.min(pushed, tr), level, ptr);
            if (trPushed == 0) continue;

            CustomFlowGraph.Edge reverse = graph.getEdgesFrom(v).get(forward.revIndex);
            forward.flow += trPushed;
            reverse.flow -= trPushed;
            return trPushed;
        }
        return 0;
    }
}
