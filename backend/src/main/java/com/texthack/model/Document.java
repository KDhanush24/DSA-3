package com.texthack.model;

public class Document {
    private String id;
    private String title;
    private String language;
    private String content;
    private long sizeBytes;
    private int wordCount;
    private boolean indexed;

    public Document() {}

    public Document(String id, String title, String language, String content) {
        this.id = id;
        this.title = title;
        this.language = language;
        this.content = content != null ? content : "";
        this.sizeBytes = this.content.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;
        this.wordCount = computeWordCount(this.content);
        this.indexed = false;
    }

    private int computeWordCount(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        String[] tokens = text.trim().split("\\s+");
        return tokens.length;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content; 
        this.sizeBytes = content != null ? content.getBytes(java.nio.charset.StandardCharsets.UTF_8).length : 0;
        this.wordCount = computeWordCount(content);
    }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }

    public boolean isIndexed() { return indexed; }
    public void setIndexed(boolean indexed) { this.indexed = indexed; }
}
