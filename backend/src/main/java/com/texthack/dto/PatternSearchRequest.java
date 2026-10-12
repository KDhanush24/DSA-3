package com.texthack.dto;

public class PatternSearchRequest {
    private String query;
    private String algorithm; // "KMP", "Z", "RABIN_KARP"
    private String text; // For single text simulation
    private boolean searchCorpus; // When true, searches across indexed documents

    public String getQuery() { return query; }
    public void setQuery(String query) { this.query = query; }

    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isSearchCorpus() { return searchCorpus; }
    public void setSearchCorpus(boolean searchCorpus) { this.searchCorpus = searchCorpus; }
}
