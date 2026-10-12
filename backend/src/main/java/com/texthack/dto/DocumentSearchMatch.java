package com.texthack.dto;

import java.util.List;

public class DocumentSearchMatch {
    private String docId;
    private String docTitle;
    private String language;
    private int matchCount;
    private List<Integer> positions;
    private String snippet;

    public DocumentSearchMatch() {}

    public DocumentSearchMatch(String docId, String docTitle, String language, int matchCount, List<Integer> positions, String snippet) {
        this.docId = docId;
        this.docTitle = docTitle;
        this.language = language;
        this.matchCount = matchCount;
        this.positions = positions;
        this.snippet = snippet;
    }

    public String getDocId() { return docId; }
    public void setDocId(String docId) { this.docId = docId; }

    public String getDocTitle() { return docTitle; }
    public void setDocTitle(String docTitle) { this.docTitle = docTitle; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public int getMatchCount() { return matchCount; }
    public void setMatchCount(int matchCount) { this.matchCount = matchCount; }

    public List<Integer> getPositions() { return positions; }
    public void setPositions(List<Integer> positions) { this.positions = positions; }

    public String getSnippet() { return snippet; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
}
