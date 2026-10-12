package com.texthack.controller;

import com.texthack.model.Document;
import com.texthack.service.CorpusService;
import com.texthack.datastructure.CustomArrayList;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CorpusController {

    private final CorpusService corpusService;

    public CorpusController(CorpusService corpusService) {
        this.corpusService = corpusService;
    }

    @GetMapping("/corpus")
    public List<Document> getAllDocuments() {
        CustomArrayList<Document> docs = corpusService.getAllDocuments();
        List<Document> list = new ArrayList<>();
        for (int i = 0; i < docs.size(); i++) {
            list.add(docs.get(i));
        }
        return list;
    }

    @GetMapping("/corpus/{id}")
    public Document getDocumentById(@PathVariable String id) {
        return corpusService.getDocumentById(id);
    }

    @PostMapping("/corpus/reindex")
    public Map<String, Object> reindex() {
        corpusService.rebuildIndex();
        Map<String, Object> result = new HashMap<>();
        result.put("status", "success");
        result.put("indexedDocuments", corpusService.getInvertedIndex().getDocumentCount());
        result.put("uniqueTerms", corpusService.getInvertedIndex().getUniqueTermsCount());
        result.put("totalOccurrences", corpusService.getInvertedIndex().getTotalOccurrences());
        return result;
    }

    @PostMapping("/corpus/add")
    public Document addDocument(@RequestBody Document doc) {
        if (doc.getId() == null || doc.getId().isEmpty()) {
            doc.setId("doc-" + (corpusService.getAllDocuments().size() + 1));
        }
        corpusService.addDocument(doc);
        return doc;
    }

    @GetMapping("/dashboard/stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        int docCount = corpusService.getAllDocuments().size();
        stats.put("documentsIndexed", docCount);
        stats.put("uniqueTerms", corpusService.getInvertedIndex().getUniqueTermsCount());
        stats.put("totalOccurrences", corpusService.getInvertedIndex().getTotalOccurrences());
        stats.put("totalCorpusSizeBytes", corpusService.getTotalCorpusSizeBytes());
        stats.put("availableAlgorithms", 11);
        stats.put("searchesPerformed", corpusService.getSearchCount());
        stats.put("corpusStatus", docCount > 0 ? "Active & Indexed" : "Corpus not loaded");
        return stats;
    }
}
