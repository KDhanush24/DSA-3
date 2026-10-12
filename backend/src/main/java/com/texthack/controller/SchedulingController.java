package com.texthack.controller;

import com.texthack.algorithm.approximation.VertexCoverApproximation;
import com.texthack.dto.SchedulingRequest;
import com.texthack.dto.SchedulingResponse;
import com.texthack.datastructure.CustomArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/scheduling")
public class SchedulingController {

    @PostMapping("/vertex-cover")
    public SchedulingResponse solveVertexCover(@RequestBody SchedulingRequest request) {
        int n = request.getNumVertices() > 0 ? request.getNumVertices() : 2;
        CustomArrayList<VertexCoverApproximation.Edge> edgeList = new CustomArrayList<>();

        if (request.getEdges() != null) {
            for (SchedulingRequest.GraphEdge e : request.getEdges()) {
                if (e.u >= 0 && e.u < n && e.v >= 0 && e.v < n) {
                    edgeList.add(new VertexCoverApproximation.Edge(e.u, e.v));
                }
            }
        }

        VertexCoverApproximation.ApproximationResult result = VertexCoverApproximation.solve(n, edgeList);

        SchedulingResponse response = new SchedulingResponse();
        response.setApproxSize(result.approxSize);
        response.setOptimalSize(result.optimalSize);
        response.setApproximationRatio(result.approximationRatio);
        response.setGuaranteeExplanation(result.guaranteeExplanation);
        response.setExecutionTimeNs(result.executionTimeNs);

        List<Integer> approxCover = new ArrayList<>();
        for (int i = 0; i < result.approximateCover.size(); i++) {
            approxCover.add(result.approximateCover.get(i));
        }
        response.setApproximateCover(approxCover);

        List<Integer> optCover = new ArrayList<>();
        for (int i = 0; i < result.optimalCover.size(); i++) {
            optCover.add(result.optimalCover.get(i));
        }
        response.setOptimalCover(optCover);

        List<SchedulingResponse.MatchedEdgeDto> matching = new ArrayList<>();
        for (int i = 0; i < result.maximalMatching.size(); i++) {
            VertexCoverApproximation.Edge me = result.maximalMatching.get(i);
            matching.add(new SchedulingResponse.MatchedEdgeDto(me.u, me.v));
        }
        response.setMaximalMatching(matching);

        return response;
    }
}
