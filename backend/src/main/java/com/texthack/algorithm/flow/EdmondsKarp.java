package com.texthack.algorithm.flow;

import com.texthack.datastructure.CustomArrayList;
import com.texthack.datastructure.CustomQueue;

public class EdmondsKarp {

    public static class StepInfo {
        public CustomArrayList<Integer> path;
        public int bottleneck;
        public int cumulativeFlow;

        public StepInfo(CustomArrayList<Integer> path, int bottleneck, int cumulativeFlow) {
            this.path = path;
            this.bottleneck = bottleneck;
            this.cumulativeFlow = cumulativeFlow;
        }
    }

    public static class FlowResult {
        public int maxFlow;
        public CustomArrayList<StepInfo> steps;
        public CustomArrayList<Integer> sourcePartition;
        public CustomArrayList<Integer> sinkPartition;
        public String algorithmName = "Edmonds-Karp";
        public String timeComplexity = "O(V * E^2)";

        public FlowResult() {
            steps = new CustomArrayList<>();
            sourcePartition = new CustomArrayList<>();
            sinkPartition = new CustomArrayList<>();
        }
    }

    public static FlowResult computeMaxFlow(CustomFlowGraph graph, int source, int sink) {
        FlowResult result = new FlowResult();
        int n = graph.getNumVertices();
        int totalFlow = 0;

        int[] parentEdgeIndex = new int[n];
        int[] parentNode = new int[n];

        while (true) {
            // BFS using CustomQueue
            CustomQueue<Integer> queue = new CustomQueue<>();
            boolean[] visited = new boolean[n];

            for (int i = 0; i < n; i++) {
                parentNode[i] = -1;
                parentEdgeIndex[i] = -1;
            }

            queue.enqueue(source);
            visited[source] = true;

            while (!queue.isEmpty()) {
                int curr = queue.dequeue();
                if (curr == sink) break;

                CustomArrayList<CustomFlowGraph.Edge> edges = graph.getEdgesFrom(curr);
                for (int i = 0; i < edges.size(); i++) {
                    CustomFlowGraph.Edge edge = edges.get(i);
                    if (!visited[edge.v] && edge.remainingCapacity() > 0) {
                        visited[edge.v] = true;
                        parentNode[edge.v] = curr;
                        parentEdgeIndex[edge.v] = i;
                        queue.enqueue(edge.v);
                    }
                }
            }

            if (!visited[sink]) {
                // No more augmenting path
                break;
            }

            // Find bottleneck capacity along the path
            int bottleneck = Integer.MAX_VALUE;
            int curr = sink;
            while (curr != source) {
                int prev = parentNode[curr];
                int edgeIdx = parentEdgeIndex[curr];
                CustomFlowGraph.Edge edge = graph.getEdgesFrom(prev).get(edgeIdx);
                bottleneck = Math.min(bottleneck, edge.remainingCapacity());
                curr = prev;
            }

            // Augment flow and record path
            CustomArrayList<Integer> path = new CustomArrayList<>();
            curr = sink;
            while (curr != source) {
                path.add(curr);
                int prev = parentNode[curr];
                int edgeIdx = parentEdgeIndex[curr];
                CustomFlowGraph.Edge forward = graph.getEdgesFrom(prev).get(edgeIdx);
                CustomFlowGraph.Edge reverse = graph.getEdgesFrom(curr).get(forward.revIndex);

                forward.flow += bottleneck;
                reverse.flow -= bottleneck;
                curr = prev;
            }
            path.add(source);

            // Reverse path to be source -> sink
            CustomArrayList<Integer> orderedPath = new CustomArrayList<>(path.size());
            for (int i = path.size() - 1; i >= 0; i--) {
                orderedPath.add(path.get(i));
            }

            totalFlow += bottleneck;
            result.steps.add(new StepInfo(orderedPath, bottleneck, totalFlow));
        }

        result.maxFlow = totalFlow;

        // Compute Min-Cut partition via reachability from source in residual graph
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
}
