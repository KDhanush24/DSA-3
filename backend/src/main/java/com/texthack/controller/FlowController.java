package com.texthack.controller;

import com.texthack.algorithm.flow.CustomFlowGraph;
import com.texthack.algorithm.flow.EdmondsKarp;
import com.texthack.algorithm.flow.Dinic;
import com.texthack.dto.FlowRequest;
import com.texthack.dto.FlowResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/flow")
public class FlowController {

    @PostMapping("/max")
    public FlowResponse calculateMaxFlow(@RequestBody FlowRequest request) {
        int n = request.getNumVertices() > 0 ? request.getNumVertices() : 2;
        CustomFlowGraph graph = new CustomFlowGraph(n);

        if (request.getNodeLabels() != null) {
            for (int i = 0; i < Math.min(n, request.getNodeLabels().size()); i++) {
                graph.setVertexName(i, request.getNodeLabels().get(i));
            }
        }

        if (request.getEdges() != null) {
            for (FlowRequest.FlowEdgeInput edge : request.getEdges()) {
                if (edge.from >= 0 && edge.from < n && edge.to >= 0 && edge.to < n && edge.capacity >= 0) {
                    graph.addEdge(edge.from, edge.to, edge.capacity);
                }
            }
        }

        int s = (request.getSource() >= 0 && request.getSource() < n) ? request.getSource() : 0;
        int t = (request.getSink() >= 0 && request.getSink() < n) ? request.getSink() : (n - 1);
        String algo = request.getAlgorithm() != null ? request.getAlgorithm().toUpperCase() : "EDMONDS_KARP";

        FlowResponse response = new FlowResponse();
        long startTime = System.nanoTime();

        if ("DINIC".equals(algo)) {
            Dinic.DinicResult res = Dinic.computeMaxFlow(graph, s, t);
            response.setMaxFlow(res.maxFlow);
            response.setAlgorithmName("Dinic");
            response.setTimeComplexity(res.timeComplexity);

            List<Integer> sPart = new ArrayList<>();
            for (int i = 0; i < res.sourcePartition.size(); i++) sPart.add(res.sourcePartition.get(i));
            response.setSourcePartition(sPart);

            List<Integer> tPart = new ArrayList<>();
            for (int i = 0; i < res.sinkPartition.size(); i++) tPart.add(res.sinkPartition.get(i));
            response.setSinkPartition(tPart);
            response.setSteps(new ArrayList<>());
        } else {
            EdmondsKarp.FlowResult res = EdmondsKarp.computeMaxFlow(graph, s, t);
            response.setMaxFlow(res.maxFlow);
            response.setAlgorithmName("Edmonds-Karp");
            response.setTimeComplexity(res.timeComplexity);

            List<FlowResponse.FlowStepDto> steps = new ArrayList<>();
            for (int i = 0; i < res.steps.size(); i++) {
                EdmondsKarp.StepInfo step = res.steps.get(i);
                List<Integer> path = new ArrayList<>();
                for (int p = 0; p < step.path.size(); p++) {
                    path.add(step.path.get(p));
                }
                steps.add(new FlowResponse.FlowStepDto(path, step.bottleneck, step.cumulativeFlow));
            }
            response.setSteps(steps);

            List<Integer> sPart = new ArrayList<>();
            for (int i = 0; i < res.sourcePartition.size(); i++) sPart.add(res.sourcePartition.get(i));
            response.setSourcePartition(sPart);

            List<Integer> tPart = new ArrayList<>();
            for (int i = 0; i < res.sinkPartition.size(); i++) tPart.add(res.sinkPartition.get(i));
            response.setSinkPartition(tPart);
        }

        long endTime = System.nanoTime();
        response.setExecutionTimeNs(endTime - startTime);

        // Export edge flows
        List<FlowResponse.FlowEdgeResult> edgeResults = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            for (int i = 0; i < graph.getEdgesFrom(u).size(); i++) {
                CustomFlowGraph.Edge e = graph.getEdgesFrom(u).get(i);
                if (e.capacity > 0) { // original forward edges
                    edgeResults.add(new FlowResponse.FlowEdgeResult(e.u, e.v, e.capacity, e.flow));
                }
            }
        }
        response.setEdgeResults(edgeResults);

        return response;
    }
}
