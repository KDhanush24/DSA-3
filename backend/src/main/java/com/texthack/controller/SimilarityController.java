package com.texthack.controller;

import com.texthack.algorithm.suffix.DocumentSimilarity;
import com.texthack.dto.SimilarityRequest;
import com.texthack.dto.SimilarityResponse;
import com.texthack.model.Document;
import com.texthack.service.CorpusService;
import com.texthack.datastructure.CustomArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SimilarityController {

    private final CorpusService corpusService;

    public SimilarityController(CorpusService corpusService) {
        this.corpusService = corpusService;
    }

    @PostMapping("/similarity")
    public SimilarityResponse calculateSimilarity(@RequestBody SimilarityRequest request) {
        corpusService.incrementSearchCount();
        String textA = request.getTextA();
        String textB = request.getTextB();

        if (request.getDocIdA() != null && !request.getDocIdA().isEmpty()) {
            Document doc = corpusService.getDocumentById(request.getDocIdA());
            if (doc != null) textA = doc.getContent();
        }

        if (request.getDocIdB() != null && !request.getDocIdB().isEmpty()) {
            Document doc = corpusService.getDocumentById(request.getDocIdB());
            if (doc != null) textB = doc.getContent();
        }

        long start = System.nanoTime();
        DocumentSimilarity.SimilarityResult simResult = DocumentSimilarity.analyze(textA, textB);
        long end = System.nanoTime();

        SimilarityResponse response = new SimilarityResponse();
        response.setSimilarityScore(simResult.similarityScore);
        response.setLongestCommonSubstring(simResult.longestCommonSubstring);
        response.setLongestCommonSubstringLength(simResult.longestCommonSubstringLength);
        response.setSuffixArray(simResult.suffixArray);
        response.setLcpArray(simResult.lcpArray);
        response.setExplanation(simResult.explanation);
        response.setExecutionTimeNs(end - start);
        response.setAlgorithm("Suffix Array O(n log^2 n) + Kasai LCP O(n)");

        List<SimilarityResponse.SubstringMatch> list = new ArrayList<>();
        CustomArrayList<DocumentSimilarity.CommonSubstring> subs = simResult.commonSubstrings;
        for (int i = 0; i < subs.size(); i++) {
            DocumentSimilarity.CommonSubstring cs = subs.get(i);
            list.add(new SimilarityResponse.SubstringMatch(cs.substring, cs.length, cs.docAIndex, cs.docBIndex));
        }
        response.setCommonSubstrings(list);

        return response;
    }
}
