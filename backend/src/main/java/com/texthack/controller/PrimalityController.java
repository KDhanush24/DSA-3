package com.texthack.controller;

import com.texthack.algorithm.randomized.MillerRabin;
import com.texthack.dto.PrimalityRequest;
import com.texthack.dto.PrimalityResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/primality")
public class PrimalityController {

    @PostMapping("/miller-rabin")
    public PrimalityResponse testPrimality(@RequestBody PrimalityRequest request) {
        String num = request.getNumber() != null ? request.getNumber().trim() : "9999999967";
        int rounds = request.getRounds() > 0 ? request.getRounds() : 10;

        MillerRabin.PrimalityResult res = MillerRabin.testPrimality(num, rounds);

        PrimalityResponse response = new PrimalityResponse();
        response.setNumber(res.number);
        response.setPrime(res.isPrime);
        response.setRoundsTested(res.roundsTested);
        response.setCompositeWitness(res.compositeWitness);
        response.setConfidencePercentage(res.confidencePercentage);
        response.setStatusMessage(res.statusMessage);
        response.setMathematicalExplanation(res.mathematicalExplanation);
        response.setExecutionTimeNs(res.executionTimeNs);

        List<String> witnesses = new ArrayList<>();
        for (int i = 0; i < res.witnessesTested.size(); i++) {
            witnesses.add(res.witnessesTested.get(i));
        }
        response.setWitnessesTested(witnesses);

        return response;
    }
}
