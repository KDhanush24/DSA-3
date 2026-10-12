package com.texthack.dto;

public class FuzzyMatch {
    private String word;
    private int distance;
    private double similarityPercentage;
    private int count;

    public FuzzyMatch() {}

    public FuzzyMatch(String word, int distance, double similarityPercentage, int count) {
        this.word = word;
        this.distance = distance;
        this.similarityPercentage = similarityPercentage;
        this.count = count;
    }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    public double getSimilarityPercentage() { return similarityPercentage; }
    public void setSimilarityPercentage(double similarityPercentage) { this.similarityPercentage = similarityPercentage; }

    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
