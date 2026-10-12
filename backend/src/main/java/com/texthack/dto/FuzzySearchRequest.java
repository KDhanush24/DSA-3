package com.texthack.dto;

public class FuzzySearchRequest {
    private String query;
    private String text; // A custom text or vocabulary
    private String algorithm; // "LEVENSHTEIN" or "DAMERAU_LEVENSHTEIN"
    private boolean searchCorpus;
    private int maxDistance = 3;

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public boolean isSearchCorpus() { return searchCorpus; }
    public void setSearchCorpus(boolean searchCorpus) { this.searchCorpus = searchCorpus; }

    public int getMaxDistance() { return maxDistance; }
    public void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }
}
