package com.texthack.algorithm.approximation;

import com.texthack.datastructure.CustomArrayList;

public class VertexCoverApproximation {

    public static class Edge {
        public int u;
        public int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    public static class ApproximationResult {
        public CustomArrayList<Integer> approximateCover;
        public CustomArrayList<Edge> maximalMatching;
        public CustomArrayList<Integer> optimalCover;
        public int approxSize;
        public int optimalSize;
        public double approximationRatio;
        public String guaranteeExplanation;
        public long executionTimeNs;

        public ApproximationResult() {
            approximateCover = new CustomArrayList<>();
            maximalMatching = new CustomArrayList<>();
            optimalCover = new CustomArrayList<>();
        }
    }

    public static ApproximationResult solve(int numVertices, CustomArrayList<Edge> edges) {
        ApproximationResult result = new ApproximationResult();
        long startTime = System.nanoTime();

        // 1. Maximal Matching 2-Approximation
        boolean[] inCover = new boolean[numVertices];
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            if (!inCover[e.u] && !inCover[e.v]) {
                inCover[e.u] = true;
                inCover[e.v] = true;
                result.maximalMatching.add(e);
                result.approximateCover.add(e.u);
                result.approximateCover.add(e.v);
            }
        }
        result.approxSize = result.approximateCover.size();

        // 2. Exact optimal vertex cover (if numVertices <= 20)
        if (numVertices <= 20) {
            result.optimalCover = findExactMinimumVertexCover(numVertices, edges);
            result.optimalSize = result.optimalCover.size();
            result.approximationRatio = (result.optimalSize > 0)
                ? (double) result.approxSize / result.optimalSize
                : 1.0;
        } else {
            result.optimalSize = -1; // Graph too large for brute force
            result.approximationRatio = 2.0; // Theoretical bound
        }

        result.guaranteeExplanation = "Maximal Matching 2-Approximation theorem: Since no two edges in the matching M share an endpoint, any valid vertex cover must select at least 1 vertex per matched edge, so |OPT| >= |M|. By including both endpoints of each edge in M, our cover |C| = 2*|M| <= 2*|OPT|.";
        result.executionTimeNs = System.nanoTime() - startTime;

        return result;
    }

    private static CustomArrayList<Integer> findExactMinimumVertexCover(int n, CustomArrayList<Edge> edges) {
        // Iterate through all subset sizes 0..n
        for (int size = 0; size <= n; size++) {
            CustomArrayList<Integer> current = new CustomArrayList<>(size);
            if (findCoverOfSize(n, edges, size, 0, current)) {
                return current;
            }
        }
        CustomArrayList<Integer> all = new CustomArrayList<>(n);
        for (int i = 0; i < n; i++) all.add(i);
        return all;
    }

    private static boolean findCoverOfSize(int n, CustomArrayList<Edge> edges, int targetSize, int start, CustomArrayList<Integer> current) {
        if (current.size() == targetSize) {
            return isVertexCover(edges, current);
        }
        for (int i = start; i < n; i++) {
            current.add(i);
            if (findCoverOfSize(n, edges, targetSize, i + 1, current)) {
                return true;
            }
            current.remove(current.size() - 1);
        }
        return false;
    }

    private static boolean isVertexCover(CustomArrayList<Edge> edges, CustomArrayList<Integer> cover) {
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            boolean covered = false;
            for (int j = 0; j < cover.size(); j++) {
                int node = cover.get(j);
                if (node == e.u || node == e.v) {
                    covered = true;
                    break;
                }
            }
            if (!covered) return false;
        }
        return true;
    }
}
