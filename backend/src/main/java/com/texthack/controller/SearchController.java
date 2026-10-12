package com.texthack.controller;

import org.springframework.web.bind.annotation.*;
import com.texthack.dto.*;
import com.texthack.algorithm.string.KMP;
import com.texthack.algorithm.string.ZAlgorithm;
import com.texthack.algorithm.string.RabinKarp;
import com.texthack.algorithm.dp.Levenshtein;
import com.texthack.algorithm.dp.DamerauLevenshtein;
import com.texthack.datastructure.CustomArrayList;
import com.texthack.datastructure.CustomHashTable;
import com.texthack.service.CorpusService;
import com.texthack.model.Document;
import com.texthack.indexing.InvertedIndex;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final CorpusService corpusService;

    public SearchController(CorpusService corpusService) {
        this.corpusService = corpusService;
    }

    @PostMapping("/pattern")
    public PatternSearchResponse patternSearch(@RequestBody PatternSearchRequest request) {
        corpusService.incrementSearchCount();
        PatternSearchResponse response = new PatternSearchResponse();
        String algo = request.getAlgorithm() != null ? request.getAlgorithm().toUpperCase() : "KMP";
        response.setAlgorithm(algo);

        String pattern = request.getQuery() != null ? request.getQuery() : "";
        long startTime = System.nanoTime();

        if (request.isSearchCorpus()) {
            // Search across entire corpus
            CustomArrayList<Document> docs = corpusService.getAllDocuments();
            List<DocumentSearchMatch> docMatches = new ArrayList<>();
            int totalOccurrences = 0;

            for (int d = 0; d < docs.size(); d++) {
                Document doc = docs.get(d);
                CustomArrayList<Integer> positions = runSingleSearch(doc.getContent(), pattern, algo, response);
                if (positions.size() > 0) {
                    totalOccurrences += positions.size();
                    List<Integer> posList = new ArrayList<>();
                    for (int p = 0; p < positions.size(); p++) {
                        posList.add(positions.get(p));
                    }
                    String snippet = createSnippet(doc.getContent(), positions.get(0), pattern.length());
                    docMatches.add(new DocumentSearchMatch(doc.getId(), doc.getTitle(), doc.getLanguage(), positions.size(), posList, snippet));
                }
            }
            response.setDocumentMatches(docMatches);
            response.setMatchCount(totalOccurrences);
        } else {
            // Search across single text
            String text = request.getText() != null ? request.getText() : "";
            CustomArrayList<Integer> positions = runSingleSearch(text, pattern, algo, response);
            response.setMatchesFromCustom(positions);
        }

        long endTime = System.nanoTime();
        response.setExecutionTimeNs(endTime - startTime);

        // Populate educational complexities and descriptions
        populateComplexity(algo, response);

        return response;
    }

    private CustomArrayList<Integer> runSingleSearch(String text, String pattern, String algo, PatternSearchResponse response) {
        if (pattern.isEmpty() || text.length() < pattern.length()) {
            return new CustomArrayList<>();
        }

        switch (algo) {
            case "Z": {
                ZAlgorithm.ZResult zRes = ZAlgorithm.searchWithDetails(text, pattern);
                response.setzArray(zRes.zArray);
                return zRes.matches;
            }
            case "RABIN_KARP": {
                RabinKarp.RKResult rkRes = RabinKarp.searchWithDetails(text, pattern);
                response.setRollingHash(rkRes.patternHash);
                response.setSpuriousHits(rkRes.spuriousHits);
                return rkRes.matches;
            }
            case "KMP":
            default: {
                KMP.KMPResult kmpRes = KMP.searchWithDetails(text, pattern);
                response.setLpsArray(kmpRes.lps);
                return kmpRes.matches;
            }
        }
    }

    private void populateComplexity(String algo, PatternSearchResponse response) {
        switch (algo) {
            case "Z":
                response.setTimeComplexity("O(n + m)");
                response.setSpaceComplexity("O(n + m)");
                response.setExplanation("Z-Algorithm constructs the Z-array for pattern$text using prefix matching window [L, R] to skip already matched characters.");
                break;
            case "RABIN_KARP":
                response.setTimeComplexity("Average: O(n + m), Worst: O(n * m)");
                response.setSpaceComplexity("O(1)");
                response.setExplanation("Rabin-Karp uses a polynomial rolling hash in base 256 modulo 10^9+7 with character verification upon hash collisions.");
                break;
            case "KMP":
            default:
                response.setTimeComplexity("O(n + m) [Preprocessing O(m), Search O(n)]");
                response.setSpaceComplexity("O(m)");
                response.setExplanation("Knuth-Morris-Pratt constructs the Longest Proper Prefix which is also Suffix (LPS) table to skip redundant character comparisons.");
                break;
        }
    }

    private String createSnippet(String content, int matchPos, int matchLen) {
        int start = Math.max(0, matchPos - 30);
        int end = Math.min(content.length(), matchPos + matchLen + 40);
        String prefix = start > 0 ? "..." : "";
        String suffix = end < content.length() ? "..." : "";
        return prefix + content.substring(start, end) + suffix;
    }

    @PostMapping("/fuzzy")
    public FuzzySearchResponse fuzzySearch(@RequestBody FuzzySearchRequest request) {
        corpusService.incrementSearchCount();
        FuzzySearchResponse response = new FuzzySearchResponse();
        String query = request.getQuery() != null ? request.getQuery().toLowerCase() : "";
        String algo = request.getAlgorithm() != null ? request.getAlgorithm().toUpperCase() : "LEVENSHTEIN";
        response.setAlgorithm(algo);

        long startTime = System.nanoTime();
        CustomArrayList<String> candidateWords = new CustomArrayList<>();

        CustomHashTable<String, Integer> wordFrequencies = new CustomHashTable<>();
        if (request.isSearchCorpus()) {
            candidateWords = corpusService.getInvertedIndex().getAllTerms();
            for (int i = 0; i < candidateWords.size(); i++) {
                wordFrequencies.put(candidateWords.get(i), 1);
            }
        } else {
            String text = request.getText() != null ? request.getText() : "";
            String[] words = text.split("\\s+");
            for (String w : words) {
                String clean = w.replaceAll("[^a-zA-Z0-9\\u0900-\\u097F\\u0C00-\\u0C7F\\u0B80-\\u0BFF]", "").toLowerCase();
                if (!clean.isEmpty()) {
                    Integer count = wordFrequencies.get(clean);
                    if (count == null) {
                        wordFrequencies.put(clean, 1);
                        candidateWords.add(clean);
                    } else {
                        wordFrequencies.put(clean, count + 1);
                    }
                }
            }
        }

        List<FuzzyMatch> matches = new ArrayList<>();
        int maxDist = request.getMaxDistance() > 0 ? request.getMaxDistance() : 3;

        for (int i = 0; i < candidateWords.size(); i++) {
            String word = candidateWords.get(i);
            int distance;
            if ("DAMERAU_LEVENSHTEIN".equals(algo)) {
                distance = DamerauLevenshtein.calculate(query, word);
            } else {
                distance = Levenshtein.calculate(query, word);
            }

            int maxLen = Math.max(query.length(), word.length());
            double matchPct = maxLen > 0 
                ? Math.max(0.0, Math.round((1.0 - (double) distance / maxLen) * 1000.0) / 10.0)
                : 100.0;

            if (distance <= maxDist || matchPct >= 50.0) {
                int freq = wordFrequencies.containsKey(word) ? wordFrequencies.get(word) : 1;
                matches.add(new FuzzyMatch(word, distance, matchPct, freq));
            }
        }

        // Sort by highest matching percentage first, then lowest distance
        matches.sort((a, b) -> {
            int cmp = Double.compare(b.getSimilarityPercentage(), a.getSimilarityPercentage());
            if (cmp != 0) return cmp;
            return Integer.compare(a.getDistance(), b.getDistance());
        });

        // Compute full DP Matrix for top suggestion (or query vs query if none)
        String bestTarget = !matches.isEmpty() ? matches.get(0).getWord() : (candidateWords.size() > 0 ? candidateWords.get(0) : "algorithm");
        response.setMatrixWord1(query);
        response.setMatrixWord2(bestTarget);

        if ("DAMERAU_LEVENSHTEIN".equals(algo)) {
            DamerauLevenshtein.Result matRes = DamerauLevenshtein.computeWithMatrix(query, bestTarget);
            response.setDpMatrix(matRes.matrix);
            response.setTimeComplexity("O(m * n)");
            response.setSpaceComplexity("O(m * n)");
        } else {
            Levenshtein.Result matRes = Levenshtein.computeWithMatrix(query, bestTarget);
            response.setDpMatrix(matRes.matrix);
            response.setTimeComplexity("O(m * n)");
            response.setSpaceComplexity("O(m * n)");
        }

        long endTime = System.nanoTime();
        response.setSuggestions(matches.size() > 15 ? matches.subList(0, 15) : matches);
        response.setExecutionTimeNs(endTime - startTime);

        return response;
    }

    @PostMapping("/inverted")
    public PostingsResponse searchInverted(@RequestParam String term) {
        corpusService.incrementSearchCount();
        InvertedIndex.PostingsList postings = corpusService.getInvertedIndex().search(term);
        PostingsResponse res = new PostingsResponse();
        res.setTerm(term);

        if (postings != null) {
            List<PostingsResponse.DocPostingDto> list = new ArrayList<>();
            for (int i = 0; i < postings.postings.size(); i++) {
                InvertedIndex.Posting p = postings.postings.get(i);
                Document doc = corpusService.getDocumentById(p.docId);
                List<Integer> posList = new ArrayList<>();
                for (int k = 0; k < p.positions.size(); k++) {
                    posList.add(p.positions.get(k));
                }
                list.add(new PostingsResponse.DocPostingDto(p.docId, doc != null ? doc.getTitle() : p.docId, posList));
            }
            res.setPostings(list);
            res.setDocumentCount(list.size());
        } else {
            res.setPostings(new ArrayList<>());
            res.setDocumentCount(0);
        }
        return res;
    }
}
